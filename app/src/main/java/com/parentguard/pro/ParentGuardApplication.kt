package com.parentguard.pro

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.Configuration
import androidx.work.WorkManager
import com.parentguard.pro.data.database.AppDatabase
import com.parentguard.pro.data.repository.*
import com.parentguard.pro.service.AppMonitoringService
import com.parentguard.pro.utils.NotificationUtils
import com.parentguard.pro.utils.PermissionUtils
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class ParentGuardApplication : Application(), Configuration.Provider {

    // Database
    val database by lazy { AppDatabase.getDatabase(this) }
    
    // Repositories
    val userRepository by lazy { UserRepository(database.userDao()) }
    val childRepository by lazy { ChildRepository(database.childDao()) }
    val appUsageRepository by lazy { AppUsageRepository(database.appUsageDao()) }
    val timeLimitRepository by lazy { TimeLimitRepository(database.timeLimitDao()) }
    val locationRepository by lazy { LocationRepository(database.locationDao()) }
    val rewardRepository by lazy { RewardRepository(database.rewardDao()) }
    val webFilterRepository by lazy { WebFilterRepository(database.webFilterDao()) }

    override fun onCreate() {
        super.onCreate()
        
        // Initialize WorkManager
        WorkManager.initialize(this, workManagerConfiguration)
        
        // Create notification channels
        createNotificationChannels()
        
        // Initialize services
        initializeServices()
        
        // Check and request permissions
        checkPermissions()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // Parental Control Channel
            val parentalChannel = NotificationChannel(
                NotificationUtils.CHANNEL_PARENTAL_CONTROL,
                getString(R.string.notification_channel_parental),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones de control parental"
                enableVibration(true)
                enableLights(true)
            }
            
            // Alerts Channel
            val alertsChannel = NotificationChannel(
                NotificationUtils.CHANNEL_ALERTS,
                getString(R.string.notification_channel_alerts),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Alertas y notificaciones importantes"
                enableVibration(true)
            }
            
            // Rewards Channel
            val rewardsChannel = NotificationChannel(
                NotificationUtils.CHANNEL_REWARDS,
                getString(R.string.notification_channel_rewards),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notificaciones de recompensas y logros"
                enableVibration(false)
            }
            
            notificationManager.createNotificationChannels(
                listOf(parentalChannel, alertsChannel, rewardsChannel)
            )
        }
    }

    private fun initializeServices() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Start app monitoring service
                AppMonitoringService.startService(this@ParentGuardApplication)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun checkPermissions() {
        CoroutineScope(Dispatchers.Main).launch {
            PermissionUtils.checkAndRequestPermissions(this@ParentGuardApplication)
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}