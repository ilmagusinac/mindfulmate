package com.example.mindfulmate.data.di

import com.example.mindfulmate.data.repository.chat.ChatRepositoryImpl
import com.example.mindfulmate.data.service.chat.ChatService
import com.example.mindfulmate.data.service.chat.ChatServiceImpl
import com.example.mindfulmate.domain.repository.chat.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatModule {

    @Binds
    @Singleton
    abstract fun chatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    @Singleton
    abstract fun chatService(
        chatServiceImpl: ChatServiceImpl
    ): ChatService
}
