package com.example.guardian

import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.guardian.location.LocationService
import com.example.guardian.usage.AppUsageWorker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
                MainScreen(
                    isUsageStatsGranted = isUsageStatsPermissionGranted(),
                    onRequestUsageAccess = { openUsageAccessSettings() },
                    permissionStatus = permissionStatus(),
                    onRequestPermissions = { requestAllPermissions() },
                    onStartLocation = { startLocation() },
                    onStopLocation = { stopLocation() },
                    onScheduleUsageSync = { scheduleUsageSync() },
                    userDisplay = Firebase.auth.currentUser?.uid ?: "no-auth"
                )
            }
        }

        lifecycleScope.launch {
            ensureAnonymousSignIn()
        }
    }

    private fun ensureAnonymousSignIn() {
        val user = Firebase.auth.currentUser
        if (user == null) {
            Firebase.auth.signInAnonymously()
        }
    }

    private fun permissionStatus(): String {
        val needed = mutableListOf<String>()
        val permissions = buildList {
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            if (Build.VERSION.SDK_INT >= 29) add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            if (Build.VERSION.SDK_INT >= 33) add(Manifest.permission.POST_NOTIFICATIONS)
        }
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                needed.add(perm.substringAfterLast('.'))
            }
        }
        return if (needed.isEmpty()) "ok" else needed.joinToString(", ")
    }

    private fun requestAllPermissions() {
        val permissions = buildList {
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            if (Build.VERSION.SDK_INT >= 29) add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            if (Build.VERSION.SDK_INT >= 33) add(Manifest.permission.POST_NOTIFICATIONS)
        }.toTypedArray()
        permissionLauncher.launch(permissions)
    }

    private fun isUsageStatsPermissionGranted(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                packageName
            )
        } else {
            @Suppress("DEPRECATION")
            appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                packageName
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun openUsageAccessSettings() {
        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).apply {
            data = Uri.parse("package:$packageName")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun startLocation() {
        ContextCompat.startForegroundService(this, Intent(this, LocationService::class.java))
    }

    private fun stopLocation() {
        stopService(Intent(this, LocationService::class.java))
    }

    private fun scheduleUsageSync() {
        val work = PeriodicWorkRequestBuilder<AppUsageWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "usage_sync",
            ExistingPeriodicWorkPolicy.UPDATE,
            work
        )
    }
}

@Composable
private fun MainScreen(
    isUsageStatsGranted: Boolean,
    onRequestUsageAccess: () -> Unit,
    permissionStatus: String,
    onRequestPermissions: () -> Unit,
    onStartLocation: () -> Unit,
    onStopLocation: () -> Unit,
    onScheduleUsageSync: () -> Unit,
    userDisplay: String
) {
    Column(Modifier.padding(16.dp)) {
        Text(text = stringResource(id = R.string.signed_in_as, userDisplay))
        Spacer(Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.permission_status, permissionStatus))
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRequestPermissions) { Text(text = stringResource(id = R.string.action_request_permissions)) }
        Spacer(Modifier.height(16.dp))
        Text(text = if (isUsageStatsGranted) "Uso de apps: permitido" else "Uso de apps: NO permitido")
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRequestUsageAccess) { Text(text = stringResource(id = R.string.action_open_usage_access)) }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onStartLocation) { Text(text = stringResource(id = R.string.action_start_location)) }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onStopLocation) { Text(text = stringResource(id = R.string.action_stop_location)) }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onScheduleUsageSync) { Text(text = stringResource(id = R.string.action_schedule_usage_sync)) }
    }
}

