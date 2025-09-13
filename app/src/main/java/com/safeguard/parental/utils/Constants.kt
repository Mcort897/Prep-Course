package com.safeguard.parental.utils

object Constants {
    // Firebase Collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_CHILDREN = "children"
    const val COLLECTION_APP_USAGE = "app_usage"
    const val COLLECTION_LOCATIONS = "locations"
    const val COLLECTION_SAFE_ZONES = "safe_zones"
    const val COLLECTION_REPORTS = "reports"
    
    // SharedPreferences
    const val PREFS_NAME = "safeguard_prefs"
    const val PREF_FIRST_LAUNCH = "first_launch"
    const val PREF_DEVICE_ADMIN_ENABLED = "device_admin_enabled"
    const val PREF_USAGE_ACCESS_ENABLED = "usage_access_enabled"
    const val PREF_LOCATION_ENABLED = "location_enabled"
    
    // Notification Channels
    const val NOTIFICATION_CHANNEL_MONITORING = "monitoring_channel"
    const val NOTIFICATION_CHANNEL_ALERTS = "alerts_channel"
    const val NOTIFICATION_CHANNEL_LOCATION = "location_channel"
    
    // Notification IDs
    const val NOTIFICATION_ID_MONITORING = 1001
    const val NOTIFICATION_ID_LOCATION = 1002
    const val NOTIFICATION_ID_ALERT = 1003
    
    // Work Manager Tags
    const val WORK_TAG_MONITORING = "monitoring_work"
    const val WORK_TAG_LOCATION = "location_work"
    const val WORK_TAG_SYNC = "sync_work"
    
    // Request Codes
    const val REQUEST_CODE_DEVICE_ADMIN = 2001
    const val REQUEST_CODE_USAGE_ACCESS = 2002
    const val REQUEST_CODE_OVERLAY_PERMISSION = 2003
    const val REQUEST_CODE_LOCATION_PERMISSION = 2004
    
    // Time Constants
    const val LOCATION_UPDATE_INTERVAL = 15 * 60 * 1000L // 15 minutes
    const val MONITORING_INTERVAL = 30 * 1000L // 30 seconds
    const val SYNC_INTERVAL = 5 * 60 * 1000L // 5 minutes
    
    // App Usage Limits (in minutes)
    const val DEFAULT_DAILY_LIMIT = 120 // 2 hours
    const val WARNING_THRESHOLD = 0.8f // 80% of limit
    
    // Location Settings
    const val DEFAULT_SAFE_ZONE_RADIUS = 100.0 // meters
    const val LOCATION_ACCURACY_THRESHOLD = 50.0 // meters
    
    // App Categories
    val SOCIAL_MEDIA_PACKAGES = listOf(
        "com.facebook.katana",
        "com.instagram.android",
        "com.twitter.android",
        "com.snapchat.android",
        "com.zhiliaoapp.musically" // TikTok
    )
    
    val GAME_PACKAGES = listOf(
        "com.supercell.clashofclans",
        "com.king.candycrushsaga",
        "com.mojang.minecraftpe",
        "com.roblox.client"
    )
    
    val EDUCATIONAL_PACKAGES = listOf(
        "com.duolingo",
        "org.khanacademy.android",
        "com.google.android.apps.classroom"
    )
    
    // Web Content Filtering
    val BLOCKED_DOMAINS = listOf(
        "adult-content.com",
        "violence.com",
        "gambling.com"
    )
    
    val SAFE_SEARCH_ENGINES = listOf(
        "https://www.google.com/search?safe=strict&q=",
        "https://www.bing.com/search?adlt=strict&q=",
        "https://search.yahoo.com/search?vm=p&q="
    )
}