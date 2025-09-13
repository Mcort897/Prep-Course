package com.parentalcontrolpro

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ParentalControlApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Initialize any global components here
    }
}