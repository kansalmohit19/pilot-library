package com.mohit.locationlibrary.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.mohit.locationlibrary.data.LocationDao
import com.mohit.locationlibrary.data.LocationDatabase
import com.mohit.locationlibrary.data.LocationRepository
import com.mohit.locationlibrary.tracker.LocationTracker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        LocationDatabase.getDatabase(context)

    @Provides
    fun provideDao(db: LocationDatabase) = db.locationDao()

    @Provides
    @Singleton
    fun provideLocationRepo(
        tracker: LocationTracker, dao: LocationDao
    ) = LocationRepository(tracker, dao)
}