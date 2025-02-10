package com.example.mindfulmate.data.di

import com.example.mindfulmate.data.repository.emergency_contact.EmergencyContactRepositoryImpl
import com.example.mindfulmate.data.service.emergency_contact.EmergencyContactService
import com.example.mindfulmate.data.service.emergency_contact.EmergencyContactServiceImpl
import com.example.mindfulmate.domain.repository.emergency_contact.EmergencyContactRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EmergencyContactModule {

    @Binds
    @Singleton
    abstract fun emergencyContactRepository(
        emergencyContactRepositoryImpl: EmergencyContactRepositoryImpl
    ): EmergencyContactRepository

    @Binds
    @Singleton
    abstract fun emergencyContactService(
        emergencyContactServiceImpl: EmergencyContactServiceImpl
    ): EmergencyContactService
}
