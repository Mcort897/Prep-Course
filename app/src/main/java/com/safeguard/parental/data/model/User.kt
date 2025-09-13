package com.safeguard.parental.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class User(
    @DocumentId
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val role: UserRole = UserRole.PARENT,
    val isActive: Boolean = true,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null,
    val deviceInfo: DeviceInfo? = null,
    val preferences: UserPreferences = UserPreferences()
)

enum class UserRole {
    PARENT,
    CHILD,
    ADMIN
}

data class DeviceInfo(
    val deviceId: String = "",
    val deviceName: String = "",
    val model: String = "",
    val osVersion: String = "",
    val appVersion: String = "",
    val lastSeen: Date? = null
)

data class UserPreferences(
    val notificationsEnabled: Boolean = true,
    val locationTrackingEnabled: Boolean = true,
    val appMonitoringEnabled: Boolean = true,
    val webFilteringEnabled: Boolean = true,
    val dailyReportsEnabled: Boolean = true,
    val weeklyReportsEnabled: Boolean = true,
    val alertsEnabled: Boolean = true,
    val language: String = "es",
    val timezone: String = "America/Mexico_City"
)