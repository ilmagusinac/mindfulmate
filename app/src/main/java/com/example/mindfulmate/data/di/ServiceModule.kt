package com.example.mindfulmate.data.di

import com.example.mindfulmate.data.service.AccountService
import com.example.mindfulmate.data.service.AccountServiceImpl
import com.example.mindfulmate.domain.repository.user.UserRepository
import com.example.mindfulmate.data.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    companion object {
        @Provides
        @Singleton
        fun provideUserRepository(
            accountService: AccountService
        ): UserRepository {
            return UserRepositoryImpl(accountService)
        }
    }
}