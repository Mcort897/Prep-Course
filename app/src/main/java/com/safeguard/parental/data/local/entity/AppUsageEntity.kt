package com.safeguard.parental.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "app_usage")
data class AppUsageEntity(
    @PrimaryKey
    val id: String,
    val childId: String,
    val packageName: String,
    val appName: String,
    val category: String,
    val usageTimeMillis: Long,
    val launchCount: Int,
    val lastUsed: Date?,
    val date: String,
    val createdAt: Date?,
    val icon: String
)