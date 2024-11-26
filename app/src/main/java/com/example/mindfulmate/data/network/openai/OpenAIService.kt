package com.example.mindfulmate.data.network.openai

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIService {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun sendMessage(@Body request: ChatRequest): Response<ChatResponse>
}
