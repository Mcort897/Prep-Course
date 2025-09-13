package com.example.guardian.location

import android.app.Notification
import androidx.core.app.NotificationCompat
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import com.example.guardian.R
import com.example.guardian.data.FirestoreRepository
import com.example.guardian.data.LocationSample
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LocationService : Service() {
    private val fused by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private var repo: FirestoreRepository? = null
    private var serviceScope: CoroutineScope? = null

    private val callback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val location = result.lastLocation ?: return
            saveLocation(location)
        }
    }

    override fun onCreate() {
        super.onCreate()
        repo = FirestoreRepository(Firebase.auth, Firebase.firestore)
        serviceScope = CoroutineScope(Dispatchers.IO + Job())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, buildNotification())
        startLocationUpdates()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        fused.removeLocationUpdates(callback)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, "guardian_location")
            .setContentTitle(getString(R.string.notification_location_title))
            .setContentText(getString(R.string.notification_location_text))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true)
            .build()
    }

    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(10_000L)
            .setMinUpdateIntervalMillis(5_000L)
            .setMinUpdateDistanceMeters(25f)
            .build()
        fused.requestLocationUpdates(request, callback, mainLooper)
    }

    private fun saveLocation(location: Location) {
        val sample = LocationSample(
            latitude = location.latitude,
            longitude = location.longitude,
            timestamp = System.currentTimeMillis()
        )
        serviceScope?.launch {
            try {
                repo?.saveLocation(sample)
            } catch (_: Exception) { }
        }
    }
}

