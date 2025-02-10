package com.example.mindfulmate.data.network.openai

import com.example.mindfulmate.data.util.constants.OpenAIValues

data class ChatRequest(
    val model: String = OpenAIValues.MODEL_NAME,
    val messages: List<ChatMessage>
)
