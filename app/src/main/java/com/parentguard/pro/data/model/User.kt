package com.parentguard.pro.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val userType: UserType,
    val familyCode: String? = null,
    val profileImageUrl: String? = null,
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val lastLoginAt: Date? = null,
    val settings: UserSettings? = null
) : Parcelable

@Parcelize
enum class UserType : Parcelable {
    PARENT,
    CHILD
}

@Parcelize
data class UserSettings(
    val notificationsEnabled: Boolean = true,
    val locationTrackingEnabled: Boolean = true,
    val appMonitoringEnabled: Boolean = true,
    val webFilteringEnabled: Boolean = true,
    val timeLimitsEnabled: Boolean = true,
    val rewardsEnabled: Boolean = true,
    val theme: String = "light", // light, dark, auto
    val language: String = "es", // es, en
    val timezone: String = "UTC"
) : Parcelable