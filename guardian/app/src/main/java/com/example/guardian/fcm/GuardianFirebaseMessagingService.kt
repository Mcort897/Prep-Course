package com.example.guardian.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class GuardianFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        // Could save token to Firestore under the user document if needed
    }

    override fun onMessageReceived(message: RemoteMessage) {
        // Handle remote commands later (e.g., request immediate sync)
    }
}

