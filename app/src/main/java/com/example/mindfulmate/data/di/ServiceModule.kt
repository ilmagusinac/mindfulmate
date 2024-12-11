package com.example.mindfulmate.data.di

import com.example.mindfulmate.data.service.auth.AccountService
import com.example.mindfulmate.data.service.auth.AccountServiceImpl
import com.example.mindfulmate.domain.repository.user.UserRepository
import com.example.mindfulmate.data.repository.user.UserRepositoryImpl
import com.example.mindfulmate.data.service.user.UserService
import com.example.mindfulmate.data.service.user.UserServiceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
            accountService: AccountService,
            userService: UserService
        ): UserRepository {
            return UserRepositoryImpl(accountService, userService)
        }

        @Provides
        @Singleton
        fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

        @Provides
        @Singleton
        fun provideFirestoreService(
            firestore: FirebaseFirestore,
            auth: FirebaseAuth
        ): UserService = UserServiceImpl(firestore, auth)
    }
}