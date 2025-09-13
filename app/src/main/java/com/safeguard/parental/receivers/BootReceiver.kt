package com.safeguard.parental.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.safeguard.parental.services.MonitoringService
import com.safeguard.parental.services.LocationService

class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED,
            Intent.ACTION_PACKAGE_REPLACED -> {
                // Start monitoring services after boot
                startServices(context)
            }
        }
    }
    
    private fun startServices(context: Context) {
        // Check if monitoring should be enabled (based on user preferences)
        val prefs = context.getSharedPreferences("safeguard_prefs", Context.MODE_PRIVATE)
        val monitoringEnabled = prefs.getBoolean("monitoring_enabled", false)
        val locationEnabled = prefs.getBoolean("location_enabled", false)
        
        if (monitoringEnabled) {
            val monitoringIntent = Intent(context, MonitoringService::class.java).apply {
                action = MonitoringService.ACTION_START_MONITORING
            }
            context.startForegroundService(monitoringIntent)
        }
        
        if (locationEnabled) {
            val locationIntent = Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_START_LOCATION_UPDATES
            }
            context.startForegroundService(locationIntent)
        }
    }
}