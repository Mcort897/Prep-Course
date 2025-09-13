package com.safeguard.parental.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.safeguard.parental.MainActivity
import com.safeguard.parental.R
import com.safeguard.parental.utils.Constants
import com.safeguard.parental.workers.AppMonitoringWorker
import java.util.concurrent.TimeUnit

class MonitoringService : Service() {
    
    private val notificationId = Constants.NOTIFICATION_ID_MONITORING
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_MONITORING -> startMonitoring()
            ACTION_STOP_MONITORING -> stopMonitoring()
        }
        
        return START_STICKY
    }
    
    private fun startMonitoring() {
        val notification = createNotification()
        startForeground(notificationId, notification)
        
        // Start periodic app monitoring work
        scheduleAppMonitoring()
    }
    
    private fun stopMonitoring() {
        // Cancel all monitoring work
        WorkManager.getInstance(this).cancelAllWorkByTag(Constants.WORK_TAG_MONITORING)
        
        stopForeground(true)
        stopSelf()
    }
    
    private fun scheduleAppMonitoring() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        
        val appMonitoringWork = PeriodicWorkRequestBuilder<AppMonitoringWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .addTag(Constants.WORK_TAG_MONITORING)
            .build()
        
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "app_monitoring_work",
            ExistingPeriodicWorkPolicy.REPLACE,
            appMonitoringWork
        )
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_MONITORING)
            .setContentTitle(getString(R.string.notification_monitoring_title))
            .setContentText(getString(R.string.notification_monitoring_content))
            .setSmallIcon(R.drawable.ic_shield_check)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_MONITORING,
                getString(R.string.notification_channel_monitoring),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notificaciones de monitoreo continuo"
                setShowBadge(false)
                enableVibration(false)
                setSound(null, null)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    companion object {
        const val ACTION_START_MONITORING = "start_monitoring"
        const val ACTION_STOP_MONITORING = "stop_monitoring"
    }
}