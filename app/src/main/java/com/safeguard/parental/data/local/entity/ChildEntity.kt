package com.safeguard.parental.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "children")
data class ChildEntity(
    @PrimaryKey
    val id: String,
    val parentId: String,
    val name: String,
    val age: Int,
    val avatarUrl: String,
    val deviceId: String,
    val deviceName: String,
    val isActive: Boolean,
    val status: String,
    val createdAt: Date?,
    val updatedAt: Date?,
    val lastSeen: Date?,
    val currentLatitude: Double?,
    val currentLongitude: Double?,
    val blockedApps: List<String>,
    val allowedApps: List<String>,
    val safeZones: List<String>
)