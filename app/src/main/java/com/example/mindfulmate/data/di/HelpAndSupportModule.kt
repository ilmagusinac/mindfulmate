package com.example.mindfulmate.data.di

import com.example.mindfulmate.data.repository.help_support.HelpAndSupportRepositoryImpl
import com.example.mindfulmate.data.service.help_support.HelpAndSupportService
import com.example.mindfulmate.data.service.help_support.HelpAndSupportServiceImpl
import com.example.mindfulmate.domain.repository.help_support.HelpAndSupportRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HelpAndSupportModule {

    @Binds
    @Singleton
    abstract fun helpAndSupportRepository(
        helpAndSupportRepositoryImpl: HelpAndSupportRepositoryImpl
    ): HelpAndSupportRepository

    @Binds
    @Singleton
    abstract fun helpAndSupportService(
        helpAndSupportServiceImpl: HelpAndSupportServiceImpl
    ): HelpAndSupportService
}
