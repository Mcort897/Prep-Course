package com.parentalcontrolpro.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.parentalcontrolpro.data.dao.*
import com.parentalcontrolpro.data.model.*

@Database(
    entities = [
        User::class,
        AppUsage::class,
        WebActivity::class,
        LocationData::class,
        CallLog::class,
        Message::class,
        TimeLimit::class,
        Alert::class,
        Report::class,
        SafeZone::class,
        DangerZone::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun appUsageDao(): AppUsageDao
    abstract fun webActivityDao(): WebActivityDao
    abstract fun locationDao(): LocationDao
    abstract fun callLogDao(): CallLogDao
    abstract fun messageDao(): MessageDao
    abstract fun timeLimitDao(): TimeLimitDao
    abstract fun alertDao(): AlertDao
    abstract fun reportDao(): ReportDao
    abstract fun safeZoneDao(): SafeZoneDao
    abstract fun dangerZoneDao(): DangerZoneDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "parental_control_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}