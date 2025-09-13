package com.parentguard.pro.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.parentguard.pro.R
import com.parentguard.pro.data.model.AppUsage
import com.parentguard.pro.data.model.AppCategory
import com.parentguard.pro.data.repository.AppUsageRepository
import com.parentguard.pro.utils.NotificationUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AppMonitoringService : Service() {
    
    @Inject
    lateinit var appUsageRepository: AppUsageRepository
    
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var isMonitoring = false
    
    companion object {
        private const val TAG = "AppMonitoringService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "app_monitoring_channel"
        
        fun startService(context: Context) {
            val intent = Intent(context, AppMonitoringService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
        
        fun stopService(context: Context) {
            val intent = Intent(context, AppMonitoringService::class.java)
            context.stopService(intent)
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "AppMonitoringService created")
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "AppMonitoringService started")
        startForeground(NOTIFICATION_ID, createNotification())
        startMonitoring()
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "AppMonitoringService destroyed")
        serviceScope.cancel()
        isMonitoring = false
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "App Monitoring",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Monitors app usage for parental control"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("ParentGuard Pro")
            .setContentText("Monitoreando uso de aplicaciones")
            .setSmallIcon(R.drawable.ic_notification)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
    
    private fun startMonitoring() {
        if (isMonitoring) return
        
        isMonitoring = true
        serviceScope.launch {
            try {
                while (isMonitoring) {
                    // Monitor app usage
                    monitorAppUsage()
                    
                    // Wait before next check
                    delay(30000) // Check every 30 seconds
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in monitoring loop", e)
            }
        }
    }
    
    private suspend fun monitorAppUsage() {
        try {
            // This would integrate with UsageStatsManager to get app usage
            // For now, we'll simulate the monitoring
            Log.d(TAG, "Monitoring app usage...")
            
            // TODO: Implement actual app usage monitoring
            // This would require:
            // 1. UsageStatsManager integration
            // 2. AccessibilityService for real-time monitoring
            // 3. App categorization
            // 4. Time limit checking
            // 5. Blocking mechanisms
            
        } catch (e: Exception) {
            Log.e(TAG, "Error monitoring app usage", e)
        }
    }
    
    private suspend fun saveAppUsage(appUsage: AppUsage) {
        try {
            appUsageRepository.insertAppUsage(appUsage)
            Log.d(TAG, "App usage saved: ${appUsage.appName}")
        } catch (e: Exception) {
            Log.e(TAG, "Error saving app usage", e)
        }
    }
    
    private fun categorizeApp(packageName: String, appName: String): AppCategory {
        // Simple categorization logic
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