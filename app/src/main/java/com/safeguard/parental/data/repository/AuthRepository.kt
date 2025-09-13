package com.safeguard.parental.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.safeguard.parental.data.model.User
import com.safeguard.parental.data.model.UserRole
import com.safeguard.parental.utils.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRepository {
    
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    
    val currentUser: FirebaseUser?
        get() = auth.currentUser
    
    fun getAuthState(): Flow<Boolean> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }
    
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createUserWithEmailAndPassword(
        email: String, 
        password: String, 
        displayName: String
    ): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user!!
            
            // Create user profile in Firestore
            val user = User(
                id = firebaseUser.uid,
                email = email,
                displayName = displayName,
                role = UserRole.PARENT
            )
            
            firestore.collection(Constants.COLLECTION_USERS)
                .document(firebaseUser.uid)
                .set(user)
                .await()
            
            Result.success(firebaseUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun signOut(): Result<Unit> {
        return try {
            auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCurrentUserProfile(): Result<User?> {
        return try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userDoc = firestore.collection(Constants.COLLECTION_USERS)
                    .document(currentUser.uid)
                    .get()
                    .await()
                
                val user = userDoc.toObject(User::class.java)
                Result.success(user)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateUserProfile(user: User): Result<Unit> {
        return try {
            firestore.collection(Constants.COLLECTION_USERS)
                .document(user.id)
                .set(user)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteAccount(): Result<Unit> {
        return try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // Delete user data from Firestore
                firestore.collection(Constants.COLLECTION_USERS)
                    .document(currentUser.uid)
                    .delete()
                    .await()
                
                // Delete Firebase Auth account
                currentUser.delete().await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}