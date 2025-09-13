package com.safeguard.parental.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Location(
    @DocumentId
    val id: String = "",
    val childId: String = "",
    val geoPoint: GeoPoint = GeoPoint(0.0, 0.0),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val accuracy: Float = 0f,
    val altitude: Double = 0.0,
    val speed: Float = 0f,
    val bearing: Float = 0f,
    val address: String = "",
    val locationName: String = "",
    @ServerTimestamp
    val timestamp: Date? = null,
    val provider: String = "",
    val isInSafeZone: Boolean = false,
    val safeZoneId: String = "",
    val batteryLevel: Int = 100,
    val isCharging: Boolean = false
)

data class SafeZone(
    @DocumentId
    val id: String = "",
    val parentId: String = "",
    val childId: String = "",
    val name: String = "",
    val description: String = "",
    val centerLat: Double = 0.0,
    val centerLng: Double = 0.0,
    val radiusMeters: Double = 100.0,
    val isActive: Boolean = true,
    val alertOnEntry: Boolean = false,
    val alertOnExit: Boolean = true,
    val timeRestrictions: List<TimeSlot> = emptyList(),
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null,
    val color: String = "#4CAF50" // Hex color for map display
)

data class LocationHistory(
    @DocumentId
    val id: String = "",
    val childId: String = "",
    val locations: List<Location> = emptyList(),
    val date: String = "", // YYYY-MM-DD format
    val totalDistanceKm: Double = 0.0,
    val averageSpeed: Float = 0f,
    val timeInSafeZones: Long = 0L, // milliseconds
    val timeOutsideSafeZones: Long = 0L,
    val alertsGenerated: Int = 0
)

data class LocationAlert(
    @DocumentId
    val id: String = "",
    val childId: String = "",
    val parentId: String = "",
    val type: AlertType = AlertType.SAFE_ZONE_EXIT,
    val title: String = "",
    val message: String = "",
    val location: Location? = null,
    val safeZone: SafeZone? = null,
    val isRead: Boolean = false,
    val severity: AlertSeverity = AlertSeverity.MEDIUM,
    @ServerTimestamp
    val timestamp: Date? = null
)

enum class AlertType {
    SAFE_ZONE_ENTRY,
    SAFE_ZONE_EXIT,
    SPEED_LIMIT_EXCEEDED,
    LOCATION_SHARING_DISABLED,
    BATTERY_LOW,
    DEVICE_OFFLINE,
    EMERGENCY_LOCATION
}

enum class AlertSeverity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}