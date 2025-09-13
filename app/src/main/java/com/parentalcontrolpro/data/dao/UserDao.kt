package com.parentalcontrolpro.data.dao

import androidx.room.*
import com.parentalcontrolpro.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    
    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserById(userId: String): User?
    
    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?
    
    @Query("SELECT * FROM user WHERE role = :role")
    fun getUsersByRole(role: String): Flow<List<User>>
    
    @Query("SELECT * FROM user WHERE parentId = :parentId")
    fun getChildrenByParentId(parentId: String): Flow<List<User>>
    
    @Query("SELECT * FROM user WHERE isActive = 1")
    fun getAllActiveUsers(): Flow<List<User>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)
    
    @Update
    suspend fun updateUser(user: User)
    
    @Delete
    suspend fun deleteUser(user: User)
    
    @Query("DELETE FROM user WHERE id = :userId")
    suspend fun deleteUserById(userId: String)
    
    @Query("UPDATE user SET isActive = :isActive WHERE id = :userId")
    suspend fun updateUserActiveStatus(userId: String, isActive: Boolean)
    
    @Query("UPDATE user SET lastLogin = :lastLogin WHERE id = :userId")
    suspend fun updateLastLogin(userId: String, lastLogin: Long)
    
    @Query("SELECT COUNT(*) FROM user WHERE role = :role")
    suspend fun getUserCountByRole(role: String): Int
    
    @Query("SELECT COUNT(*) FROM user WHERE parentId = :parentId")
    suspend fun getChildrenCountByParentId(parentId: String): Int
}