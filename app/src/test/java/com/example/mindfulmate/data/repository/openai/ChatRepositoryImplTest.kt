package com.example.mindfulmate.data.repository.openai

import com.example.mindfulmate.data.network.openai.ChatMessage
import com.example.mindfulmate.data.network.openai.ChatResponse
import com.example.mindfulmate.data.network.openai.OpenAIService
import com.example.mindfulmate.data.repository.openai.ChatRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ChatRepositoryImplTest {

    private lateinit var openAIService: OpenAIService
    private lateinit var chatRepository: ChatRepositoryImpl

    @Before
    fun setUp() {
        openAIService = mockk()
        chatRepository = ChatRepositoryImpl(openAIService)
    }

    @Test
    fun `sendMessage calls service and returns response`() = runBlocking {
        // Arrange
        val messages = listOf(ChatMessage("user", "Hello"))
        val chatResponse = ChatResponse(choices = listOf())
        val response = Response.success(chatResponse) // Wrap ChatResponse in Response
        coEvery { openAIService.sendMessage(any()) } returns response

        // Act
        val result = chatRepository.sendMessage(messages)

        // Assert
        coVerify { openAIService.sendMessage(any()) }
        assertEquals(chatResponse, result)
    }

}
