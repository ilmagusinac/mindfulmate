package com.example.mindfulmate.data.di

import com.example.mindfulmate.data.network.openai.OpenAIService
import com.example.mindfulmate.data.network.openai.RetrofitClient
import com.example.mindfulmate.data.repository.openai.ChatRepositoryImpl
import com.example.mindfulmate.data.util.constants.MentalHealthKeywords
import com.example.mindfulmate.domain.model.openai.IntentHandler
import com.example.mindfulmate.domain.repository.openai.ChatRepository
import com.example.mindfulmate.domain.usecase.openai.ProcessMessageUseCase
import com.example.mindfulmate.domain.usecase.openai.SendMessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OpenChatAIModule {

    @Provides
    @Singleton
    fun provideOpenAIService(): OpenAIService {
        return RetrofitClient.apiService
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        apiService: OpenAIService
    ): ChatRepository {
        return ChatRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSendMessageUseCase(
        repository: ChatRepository
    ): SendMessageUseCase {
        return SendMessageUseCase(repository)
    }

    @Provides
    fun provideProcessMessageUseCase(chatRepository: ChatRepository, intentHandler: IntentHandler): ProcessMessageUseCase {
        return ProcessMessageUseCase(chatRepository, intentHandler)
    }

    @Provides
    @Singleton
    fun provideIntentHandler(): IntentHandler {
        return IntentHandler(MentalHealthKeywords.MENTAL_HEALTH_KEYWORDS)
    }
}
