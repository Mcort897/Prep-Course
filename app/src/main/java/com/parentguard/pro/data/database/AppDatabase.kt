package com.parentguard.pro.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.parentguard.pro.data.dao.*
import com.parentguard.pro.data.model.*

@Database(
    entities = [
        User::class,
        Child::class,
        AppUsage::class,
        AppUsageDaily::class,
        AppUsageWeekly::class,
        AppUsageMonthly::class,
        TimeLimit::class,
        TimeLimitUsage::class,
        TimeLimitViolation::class,
        Location::class,
        Reward::class,
        Achievement::class,
        WebFilter::class,
        WebFilterRule::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun childDao(): ChildDao
    abstract fun appUsageDao(): AppUsageDao
    abstract fun timeLimitDao(): TimeLimitDao
    abstract fun locationDao(): LocationDao
    abstract fun rewardDao(): RewardDao
    abstract fun webFilterDao(): WebFilterDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "parent_guard_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}