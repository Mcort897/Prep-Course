package com.parentguard.pro.di

import android.content.Context
import com.parentguard.pro.data.database.AppDatabase
import com.parentguard.pro.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
    
    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()
    
    @Provides
    fun provideChildDao(database: AppDatabase) = database.childDao()
    
    @Provides
    fun provideAppUsageDao(database: AppDatabase) = database.appUsageDao()
    
    @Provides
    fun provideTimeLimitDao(database: AppDatabase) = database.timeLimitDao()
    
    @Provides
    fun provideLocationDao(database: AppDatabase) = database.locationDao()
    
    @Provides
    fun provideRewardDao(database: AppDatabase) = database.rewardDao()
    
    @Provides
    fun provideWebFilterDao(database: AppDatabase) = database.webFilterDao()
    
    @Provides
    @Singleton
    fun provideUserRepository(userDao: com.parentguard.pro.data.dao.UserDao): UserRepository {
        return UserRepository(userDao)
    }
    
    @Provides
    @Singleton
    fun provideChildRepository(childDao: com.parentguard.pro.data.dao.ChildDao): ChildRepository {
        return ChildRepository(childDao)
    }
    
    @Provides
    @Singleton
    fun provideAppUsageRepository(appUsageDao: com.parentguard.pro.data.dao.AppUsageDao): AppUsageRepository {
        return AppUsageRepository(appUsageDao)
    }
    
    @Provides
    @Singleton
    fun provideTimeLimitRepository(timeLimitDao: com.parentguard.pro.data.dao.TimeLimitDao): TimeLimitRepository {
        return TimeLimitRepository(timeLimitDao)
    }
    
    @Provides
    @Singleton
    fun provideLocationRepository(locationDao: com.parentguard.pro.data.dao.LocationDao): LocationRepository {
        return LocationRepository(locationDao)
    }
    
    @Provides
    @Singleton
    fun provideRewardRepository(rewardDao: com.parentguard.pro.data.dao.RewardDao): RewardRepository {
        return RewardRepository(rewardDao)
    }
    
    @Provides
    @Singleton
    fun provideWebFilterRepository(webFilterDao: com.parentguard.pro.data.dao.WebFilterDao): WebFilterRepository {
        return WebFilterRepository(webFilterDao)
    }
}