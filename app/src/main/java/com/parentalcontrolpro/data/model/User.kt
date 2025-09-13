package com.parentalcontrolpro.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val role: UserRole = UserRole.CHILD,
    val profileImage: String = "",
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val lastLogin: Date = Date(),
    val parentId: String? = null,
    val childInfo: ChildInfo? = null,
    val parentInfo: ParentInfo? = null
) : Parcelable

@Parcelize
data class ChildInfo(
    val age: Int = 0,
    val grade: String = "",
    val school: String = "",
    val emergencyContacts: List<String> = emptyList(),
    val allowedApps: List<String> = emptyList(),
    val blockedApps: List<String> = emptyList(),
    val allowedWebsites: List<String> = emptyList(),
    val blockedWebsites: List<String> = emptyList(),
    val dailyTimeLimit: Int = 0, // in minutes
    val bedtime: String = "22:00",
    val wakeUpTime: String = "07:00",
    val studyTime: Int = 0, // in minutes
    val playTime: Int = 0, // in minutes
    val safeZones: List<SafeZone> = emptyList(),
    val dangerZones: List<DangerZone> = emptyList(),
    val isLocationTrackingEnabled: Boolean = true,
    val isWebFilteringEnabled: Boolean = true,
    val isAppBlockingEnabled: Boolean = true,
    val isTimeControlEnabled: Boolean = true
) : Parcelable

@Parcelize
data class ParentInfo(
    val phoneNumber: String = "",
    val emergencyContacts: List<String> = emptyList(),
    val notificationSettings: NotificationSettings = NotificationSettings(),
    val isMonitoringEnabled: Boolean = true,
    val isLocationTrackingEnabled: Boolean = true,
    val isWebFilteringEnabled: Boolean = true,
    val isAppBlockingEnabled: Boolean = true,
    val isTimeControlEnabled: Boolean = true
) : Parcelable

@Parcelize
data class SafeZone(
    val id: String = "",
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val radius: Float = 100f, // in meters
    val isActive: Boolean = true
) : Parcelable

@Parcelize
data class DangerZone(
    val id: String = "",
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val radius: Float = 100f, // in meters
    val isActive: Boolean = true
) : Parcelable

@Parcelize
data class NotificationSettings(
    val isEmailEnabled: Boolean = true,
    val isPushEnabled: Boolean = true,
    val isSmsEnabled: Boolean = false,
    val timeLimitAlerts: Boolean = true,
    val locationAlerts: Boolean = true,
    val contentAlerts: Boolean = true,
    val emergencyAlerts: Boolean = true,
    val appUsageAlerts: Boolean = true
) : Parcelable

enum class UserRole {
    PARENT, CHILD
}