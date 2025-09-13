package com.safeguard.parental.data.repository

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.google.firebase.firestore.FirebaseFirestore
import com.safeguard.parental.data.model.AppUsage
import com.safeguard.parental.data.model.AppInfo
import com.safeguard.parental.utils.Constants
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class MonitoringRepository(private val context: Context) {
    
    private val firestore = FirebaseFirestore.getInstance()
    private val packageManager = context.packageManager
    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    suspend fun getInstalledApps(): List<AppInfo> {
        return try {
            val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            
            installedApps.mapNotNull { appInfo ->
                try {
                    val packageName = appInfo.packageName
                    val appName = packageManager.getApplicationLabel(appInfo).toString()
                    val isSystemApp = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                    
                    // Skip system apps unless they're commonly used
                    if (isSystemApp && !isCommonlyUsedSystemApp(packageName)) {
                        return@mapNotNull null
                    }
                    
                    AppInfo(
                        packageName = packageName,
                        appName = appName,
                        category = getAppCategory(packageName),
                        isSystemApp = isSystemApp,
                        isInstalled = true
                    )
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getAppUsageStats(childId: String, startTime: Long, endTime: Long): List<AppUsage> {
        return try {
            val usageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                startTime,
                endTime
            )
            
            val date = dateFormat.format(Date())
            
            usageStats.mapNotNull { stats ->
                if (stats.totalTimeInForeground > 0) {
                    try {
                        val appInfo = packageManager.getApplicationInfo(stats.packageName, 0)
                        val appName = packageManager.getApplicationLabel(appInfo).toString()
                        
                        AppUsage(
                            id = "${childId}_${stats.packageName}_$date",
                            childId = childId,
                            packageName = stats.packageName,
                            appName = appName,
                            category = getAppCategory(stats.packageName),
                            usageTimeMillis = stats.totalTimeInForeground,
                            launchCount = stats.lastTimeUsed.toInt(),
                            lastUsed = Date(stats.lastTimeUsed),
                            date = date
                        )
                    } catch (e: Exception) {
                        null
                    }
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun saveAppUsage(appUsages: List<AppUsage>): Result<Unit> {
        return try {
            val batch = firestore.batch()
            
            appUsages.forEach { usage ->
                val docRef = firestore.collection(Constants.COLLECTION_APP_USAGE)
                    .document(usage.id)
                batch.set(docRef, usage)
            }
            
            batch.commit().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getAppUsageByChild(childId: String, date: String): Result<List<AppUsage>> {
        return try {
            val snapshot = firestore.collection(Constants.COLLECTION_APP_USAGE)
                .whereEqualTo("childId", childId)
                .whereEqualTo("date", date)
                .get()
                .await()
            
            val appUsages = snapshot.documents.mapNotNull { doc ->
                doc.toObject(AppUsage::class.java)
            }
            
            Result.success(appUsages)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getTotalScreenTime(childId: String, date: String): Result<Long> {
        return try {
            val appUsages = getAppUsageByChild(childId, date).getOrNull() ?: emptyList()
            val totalTime = appUsages.sumOf { it.usageTimeMillis }
            Result.success(totalTime)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getMostUsedApps(childId: String, date: String, limit: Int = 10): Result<List<AppUsage>> {
        return try {
            val appUsages = getAppUsageByChild(childId, date).getOrNull() ?: emptyList()
            val sortedApps = appUsages.sortedByDescending { it.usageTimeMillis }.take(limit)
            Result.success(sortedApps)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun getAppCategory(packageName: String): String {
        return when {
            Constants.SOCIAL_MEDIA_PACKAGES.contains(packageName) -> "Social Media"
            Constants.GAME_PACKAGES.contains(packageName) -> "Games"
            Constants.EDUCATIONAL_PACKAGES.contains(packageName) -> "Education"
            packageName.contains("browser") || packageName.contains("chrome") -> "Browser"
            packageName.contains("video") || packageName.contains("youtube") -> "Video"
            packageName.contains("music") || packageName.contains("spotify") -> "Music"
            packageName.contains("photo") || packageName.contains("camera") -> "Photo"
            packageName.contains("messenger") || packageName.contains("whatsapp") -> "Communication"
            else -> "Other"
        }
    }
    
    private fun isCommonlyUsedSystemApp(packageName: String): Boolean {
        val commonSystemApps = listOf(
            "com.android.chrome",
            "com.android.camera",
            "com.android.gallery3d",
            "com.android.music",
            "com.android.calculator2",
            "com.android.calendar",
            "com.android.contacts",
            "com.android.phone",
            "com.android.messaging",
            "com.android.settings"
        )
        return commonSystemApps.contains(packageName)
    }
}