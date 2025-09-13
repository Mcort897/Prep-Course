package com.safeguard.parental.services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.safeguard.parental.MainActivity
import com.safeguard.parental.R
import com.safeguard.parental.SafeGuardApplication
import com.safeguard.parental.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LocationService : Service() {
    
    private val notificationId = Constants.NOTIFICATION_ID_LOCATION
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    
    private val locationRepository by lazy {
        (application as SafeGuardApplication).locationRepository
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onCreate() {
        super.onCreate()
        
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationRequest()
        createLocationCallback()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_LOCATION_UPDATES -> startLocationUpdates()
            ACTION_STOP_LOCATION_UPDATES -> stopLocationUpdates()
        }
        
        return START_STICKY
    }
    
    private fun startLocationUpdates() {
        val notification = createNotification()
        startForeground(notificationId, notification)
        
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            // Handle permission not granted
            stopSelf()
        }
    }
    
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        stopForeground(true)
        stopSelf()
    }
    
    private fun createLocationRequest() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, Constants.LOCATION_UPDATE_INTERVAL)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(Constants.LOCATION_UPDATE_INTERVAL / 2)
            .setMaxUpdateDelayMillis(Constants.LOCATION_UPDATE_INTERVAL * 2)
            .build()
    }
    
    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                
                locationResult.lastLocation?.let { location ->
                    handleLocationUpdate(location)
                }
            }
        }
    }
    
    private fun handleLocationUpdate(location: Location) {
        serviceScope.launch {
            try {
                // Get current child ID (in a real app, this would be determined by the device)
                val childId = getCurrentChildId()
                
                if (childId.isNotEmpty()) {
                    val locationData = com.safeguard.parental.data.model.Location(
                        childId = childId,
                        latitude = location.latitude,
                        longitude = location.longitude,
                        accuracy = location.accuracy,
                        altitude = location.altitude,
                        speed = location.speed,
                        bearing = location.bearing,
                        provider = location.provider ?: "fused",
                        batteryLevel = getBatteryLevel(),
                        isCharging = isDeviceCharging()
                    )
                    
                    locationRepository.saveLocation(locationData)
                }
            } catch (e: Exception) {
                // Log error
            }
        }
    }
    
    private fun getCurrentChildId(): String {
        // In a real implementation, this would get the child ID associated with this device
        // For demo purposes, return a placeholder
        return "demo_child_id"
    }
    
    private fun getBatteryLevel(): Int {
        // Implementation to get battery level
        return 100 // Placeholder
    }
    
    private fun isDeviceCharging(): Boolean {
        // Implementation to check if device is charging
        return false // Placeholder
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_LOCATION)
            .setContentTitle(getString(R.string.notification_location_title))
            .setContentText("Compartiendo ubicación de forma segura")
            .setSmallIcon(R.drawable.ic_location_on)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    
    companion object {
        const val ACTION_START_LOCATION_UPDATES = "start_location_updates"
        const val ACTION_STOP_LOCATION_UPDATES = "stop_location_updates"
    }
}