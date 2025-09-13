package com.parentguard.pro.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "children")
@Parcelize
data class Child(
    @PrimaryKey
    val id: String,
    val parentId: String,
    val name: String,
    val age: Int,
    val profileImageUrl: String? = null,
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val lastSeenAt: Date? = null,
    val currentLocation: Location? = null,
    val deviceInfo: DeviceInfo? = null,
    val restrictions: ChildRestrictions? = null
) : Parcelable

@Parcelize
data class Location(
    val latitude: Double,
    val longitude: Double,
    val address: String? = null,
    val timestamp: Date = Date(),
    val accuracy: Float? = null
) : Parcelable

@Parcelize
data class DeviceInfo(
    val deviceId: String,
    val deviceName: String,
    val androidVersion: String,
    val appVersion: String,
    val lastSyncAt: Date = Date()
) : Parcelable

@Parcelize
data class ChildRestrictions(
    val maxDailyScreenTime: Long = 0, // in minutes
    val bedtimeStart: String? = null, // HH:mm format
    val bedtimeEnd: String? = null, // HH:mm format
    val blockedApps: List<String> = emptyList(),
    val allowedApps: List<String> = emptyList(),
    val blockedWebsites: List<String> = emptyList(),
    val allowedWebsites: List<String> = emptyList(),
    val locationRestrictions: List<LocationRestriction> = emptyList(),
    val emergencyContacts: List<String> = emptyList(),
    val panicButtonEnabled: Boolean = true,
    val sosMessage: String = "Necesito ayuda urgente"
) : Parcelable

@Parcelize
data class LocationRestriction(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Float, // in meters
    val isAllowed: Boolean, // true = allowed area, false = restricted area
    val alertOnEnter: Boolean = true,
    val alertOnExit: Boolean = true
) : Parcelable