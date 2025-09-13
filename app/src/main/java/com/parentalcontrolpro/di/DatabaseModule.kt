package com.parentalcontrolpro.di

import android.content.Context
import androidx.room.Room
import com.parentalcontrolpro.data.database.AppDatabase
import com.parentalcontrolpro.data.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "parental_control_database"
        ).build()
    }
    
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
    
    @Provides
    fun provideAppUsageDao(database: AppDatabase): AppUsageDao = database.appUsageDao()
    
    @Provides
    fun provideWebActivityDao(database: AppDatabase): WebActivityDao = database.webActivityDao()
    
    @Provides
    fun provideLocationDao(database: AppDatabase): LocationDao = database.locationDao()
    
    @Provides
    fun provideCallLogDao(database: AppDatabase): CallLogDao = database.callLogDao()
    
    @Provides
    fun provideMessageDao(database: AppDatabase): MessageDao = database.messageDao()
    
    @Provides
    fun provideTimeLimitDao(database: AppDatabase): TimeLimitDao = database.timeLimitDao()
    
    @Provides
    fun provideAlertDao(database: AppDatabase): AlertDao = database.alertDao()
    
    @Provides
    fun provideReportDao(database: AppDatabase): ReportDao = database.reportDao()
    
    @Provides
    fun provideSafeZoneDao(database: AppDatabase): SafeZoneDao = database.safeZoneDao()
    
    @Provides
    fun provideDangerZoneDao(database: AppDatabase): DangerZoneDao = database.dangerZoneDao()
}