package com.safeguard.parental.data.repository

import android.content.Context
import android.location.Geocoder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.safeguard.parental.data.model.Location
import com.safeguard.parental.data.model.SafeZone
import com.safeguard.parental.data.model.LocationAlert
import com.safeguard.parental.data.model.AlertType
import com.safeguard.parental.data.model.AlertSeverity
import com.safeguard.parental.utils.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.math.*

class LocationRepository(private val context: Context) {
    
    private val firestore = FirebaseFirestore.getInstance()
    private val geocoder = Geocoder(context, Locale.getDefault())
    
    fun getLocationsByChild(childId: String): Flow<List<Location>> = callbackFlow {
        val listener = firestore.collection(Constants.COLLECTION_LOCATIONS)
            .whereEqualTo("childId", childId)
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(100)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val locations = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Location::class.java)
                } ?: emptyList()
                
                trySend(locations)
            }
        
        awaitClose { listener.remove() }
    }
    
    suspend fun saveLocation(location: Location): Result<String> {
        return try {
            // Check if location is in any safe zone
            val safeZones = getSafeZonesByChild(location.childId).getOrNull() ?: emptyList()
            val (isInSafeZone, safeZoneId) = checkIfInSafeZone(location, safeZones)
            
            // Get address from coordinates
            val address = getAddressFromCoordinates(location.latitude, location.longitude)
            
            val updatedLocation = location.copy(
                isInSafeZone = isInSafeZone,
                safeZoneId = safeZoneId,
                address = address
            )
            
            val docRef = firestore.collection(Constants.COLLECTION_LOCATIONS)
                .add(updatedLocation)
                .await()
            
            // Generate alerts if needed
            generateLocationAlerts(updatedLocation, safeZones)
            
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getLastLocation(childId: String): Result<Location?> {
        return try {
            val snapshot = firestore.collection(Constants.COLLECTION_LOCATIONS)
                .whereEqualTo("childId", childId)
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()
            
            val location = snapshot.documents.firstOrNull()?.toObject(Location::class.java)
            Result.success(location)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getSafeZonesByParent(parentId: String): Flow<List<SafeZone>> = callbackFlow {
        val listener = firestore.collection(Constants.COLLECTION_SAFE_ZONES)
            .whereEqualTo("parentId", parentId)
            .whereEqualTo("isActive", true)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val safeZones = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(SafeZone::class.java)
                } ?: emptyList()
                
                trySend(safeZones)
            }
        
        awaitClose { listener.remove() }
    }
    
    suspend fun getSafeZonesByChild(childId: String): Result<List<SafeZone>> {
        return try {
            val snapshot = firestore.collection(Constants.COLLECTION_SAFE_ZONES)
                .whereEqualTo("childId", childId)
                .whereEqualTo("isActive", true)
                .get()
                .await()
            
            val safeZones = snapshot.documents.mapNotNull { doc ->
                doc.toObject(SafeZone::class.java)
            }
            
            Result.success(safeZones)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun addSafeZone(safeZone: SafeZone): Result<String> {
        return try {
            val docRef = firestore.collection(Constants.COLLECTION_SAFE_ZONES)
                .add(safeZone)
                .await()
            
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateSafeZone(safeZone: SafeZone): Result<Unit> {
        return try {
            firestore.collection(Constants.COLLECTION_SAFE_ZONES)
                .document(safeZone.id)
                .set(safeZone)
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteSafeZone(safeZoneId: String): Result<Unit> {
        return try {
            firestore.collection(Constants.COLLECTION_SAFE_ZONES)
                .document(safeZoneId)
                .update("isActive", false)
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun checkIfInSafeZone(location: Location, safeZones: List<SafeZone>): Pair<Boolean, String> {
        for (safeZone in safeZones) {
            val distance = calculateDistance(
                location.latitude, location.longitude,
                safeZone.centerLat, safeZone.centerLng
            )
            
            if (distance <= safeZone.radiusMeters) {
                return Pair(true, safeZone.id)
            }
        }
        return Pair(false, "")
    }
    
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371000.0 // Earth's radius in meters
        
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        
        val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        
        return earthRadius * c
    }
    
    private suspend fun getAddressFromCoordinates(latitude: Double, longitude: Double): String {
        return try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                "${address.thoroughfare ?: ""} ${address.locality ?: ""}, ${address.countryName ?: ""}".trim()
            } else {
                "Ubicación desconocida"
            }
        } catch (e: Exception) {
            "Lat: ${"%.6f".format(latitude)}, Lng: ${"%.6f".format(longitude)}"
        }
    }
    
    private suspend fun generateLocationAlerts(location: Location, safeZones: List<SafeZone>) {
        try {
            val previousLocation = getSecondLastLocation(location.childId)
            
            if (previousLocation != null) {
                val wasInSafeZone = previousLocation.isInSafeZone
                val isInSafeZone = location.isInSafeZone
                
                // Generate safe zone entry/exit alerts
                if (!wasInSafeZone && isInSafeZone) {
                    // Entered safe zone
                    val safeZone = safeZones.find { it.id == location.safeZoneId }
                    if (safeZone?.alertOnEntry == true) {
                        createLocationAlert(
                            location = location,
                            safeZone = safeZone,
                            type = AlertType.SAFE_ZONE_ENTRY,
                            title = "Zona Segura - Entrada",
                            message = "Su hijo ha entrado en la zona segura: ${safeZone.name}"
                        )
                    }
                } else if (wasInSafeZone && !isInSafeZone) {
                    // Exited safe zone
                    val safeZone = safeZones.find { it.id == previousLocation.safeZoneId }
                    if (safeZone?.alertOnExit == true) {
                        createLocationAlert(
                            location = location,
                            safeZone = safeZone,
                            type = AlertType.SAFE_ZONE_EXIT,
                            title = "Zona Segura - Salida",
                            message = "Su hijo ha salido de la zona segura: ${safeZone.name}",
                            severity = AlertSeverity.HIGH
                        )
                    }
                }
            }
        } catch (e: Exception) {
            // Log error but don't fail the location save
        }
    }
    
    private suspend fun getSecondLastLocation(childId: String): Location? {
        return try {
            val snapshot = firestore.collection(Constants.COLLECTION_LOCATIONS)
                .whereEqualTo("childId", childId)
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .limit(2)
                .get()
                .await()
            
            val locations = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Location::class.java)
            }
            
            if (locations.size >= 2) locations[1] else null
        } catch (e: Exception) {
            null
        }
    }
    
    private suspend fun createLocationAlert(
        location: Location,
        safeZone: SafeZone? = null,
        type: AlertType,
        title: String,
        message: String,
        severity: AlertSeverity = AlertSeverity.MEDIUM
    ) {
        try {
            val alert = LocationAlert(
                childId = location.childId,
                parentId = safeZone?.parentId ?: "",
                type = type,
                title = title,
                message = message,
                location = location,
                safeZone = safeZone,
                severity = severity
            )
            
            firestore.collection("location_alerts")
                .add(alert)
                .await()
        } catch (e: Exception) {
            // Log error
        }
    }
}