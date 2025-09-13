package com.parentguard.pro.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.parentguard.pro.data.model.AppUsage
import com.parentguard.pro.data.model.AppCategory
import com.parentguard.pro.data.repository.AppUsageRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class NotificationListenerService : NotificationListenerService() {
    
    @Inject
    lateinit var appUsageRepository: AppUsageRepository
    
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    
    companion object {
        private const val TAG = "NotificationListenerService"
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "NotificationListenerService created")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "NotificationListenerService destroyed")
    }
    
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        
        sbn?.let { notification ->
            Log.d(TAG, "Notification posted: ${notification.packageName}")
            
            // Track notification usage
            serviceScope.launch {
                try {
                    trackNotificationUsage(notification)
                } catch (e: Exception) {
                    Log.e(TAG, "Error tracking notification usage", e)
                }
            }
        }
    }
    
    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        
        sbn?.let { notification ->
            Log.d(TAG, "Notification removed: ${notification.packageName}")
        }
    }
    
    private suspend fun trackNotificationUsage(notification: StatusBarNotification) {
        try {
            val packageName = notification.packageName
            val appName = getAppName(packageName)
            
            // Create app usage entry for notification
            val appUsage = AppUsage(
                id = UUID.randomUUID().toString(),
                childId = getCurrentChildId(), // TODO: Get current child ID
                packageName = packageName,
                appName = appName,
                usageTime = 0, // Notifications don't have usage time
                sessionCount = 1,
                date = Date(),
                isBlocked = false,
                category = categorizeApp(packageName, appName),
                lastUsedAt = Date()
            )
            
            appUsageRepository.insertAppUsage(appUsage)
            Log.d(TAG, "Notification usage tracked: $appName")
            
        } catch (e: Exception) {
            Log.e(TAG, "Error tracking notification usage", e)
        }
    }
    
    private fun getAppName(packageName: String): String {
        // TODO: Implement app name resolution
        return packageName
    }
    
    private fun getCurrentChildId(): String {
        // TODO: Implement current child ID retrieval
        return "default_child_id"
    }
    
    private fun categorizeApp(packageName: String, appName: String): AppCategory {
        return when {
            packageName.contains("social") || packageName.contains("facebook") || 
            packageName.contains("instagram") || packageName.contains("twitter") || 
            packageName.contains("tiktok") || packageName.contains("snapchat") -> AppCategory.SOCIAL_MEDIA
            
            packageName.contains("game") || packageName.contains("play") || 
            packageName.contains("unity") -> AppCategory.GAMES
            
            packageName.contains("edu") || packageName.contains("learn") || 
            packageName.contains("school") -> AppCategory.EDUCATION
            
            packageName.contains("office") || packageName.contains("work") || 
            packageName.contains("productivity") -> AppCategory.PRODUCTIVITY
            
            packageName.contains("video") || packageName.contains("music") || 
            packageName.contains("entertainment") -> AppCategory.ENTERTAINMENT
            
            packageName.contains("message") || packageName.contains("chat") || 
            packageName.contains("whatsapp") || packageName.contains("telegram") -> AppCategory.COMMUNICATION
            
            packageName.contains("news") || packageName.contains("reader") -> AppCategory.NEWS
            
            packageName.contains("shop") || packageName.contains("store") || 
            packageName.contains("amazon") -> AppCategory.SHOPPING
            
            packageName.contains("bank") || packageName.contains("finance") || 
            packageName.contains("money") -> AppCategory.FINANCE
            
            packageName.contains("health") || packageName.contains("fitness") || 
            packageName.contains("medical") -> AppCategory.HEALTH
            
            else -> AppCategory.OTHER
        }
    }
}