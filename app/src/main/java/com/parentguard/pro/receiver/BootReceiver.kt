package com.parentguard.pro.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.parentguard.pro.service.AppMonitoringService

class BootReceiver : BroadcastReceiver() {
    
    companion object {
        private const val TAG = "BootReceiver"
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            "android.intent.action.QUICKBOOT_POWERON" -> {
                Log.d(TAG, "Boot completed, starting services")
                startServices(context)
            }
        }
    }
    
    private fun startServices(context: Context) {
        try {
            // Start app monitoring service
            AppMonitoringService.startService(context)
            Log.d(TAG, "Services started successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error starting services", e)
        }
    }
}