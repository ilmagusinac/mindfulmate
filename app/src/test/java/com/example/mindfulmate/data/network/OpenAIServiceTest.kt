package com.example.mindfulmate.data.network

import com.example.mindfulmate.data.network.openai.ChatMessage
import com.example.mindfulmate.data.network.openai.ChatRequest
import com.example.mindfulmate.data.network.openai.ChatResponse
import com.example.mindfulmate.data.network.openai.Choice
import com.example.mindfulmate.data.network.openai.OpenAIService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class OpenAIServiceTest {

    private val openAIService: OpenAIService = mockk()

    @Test
    fun `sendMessage returns successful response`() = runTest {
        val request = ChatRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(
                ChatMessage(role = "user", content = "Hello, how are you?")
            )
        )
        val response = ChatResponse(
            choices = listOf(
                Choice(
                    message = ChatMessage(
                        role = "assistant",
                        content = "I'm good! How can I help you?"
                    )
                )
            )
        )
        coEvery { openAIService.sendMessage(request) } returns Response.success(response)

        val result = openAIService.sendMessage(request)

        assertEquals(true, result.isSuccessful)
        assertEquals(
            "I'm good! How can I help you?",
            result.body()?.choices?.first()?.message?.content
        )
    }

    @Test
    fun `sendMessage returns error response`() = runTest {
        val request = ChatRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(
                ChatMessage(role = "user", content = "Hello, how are you?")
            )
        )
        val errorBody = ResponseBody.create(null, "Internal Server Error")
        coEvery { openAIService.sendMessage(request) } returns Response.error(500, errorBody)

        val result = openAIService.sendMessage(request)

        assertEquals(false, result.isSuccessful)
        assertEquals(500, result.code())
        assertEquals("Internal Server Error", result.errorBody()?.string())
    }
}
