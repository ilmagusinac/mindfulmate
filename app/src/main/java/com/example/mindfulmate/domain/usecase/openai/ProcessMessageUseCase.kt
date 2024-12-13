package com.example.mindfulmate.domain.usecase.openai

import com.example.mindfulmate.data.network.openai.ChatMessage
import com.example.mindfulmate.data.util.constants.OpenAIValues
import com.example.mindfulmate.data.util.constants.SystemMessage.SYSTEM_MESSAGE
import com.example.mindfulmate.domain.model.openai.IntentHandler
import com.example.mindfulmate.domain.repository.openai.ChatRepository
import com.example.mindfulmate.presentation.util.MessageModel
import javax.inject.Inject

class ProcessMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val intentHandler: IntentHandler
) {
    private val conversationContext = mutableListOf(SYSTEM_MESSAGE)

    suspend fun execute(userMessage: String): List<MessageModel> {
        val (detectedIntent, remainingMessage) = intentHandler.detectIntentWithMessage(userMessage)

        println("User Message: $userMessage, Detected Intent: $detectedIntent, Remaining: $remainingMessage")

        val messageToProcess = remainingMessage.ifBlank { userMessage }

        if (!intentHandler.isMentalHealthRelated(messageToProcess)) {
            println("Rejected Message: $messageToProcess is not mental health-related.")
            return listOf(
                MessageModel("Please ask a mental health-related question.", "assistant")
            )
        }

        conversationContext.add(ChatMessage("user", messageToProcess))

        val chatResponse = chatRepository.sendMessage(conversationContext)

        return chatResponse?.choices?.map { choice ->
            conversationContext.add(ChatMessage("assistant", choice.message.content))
            MessageModel(choice.message.content, "assistant")
        } ?: listOf(
            MessageModel("I'm unable to process your request at the moment. Please check your connection or try again later.", "assistant")
        )
    }
/*
    suspend fun execute(userMessage: String): List<MessageModel> {
        val (detectedIntent, remainingMessage) = intentHandler.detectIntentWithMessage(userMessage)

        if (remainingMessage.isBlank()) {
            val immediateResponse = when (detectedIntent) {
                "greeting" -> "Hi! How can I assist you today?"
                "farewell" -> "Goodbye! Take care of yourself."
                "gratitude" -> "You're welcome! I'm here if you need anything else."
                "acknowledgment" -> "I'm glad I could help. Let me know if you need more assistance!"
                else -> null
            }
            if (immediateResponse != null) {
                return listOf(MessageModel(immediateResponse, "assistant"))
            }
        }

        val messageToProcess = remainingMessage.ifBlank { userMessage }

        if (!intentHandler.isMentalHealthRelated(messageToProcess)) {
            return listOf(
                MessageModel("Please ask a mental health-related question.", "assistant")
            )
        }

        conversationContext.add(ChatMessage("user", messageToProcess))

        val chatResponse = chatRepository.sendMessage(conversationContext)

        return chatResponse?.choices?.map { choice ->
            conversationContext.add(ChatMessage("assistant", choice.message.content))
            MessageModel(choice.message.content, "assistant")
        } ?: listOf(
            MessageModel(
                "I'm unable to process your request at the moment. Please check your connection or try again later.",
                "assistant"
            )
        )
    }
 */
}
