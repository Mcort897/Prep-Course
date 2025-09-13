package com.safeguard.parental.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.safeguard.parental.data.model.Child
import com.safeguard.parental.utils.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChildRepository {
    
    private val firestore = FirebaseFirestore.getInstance()
    
    fun getChildrenByParent(parentId: String): Flow<List<Child>> = callbackFlow {
        val listener = firestore.collection(Constants.COLLECTION_CHILDREN)
            .whereEqualTo("parentId", parentId)
            .whereEqualTo("isActive", true)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val children = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Child::class.java)
                } ?: emptyList()
                
                trySend(children)
            }
        
        awaitClose { listener.remove() }
    }
    
    suspend fun getChildById(childId: String): Result<Child?> {
        return try {
            val doc = firestore.collection(Constants.COLLECTION_CHILDREN)
                .document(childId)
                .get()
                .await()
            
            val child = doc.toObject(Child::class.java)
            Result.success(child)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun addChild(child: Child): Result<String> {
        return try {
            val docRef = firestore.collection(Constants.COLLECTION_CHILDREN)
                .add(child)
                .await()
            
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateChild(child: Child): Result<Unit> {
        return try {
            firestore.collection(Constants.COLLECTION_CHILDREN)
                .document(child.id)
                .set(child)
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteChild(childId: String): Result<Unit> {
        return try {
            // Soft delete - mark as inactive
            firestore.collection(Constants.COLLECTION_CHILDREN)
                .document(childId)
                .update("isActive", false)
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateChildStatus(childId: String, status: String): Result<Unit> {
        return try {
            firestore.collection(Constants.COLLECTION_CHILDREN)
                .document(childId)
                .update(
                    mapOf(
                        "status" to status,
                        "lastSeen" to com.google.firebase.Timestamp.now()
                    )
                )
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateChildLocation(childId: String, latitude: Double, longitude: Double): Result<Unit> {
        return try {
            val geoPoint = com.google.firebase.firestore.GeoPoint(latitude, longitude)
            
            firestore.collection(Constants.COLLECTION_CHILDREN)
                .document(childId)
                .update(
                    mapOf(
                        "currentLocation.geoPoint" to geoPoint,
                        "currentLocation.latitude" to latitude,
                        "currentLocation.longitude" to longitude,
                        "currentLocation.timestamp" to com.google.firebase.Timestamp.now(),
                        "lastSeen" to com.google.firebase.Timestamp.now()
                    )
                )
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun blockApp(childId: String, packageName: String): Result<Unit> {
        return try {
            val child = getChildById(childId).getOrNull()
            if (child != null) {
                val updatedBlockedApps = child.blockedApps.toMutableList()
                if (!updatedBlockedApps.contains(packageName)) {
                    updatedBlockedApps.add(packageName)
                }
                
                val updatedAllowedApps = child.allowedApps.toMutableList()
                updatedAllowedApps.remove(packageName)
                
                firestore.collection(Constants.COLLECTION_CHILDREN)
                    .document(childId)
                    .update(
                        mapOf(
                            "blockedApps" to updatedBlockedApps,
                            "allowedApps" to updatedAllowedApps
                        )
                    )
                    .await()
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun unblockApp(childId: String, packageName: String): Result<Unit> {
        return try {
            val child = getChildById(childId).getOrNull()
            if (child != null) {
                val updatedBlockedApps = child.blockedApps.toMutableList()
                updatedBlockedApps.remove(packageName)
                
                val updatedAllowedApps = child.allowedApps.toMutableList()
                if (!updatedAllowedApps.contains(packageName)) {
                    updatedAllowedApps.add(packageName)
                }
                
                firestore.collection(Constants.COLLECTION_CHILDREN)
                    .document(childId)
                    .update(
                        mapOf(
                            "blockedApps" to updatedBlockedApps,
                            "allowedApps" to updatedAllowedApps
                        )
                    )
                    .await()
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}