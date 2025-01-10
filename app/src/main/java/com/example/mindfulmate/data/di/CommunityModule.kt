package com.example.mindfulmate.data.di

import com.example.mindfulmate.data.repository.community.CommunityRepositoryImpl
import com.example.mindfulmate.data.service.community.CommunityService
import com.example.mindfulmate.data.service.community.CommunityServiceImpl
import com.example.mindfulmate.domain.repository.community.CommunityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommunityModule {

    @Binds
    @Singleton
    abstract fun communityRepository(
        communityRepositoryImpl: CommunityRepositoryImpl
    ): CommunityRepository

    @Binds
    @Singleton
    abstract fun communityService(
        communityServiceImpl: CommunityServiceImpl
    ): CommunityService
}
