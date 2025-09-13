package com.parentguard.pro.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.parentguard.pro.data.model.AppUsage
import com.parentguard.pro.data.model.AppCategory
import com.parentguard.pro.data.repository.AppUsageRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ParentalControlAccessibilityService : AccessibilityService() {
    
    @Inject
    lateinit var appUsageRepository: AppUsageRepository
    
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var currentAppPackage: String? = null
    private var currentAppName: String? = null
    private var appStartTime: Long = 0
    private var isAppBlocked = false
    
    companion object {
        private const val TAG = "ParentalControlAccessibilityService"
        private var instance: ParentalControlAccessibilityService? = null
        
        fun getInstance(): ParentalControlAccessibilityService? = instance
    }
    
    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "Accessibility service connected")
        instance = this
        
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or
                        AccessibilityEvent.TYPE_VIEW_FOCUSED or
                        AccessibilityEvent.TYPE_VIEW_CLICKED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
            notificationTimeout = 0
        }
        
        serviceInfo = info
    }
    
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let { handleAccessibilityEvent(it) }
    }
    
    private fun handleAccessibilityEvent(event: AccessibilityEvent) {
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                handleWindowStateChanged(event)
            }
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                handleViewFocused(event)
            }
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                handleViewClicked(event)
            }
        }
    }
    
    private fun handleWindowStateChanged(event: AccessibilityEvent) {
        val packageName = event.packageName?.toString()
        val className = event.className?.toString()
        
        if (packageName != null && packageName != currentAppPackage) {
            Log.d(TAG, "App changed: $packageName")
            
            // Save previous app usage
            if (currentAppPackage != null && appStartTime > 0) {
                saveAppUsage()
            }
            
            // Start monitoring new app
            currentAppPackage = packageName
            currentAppName = getAppName(packageName)
            appStartTime = System.currentTimeMillis()
            isAppBlocked = false
            
            // Check if app should be blocked
            checkAppBlocking(packageName)
        }
    }
    
    private fun handleViewFocused(event: AccessibilityEvent) {
        // Handle view focus events if needed
    }
    
    private fun handleViewClicked(event: AccessibilityEvent) {
        // Handle click events if needed
    }
    
    private fun checkAppBlocking(packageName: String) {
        serviceScope.launch {
            try {
                // Check if app is in blocked list
                // This would query the database for blocked apps
                val isBlocked = isAppBlocked(packageName)
                
                if (isBlocked) {
                    Log.d(TAG, "App is blocked: $packageName")
                    blockApp(packageName)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error checking app blocking", e)
            }
        }
    }
    
    private suspend fun isAppBlocked(packageName: String): Boolean {
        // TODO: Implement actual blocking check from database
        // This would check:
        // 1. If app is in blocked list
        // 2. If time limits are exceeded
        // 3. If app is allowed during current time
        return false
    }
    
    private fun blockApp(packageName: String) {
        isAppBlocked = true
        
        // Show blocking dialog
        showBlockingDialog(packageName)
        
        // Go to home screen
        goToHomeScreen()
    }
    
    private fun showBlockingDialog(packageName: String) {
        // TODO: Implement blocking dialog
        Log.d(TAG, "Showing blocking dialog for: $packageName")
    }
    
    private fun goToHomeScreen() {
        val homeIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(homeIntent)
    }
    
    private fun getAppName(packageName: String): String {
        // TODO: Implement app name resolution
        return packageName
    }
    
    private suspend fun saveAppUsage() {
        if (currentAppPackage == null || appStartTime <= 0) return
        
        try {
            val usageTime = System.currentTimeMillis() - appStartTime
            val appUsage = AppUsage(
                id = UUID.randomUUID().toString(),
                childId = getCurrentChildId(), // TODO: Get current child ID
                packageName = currentAppPackage!!,
                appName = currentAppName ?: currentAppPackage!!,
                usageTime = usageTime,
                sessionCount = 1,
                date = Date(),
                isBlocked = isAppBlocked,
                category = categorizeApp(currentAppPackage!!, currentAppName ?: ""),
                lastUsedAt = Date()
            )
            
            appUsageRepository.insertAppUsage(appUsage)
            Log.d(TAG, "App usage saved: ${appUsage.appName} - ${usageTime}ms")
            
        } catch (e: Exception) {
            Log.e(TAG, "Error saving app usage", e)
        }
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
    
    override fun onInterrupt() {
        Log.d(TAG, "Accessibility service interrupted")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Accessibility service destroyed")
        instance = null
        serviceScope.cancel()
    }
}