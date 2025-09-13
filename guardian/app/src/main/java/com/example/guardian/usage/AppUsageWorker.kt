package com.example.guardian.usage

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.guardian.data.FirestoreRepository
import com.example.guardian.data.UsageSample
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AppUsageWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    private val repo = FirestoreRepository(Firebase.auth, Firebase.firestore)

    override suspend fun doWork(): Result {
        return try {
            val now = System.currentTimeMillis()
            val start = now - 24L * 60 * 60 * 1000
            val usageSamples = queryUsage(applicationContext, start, now)
            if (usageSamples.isNotEmpty()) {
                repo.saveUsage(usageSamples)
            }
            Result.success()
        } catch (t: Throwable) {
            Result.retry()
        }
    }

    private fun queryUsage(context: Context, start: Long, end: Long): List<UsageSample> {
        val manager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val list: List<UsageStats> = manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, start, end)
        val now = System.currentTimeMillis()
        return list.map { stats ->
            UsageSample(
                packageName = stats.packageName,
                totalTimeMs = stats.totalTimeInForeground,
                lastTimeUsed = stats.lastTimeUsed,
                timestamp = now
            )
        }.filter { it.totalTimeMs > 0 }
    }
}

