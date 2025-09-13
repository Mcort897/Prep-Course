package com.parentguard.pro.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.parentguard.pro.service.AppMonitoringService

class ScreenStateReceiver : BroadcastReceiver() {
    
    companion object {
        private const val TAG = "ScreenStateReceiver"
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_SCREEN_ON -> {
                Log.d(TAG, "Screen turned on")
                handleScreenOn(context)
            }
            Intent.ACTION_SCREEN_OFF -> {
                Log.d(TAG, "Screen turned off")
                handleScreenOff(context)
            }
            Intent.ACTION_USER_PRESENT -> {
                Log.d(TAG, "User present")
                handleUserPresent(context)
            }
        }
    }
    
    private fun handleScreenOn(context: Context) {
        // Screen turned on - resume monitoring
        try {
            AppMonitoringService.startService(context)
        } catch (e: Exception) {
            Log.e(TAG, "Error handling screen on", e)
        }
    }
    
    private fun handleScreenOff(context: Context) {
        // Screen turned off - pause monitoring
        try {
            // TODO: Implement screen off handling
        } catch (e: Exception) {
            Log.e(TAG, "Error handling screen off", e)
        }
    }
    
    private fun handleUserPresent(context: Context) {
        // User is present - resume monitoring
        try {
            AppMonitoringService.startService(context)
        } catch (e: Exception) {
            Log.e(TAG, "Error handling user present", e)
        }
    }
}