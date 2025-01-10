package com.example.mindfulmate.data.di

import android.content.Context
import com.example.mindfulmate.presentation.work.daily_checkin.CheckInStateManager
import com.example.mindfulmate.presentation.work.daily_checkin.NotificationPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationPreferenceManagerModule {

    @Provides
    @Singleton
    fun provideNotificationPreferenceManager(
        @ApplicationContext context: Context
    ): NotificationPreferenceManager {
        return NotificationPreferenceManager(context)
    }

    @Provides
    @Singleton
    fun provideCheckInStateManager(
        @ApplicationContext context: Context
    ): CheckInStateManager {
        return CheckInStateManager(context)
    }
}
