package com.example.mindfulmate.data.repository

import com.example.mindfulmate.data.repository.chat.ChatRepositoryImpl
import com.example.mindfulmate.data.service.chat.ChatService
import com.example.mindfulmate.domain.model.chat.Chat
import com.example.mindfulmate.domain.model.chat.Message
import com.example.mindfulmate.domain.model.chat.Participant
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatRepositoryImplTest {

    private lateinit var chatService: ChatService
    private lateinit var chatRepository: ChatRepositoryImpl

    @Before
    fun setup() {
        chatService = mockk()
        chatRepository = ChatRepositoryImpl(chatService)
    }

    @Test
    fun `getMessages calls chatService and returns messages`() = runTest {
        val chatId = "123"
        val expectedMessages = listOf(
            Message(id = "1", text = "Hello", senderId = "user1"),
            Message(id = "2", text = "Hi", senderId = "user2")
        )
        coEvery { chatService.getMessages(chatId) } returns expectedMessages

        val result = chatRepository.getMessages(chatId)

        assertEquals(expectedMessages, result)
        coVerify { chatService.getMessages(chatId) }
    }

    @Test
    fun `sendMessage calls chatService and returns messageId`() = runTest {
        val chatId = "123"
        val message = "Hello"
        val expectedMessageId = "message123"
        coEvery { chatService.sendMessage(chatId, message) } returns expectedMessageId

        val result = chatRepository.sendMessage(chatId, message)

        assertEquals(expectedMessageId, result)
        coVerify { chatService.sendMessage(chatId, message) }
    }

    @Test
    fun `listenForMessages calls chatService`() = runTest {
        val chatId = "123"
        val messages = listOf(Message(id = "1", text = "Test", senderId = "user1"))
        val callback: (List<Message>) -> Unit = mockk(relaxed = true)
        coEvery { chatService.listenForMessages(chatId, any()) } answers {
            secondArg<(List<Message>) -> Unit>().invoke(messages)
        }

        chatRepository.listenForMessages(chatId, callback)

        coVerify { chatService.listenForMessages(chatId, any()) }
        verify { callback(messages) }
    }

    @Test
    fun `fetchChats calls chatService and returns chats`() = runTest {
        val expectedChats = listOf(Chat(chatId = "123", participants = emptyList()))
        coEvery { chatService.fetchChats() } returns expectedChats

        val result = chatRepository.fetchChats()

        assertEquals(expectedChats, result)
        coVerify { chatService.fetchChats() }
    }

    @Test
    fun `getOtherParticipantUsername returns username of other participant`() = runTest {
        val currentUserId = "user1"
        val chat = Chat(
            chatId = "123",
            participants = listOf(
                Participant(userId = "user1", username = "User1"),
                Participant(userId = "user2", username = "User2")
            )
        )
        coEvery { chatService.getCurrentUserId() } returns currentUserId

        val username = chatRepository.getOtherParticipantUsername(chat)

        assertEquals("User2", username)
        coVerify { chatService.getCurrentUserId() }
    }

    @Test
    fun `createOrGetChat calls chatService and returns chatId`() = runTest {
        val otherUserId = "user2"
        val expectedChatId = "chat123"
        coEvery { chatService.createOrGetChat(otherUserId) } returns expectedChatId

        val result = chatRepository.createOrGetChat(otherUserId)

        assertEquals(expectedChatId, result)
        coVerify { chatService.createOrGetChat(otherUserId) }
    }

    @Test
    fun `deleteChat calls chatService`() = runTest {
        val chatId = "123"
        coEvery { chatService.deleteChat(chatId) } just Runs

        chatRepository.deleteChat(chatId)

        coVerify { chatService.deleteChat(chatId) }
    }

    @Test
    fun `getCurrentUserId calls chatService and returns userId`() = runTest {
        val expectedUserId = "user1"
        coEvery { chatService.getCurrentUserId() } returns expectedUserId

        val result = chatRepository.getCurrentUserId()

        assertEquals(expectedUserId, result)
        coVerify { chatService.getCurrentUserId() }
    }

    @Test
    fun `markMessagesAsRead calls chatService`() = runTest {
        val chatId = "123"
        coEvery { chatService.markMessagesAsRead(chatId) } just Runs

        chatRepository.markMessagesAsRead(chatId)

        coVerify { chatService.markMessagesAsRead(chatId) }
    }

    @Test
    fun `editMessage calls chatService and returns true`() = runTest {
        val chatId = "123"
        val messageId = "message1"
        val newText = "Updated text"
        coEvery { chatService.editMessage(chatId, messageId, newText) } returns true

        val result = chatRepository.editMessage(chatId, messageId, newText)

        assertTrue(result)
        coVerify { chatService.editMessage(chatId, messageId, newText) }
    }

    @Test
    fun `deleteMessage calls chatService and returns true`() = runTest {
        val chatId = "123"
        val messageId = "message1"
        coEvery { chatService.deleteMessage(chatId, messageId) } returns true

        val result = chatRepository.deleteMessage(chatId, messageId)

        assertTrue(result)
        coVerify { chatService.deleteMessage(chatId, messageId) }
    }

    @Test
    fun `listenForUnreadMessages calls chatService`() = runTest {
        val callback: (Int) -> Unit = mockk(relaxed = true)
        coEvery { chatService.listenForUnreadMessages(any()) } just Runs

        chatRepository.listenForUnreadMessages(callback)

        coVerify { chatService.listenForUnreadMessages(callback) }
    }
}
