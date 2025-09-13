package com.safeguard.parental.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.safeguard.parental.data.local.dao.AppUsageDao
import com.safeguard.parental.data.local.dao.ChildDao
import com.safeguard.parental.data.local.dao.LocationDao
import com.safeguard.parental.data.local.entity.AppUsageEntity
import com.safeguard.parental.data.local.entity.ChildEntity
import com.safeguard.parental.data.local.entity.LocationEntity
import com.safeguard.parental.data.local.entity.SafeZoneEntity

@Database(
    entities = [
        ChildEntity::class,
        LocationEntity::class,
        SafeZoneEntity::class,
        AppUsageEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SafeGuardDatabase : RoomDatabase() {
    
    abstract fun childDao(): ChildDao
    abstract fun locationDao(): LocationDao
    abstract fun appUsageDao(): AppUsageDao
    
    companion object {
        @Volatile
        private var INSTANCE: SafeGuardDatabase? = null
        
        fun getDatabase(context: Context): SafeGuardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SafeGuardDatabase::class.java,
                    "safeguard_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}