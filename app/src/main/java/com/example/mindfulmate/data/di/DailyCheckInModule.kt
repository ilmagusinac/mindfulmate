package com.example.mindfulmate.data.di

import com.example.mindfulmate.data.repository.daily_checkin.DailyCheckInRepositoryImpl
import com.example.mindfulmate.data.service.daily_checkin.DailyCheckInService
import com.example.mindfulmate.data.service.daily_checkin.DailyCheckInServiceImpl
import com.example.mindfulmate.domain.repository.daily_checkin.DailyCheckInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DailyCheckInModule {

    @Binds
    @Singleton
    abstract fun dailyCheckInRepository(
        dailyCheckInRepositoryImpl: DailyCheckInRepositoryImpl
    ): DailyCheckInRepository

    @Binds
    @Singleton
    abstract fun dailyCheckInService(
        dailyCheckInServiceImpl: DailyCheckInServiceImpl
    ): DailyCheckInService
}
