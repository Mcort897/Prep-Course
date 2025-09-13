package com.parentalcontrolpro.data.dao

import androidx.room.*
import com.parentalcontrolpro.data.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    
    @Query("SELECT * FROM message WHERE userId = :userId ORDER BY timestamp DESC")
    fun getMessagesByUserId(userId: String): Flow<List<Message>>
    
    @Query("SELECT * FROM message WHERE userId = :userId AND timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getMessagesByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<Message>>
    
    @Query("SELECT * FROM message WHERE userId = :userId AND isBlocked = 1 ORDER BY timestamp DESC")
    fun getBlockedMessagesByUserId(userId: String): Flow<List<Message>>
    
    @Query("SELECT phoneNumber, COUNT(*) as messageCount FROM message WHERE userId = :userId AND timestamp BETWEEN :startDate AND :endDate GROUP BY phoneNumber ORDER BY messageCount DESC")
    suspend fun getMostMessagedNumbersByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): List<MessageSummary>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<Message>)
    
    @Update
    suspend fun updateMessage(message: Message)
    
    @Delete
    suspend fun deleteMessage(message: Message)
    
    @Query("DELETE FROM message WHERE userId = :userId")
    suspend fun deleteMessagesByUserId(userId: String)
    
    data class MessageSummary(
        val phoneNumber: String,
        val messageCount: Int
    )
}