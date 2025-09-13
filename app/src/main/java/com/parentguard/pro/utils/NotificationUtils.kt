package com.parentguard.pro.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.parentguard.pro.R
import com.parentguard.pro.ui.MainActivity

object NotificationUtils {
    
    const val CHANNEL_PARENTAL_CONTROL = "parental_control"
    const val CHANNEL_ALERTS = "alerts"
    const val CHANNEL_REWARDS = "rewards"
    
    private const val NOTIFICATION_ID_PARENTAL = 1001
    private const val NOTIFICATION_ID_ALERTS = 1002
    private const val NOTIFICATION_ID_REWARDS = 1003
    
    fun createNotificationChannel(
        context: Context,
        channelId: String,
        channelName: String,
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                importance
            ).apply {
                description = "ParentGuard Pro notifications"
                enableVibration(true)
                enableLights(true)
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    fun showParentalControlNotification(
        context: Context,
        title: String,
        message: String,
        childName: String? = null
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_PARENTAL_CONTROL)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
        
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID_PARENTAL, notification)
    }
    
    fun showAlertNotification(
        context: Context,
        title: String,
        message: String,
        alertType: AlertType = AlertType.INFO
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ALERTS)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(getAlertIcon(alertType))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(getAlertPriority(alertType))
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
        
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID_ALERTS, notification)
    }
    
    fun showRewardNotification(
        context: Context,
        title: String,
        message: String,
        points: Int? = null
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_REWARDS)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_reward)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SOCIAL)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
        
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID_REWARDS, notification)
    }
    
    fun showTimeLimitNotification(
        context: Context,
        childName: String,
        appName: String,
        remainingTime: Long
    ) {
        val title = "Límite de tiempo alcanzado"
        val message = "$childName ha alcanzado el límite de tiempo para $appName. Tiempo restante: ${formatTime(remainingTime)}"
        
        showParentalControlNotification(context, title, message, childName)
    }
    
    fun showAppBlockedNotification(
        context: Context,
        childName: String,
        appName: String
    ) {
        val title = "Aplicación bloqueada"
        val message = "$childName intentó usar $appName que está bloqueada"
        
        showParentalControlNotification(context, title, message, childName)
    }
    
    fun showLocationAlertNotification(
        context: Context,
        childName: String,
        location: String,
        alertType: LocationAlertType
    ) {
        val title = when (alertType) {
            LocationAlertType.ENTER_RESTRICTED -> "Zona restringida"
            LocationAlertType.EXIT_ALLOWED -> "Salió de zona permitida"
            LocationAlertType.EMERGENCY -> "Alerta de emergencia"
        }
        
        val message = when (alertType) {
            LocationAlertType.ENTER_RESTRICTED -> "$childName entró a una zona restringida: $location"
            LocationAlertType.EXIT_ALLOWED -> "$childName salió de una zona permitida: $location"
            LocationAlertType.EMERGENCY -> "$childName activó el botón de pánico desde: $location"
        }
        
        showAlertNotification(context, title, message, AlertType.WARNING)
    }
    
    fun showAchievementNotification(
        context: Context,
        childName: String,
        achievementName: String,
        points: Int
    ) {
        val title = "¡Logro desbloqueado!"
        val message = "$childName desbloqueó el logro '$achievementName' y ganó $points puntos"
        
        showRewardNotification(context, title, message, points)
    }
    
    fun showRewardEarnedNotification(
        context: Context,
        childName: String,
        rewardName: String,
        points: Int
    ) {
        val title = "¡Recompensa ganada!"
        val message = "$childName ganó la recompensa '$rewardName' por $points puntos"
        
        showRewardNotification(context, title, message, points)
    }
    
    private fun getAlertIcon(alertType: AlertType): Int {
        return when (alertType) {
            AlertType.INFO -> R.drawable.ic_info
            AlertType.WARNING -> R.drawable.ic_warning
            AlertType.ERROR -> R.drawable.ic_error
            AlertType.SUCCESS -> R.drawable.ic_success
        }
    }
    
    private fun getAlertPriority(alertType: AlertType): Int {
        return when (alertType) {
            AlertType.INFO -> NotificationCompat.PRIORITY_DEFAULT
            AlertType.WARNING -> NotificationCompat.PRIORITY_HIGH
            AlertType.ERROR -> NotificationCompat.PRIORITY_HIGH
            AlertType.SUCCESS -> NotificationCompat.PRIORITY_LOW
        }
    }
    
    private fun formatTime(timeInMillis: Long): String {
        val hours = timeInMillis / (1000 * 60 * 60)
        val minutes = (timeInMillis % (1000 * 60 * 60)) / (1000 * 60)
        val seconds = (timeInMillis % (1000 * 60)) / 1000
        
        return when {
            hours > 0 -> "${hours}h ${minutes}m ${seconds}s"
            minutes > 0 -> "${minutes}m ${seconds}s"
            else -> "${seconds}s"
        }
    }
    
    enum class AlertType {
        INFO,
        WARNING,
        ERROR,
        SUCCESS
    }
    
    enum class LocationAlertType {
        ENTER_RESTRICTED,
        EXIT_ALLOWED,
        EMERGENCY
    }
}