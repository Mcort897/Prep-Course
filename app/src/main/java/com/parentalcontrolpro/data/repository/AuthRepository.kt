package com.parentalcontrolpro.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.parentalcontrolpro.data.model.User
import com.parentalcontrolpro.data.model.UserRole
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
    
    fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null
    
    fun getCurrentUserId(): String? = firebaseAuth.currentUser?.uid
    
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            if (firebaseUser != null) {
                val user = getUserFromFirestore(firebaseUser.uid)
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(Exception("User data not found"))
                }
            } else {
                Result.failure(Exception("Authentication failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createUserWithEmailAndPassword(
        email: String, 
        password: String, 
        name: String, 
        role: UserRole
    ): Result<User> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            if (firebaseUser != null) {
                val user = User(
                    id = firebaseUser.uid,
                    email = email,
                    name = name,
                    role = role
                )
                saveUserToFirestore(user)
                Result.success(user)
            } else {
                Result.failure(Exception("User creation failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updatePassword(newPassword: String): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser
            if (user != null) {
                user.updatePassword(newPassword).await()
                Result.success(Unit)
            } else {
                Result.failure(Exception("No user logged in"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateProfile(name: String, profileImage: String? = null): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser
            if (user != null) {
                val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .apply {
                        if (profileImage != null) {
                            // Handle profile image update
                        }
                    }
                    .build()
                
                user.updateProfile(profileUpdates).await()
                
                // Update user in Firestore
                val userRef = firestore.collection("users").document(user.uid)
                val updates = hashMapOf<String, Any>(
                    "name" to name,
                    "lastLogin" to System.currentTimeMillis()
                )
                if (profileImage != null) {
                    updates["profileImage"] = profileImage
                }
                userRef.update(updates).await()
                
                Result.success(Unit)
            } else {
                Result.failure(Exception("No user logged in"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUserFromFirestore(userId: String): User? {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            if (document.exists()) {
                document.toObject(User::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun saveUserToFirestore(user: User): Result<Unit> {
        return try {
            firestore.collection("users").document(user.id).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateUserInFirestore(user: User): Result<Unit> {
        return try {
            firestore.collection("users").document(user.id).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteUserFromFirestore(userId: String): Result<Unit> {
        return try {
            firestore.collection("users").document(userId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getAuthStateFlow(): Flow<FirebaseUser?> = flow {
        firebaseAuth.addAuthStateListener { auth ->
            emit(auth.currentUser)
        }
    }
    
    suspend fun linkChildToParent(childId: String, parentId: String): Result<Unit> {
        return try {
            val childRef = firestore.collection("users").document(childId)
            childRef.update("parentId", parentId).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getChildrenByParentId(parentId: String): Result<List<User>> {
        return try {
            val snapshot = firestore.collection("users")
                .whereEqualTo("parentId", parentId)
                .whereEqualTo("role", UserRole.CHILD.name)
                .get().await()
            
            val children = snapshot.documents.mapNotNull { document ->
                document.toObject(User::class.java)
            }
            Result.success(children)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getParentByChildId(childId: String): Result<User?> {
        return try {
            val childDoc = firestore.collection("users").document(childId).get().await()
            val child = childDoc.toObject(User::class.java)
            if (child?.parentId != null) {
                val parentDoc = firestore.collection("users").document(child.parentId!!).get().await()
                val parent = parentDoc.toObject(User::class.java)
                Result.success(parent)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}