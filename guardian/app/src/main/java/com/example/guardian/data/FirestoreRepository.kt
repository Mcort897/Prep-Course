package com.example.guardian.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class LocationSample(
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long
)

data class UsageSample(
    val packageName: String,
    val totalTimeMs: Long,
    val lastTimeUsed: Long,
    val timestamp: Long
)

class FirestoreRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private fun userDocPath(): String {
        val uid = auth.currentUser?.uid ?: "no-auth"
        return "users/$uid"
    }

    suspend fun saveLocation(sample: LocationSample) {
        firestore.document(userDocPath())
            .collection("locations")
            .add(sample)
            .await()
    }

    suspend fun saveUsage(samples: List<UsageSample>) {
        val batch = firestore.batch()
        val col = firestore.document(userDocPath()).collection("usage")
        samples.forEach { sample ->
            batch.set(col.document(), sample)
        }
        batch.commit().await()
    }
}

