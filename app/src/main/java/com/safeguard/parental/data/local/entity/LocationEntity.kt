package com.safeguard.parental.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey
    val id: String,
    val childId: String,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val altitude: Double,
    val speed: Float,
    val bearing: Float,
    val address: String,
    val locationName: String,
    val timestamp: Date,
    val provider: String,
    val isInSafeZone: Boolean,
    val safeZoneId: String,
    val batteryLevel: Int,
    val isCharging: Boolean
)

@Entity(tableName = "safe_zones")
data class SafeZoneEntity(
    @PrimaryKey
    val id: String,
    val parentId: String,
    val childId: String,
    val name: String,
    val description: String,
    val centerLat: Double,
    val centerLng: Double,
    val radiusMeters: Double,
    val isActive: Boolean,
    val alertOnEntry: Boolean,
    val alertOnExit: Boolean,
    val createdAt: Date?,
    val updatedAt: Date?,
    val color: String
)