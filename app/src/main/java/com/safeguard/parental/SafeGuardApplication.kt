package com.safeguard.parental

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.safeguard.parental.data.local.SafeGuardDatabase
import com.safeguard.parental.data.repository.AuthRepository
import com.safeguard.parental.data.repository.ChildRepository
import com.safeguard.parental.data.repository.LocationRepository
import com.safeguard.parental.data.repository.MonitoringRepository
import com.safeguard.parental.utils.Constants

class SafeGuardApplication : Application() {
    
    // Repositories
    val authRepository by lazy { AuthRepository() }
    val childRepository by lazy { ChildRepository() }
    val locationRepository by lazy { LocationRepository(this) }
    val monitoringRepository by lazy { MonitoringRepository(this) }
    
    // Database
    val database by lazy { SafeGuardDatabase.getDatabase(this) }
    
    // Firebase Analytics
    val analytics by lazy { FirebaseAnalytics.getInstance(this) }
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        // Initialize Firebase Crashlytics
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        
        // Create notification channels
        createNotificationChannels()
        
        // Initialize WorkManager
        initializeWorkManager()
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // Canal de monitoreo
            val monitoringChannel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_MONITORING,
                getString(R.string.notification_channel_monitoring),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notificaciones de monitoreo continuo"
                setShowBadge(false)
            }
            
            // Canal de alertas
            val alertsChannel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ALERTS,
                getString(R.string.notification_channel_alerts),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alertas importantes de seguridad"
                enableVibration(true)
                enableLights(true)
            }
            
            // Canal de ubicación
            val locationChannel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_LOCATION,
                getString(R.string.notification_channel_location),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Actualizaciones de ubicación"
                setShowBadge(false)
            }
            
            notificationManager.createNotificationChannels(listOf(
                monitoringChannel,
                alertsChannel,
                locationChannel
            ))
        }
    }
    
    private fun initializeWorkManager() {
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
        
        WorkManager.initialize(this, config)
    }
}