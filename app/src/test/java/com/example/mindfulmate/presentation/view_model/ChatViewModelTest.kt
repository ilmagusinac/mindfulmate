package com.example.mindfulmate.presentation.view_model

import com.example.mindfulmate.presentation.view_model.chat.chat.ChatUiState
import com.example.mindfulmate.presentation.view_model.chat.chat.ChatViewModel
import org.junit.Assert.assertEquals
import com.example.mindfulmate.domain.model.chat.Message
import com.example.mindfulmate.domain.usecase.chat.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getMessageChatUseCase: GetMessageChatUseCase
    private lateinit var sendMessageChatUseCase: SendMessageChatUseCase
    private lateinit var fetchChatsUseCase: FetchChatsUseCase
    private lateinit var deleteChatUseCase: DeleteChatUseCase
    private lateinit var getCurrentUserIdUseCase: GetCurrentUserIdUseCase
    private lateinit var editMessageUseCase: EditMessageUseCase
    private lateinit var deleteMessageUseCase: DeleteMessageUseCase
    private lateinit var viewModel: ChatViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getMessageChatUseCase = mockk()
        sendMessageChatUseCase = mockk()
        fetchChatsUseCase = mockk()
        deleteChatUseCase = mockk()
        getCurrentUserIdUseCase = mockk()
        editMessageUseCase = mockk()
        deleteMessageUseCase = mockk()

        viewModel = ChatViewModel(
            getMessageChatUseCase,
            sendMessageChatUseCase,
            fetchChatsUseCase,
            deleteChatUseCase,
            getCurrentUserIdUseCase,
            editMessageUseCase,
            deleteMessageUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchMessages updates messages and uiState on success`() = runTest {
        val chatId = "chat123"
        val mockMessages = listOf(
            Message(id = "msg1", text = "Hello", senderId = "user1"),
            Message(id = "msg2", text = "Hi", senderId = "user2")
        )
        coEvery { getMessageChatUseCase(chatId) } returns mockMessages

        viewModel.fetchMessages(chatId)
        advanceUntilIdle()

        coVerify { getMessageChatUseCase(chatId) }
        assertEquals(mockMessages, viewModel.messages.first())
        assert(viewModel.uiState.first() is ChatUiState.Success)
    }

    @Test
    fun `fetchMessages updates uiState to Failure on exception`() = runTest {
        val chatId = "chat123"
        val errorMessage = "Failed to fetch messages"
        coEvery { getMessageChatUseCase(chatId) } throws RuntimeException(errorMessage)

        viewModel.fetchMessages(chatId)
        advanceUntilIdle()

        coVerify { getMessageChatUseCase(chatId) }
        val state = viewModel.uiState.first()
        assert(state is ChatUiState.Failure)
        assertEquals(errorMessage, (state as ChatUiState.Failure).message)
    }

    @Test
    fun `sendMessage clears messageInput on success`() = runTest {
        val chatId = "chat123"
        val messageText = "Hello"
        coEvery { sendMessageChatUseCase(chatId, messageText) } returns "messageId"

        viewModel.sendMessage(chatId, messageText)
        advanceUntilIdle()

        coVerify { sendMessageChatUseCase(chatId, messageText) }
        assertEquals("", viewModel.messageInput.first())
    }

    @Test
    fun `sendMessage updates uiState to Failure on exception`() = runTest {
        val chatId = "chat123"
        val messageText = "Hello"
        val errorMessage = "Failed to send message"
        coEvery {
            sendMessageChatUseCase(
                chatId,
                messageText
            )
        } throws RuntimeException(errorMessage)

        viewModel.sendMessage(chatId, messageText)
        advanceUntilIdle()

        coVerify { sendMessageChatUseCase(chatId, messageText) }
        val state = viewModel.uiState.first()
        assert(state is ChatUiState.Failure)
        assertEquals(errorMessage, (state as ChatUiState.Failure).message)
    }

    @Test
    fun `deleteChat triggers navigation on success`() = runTest {
        val chatId = "chat123"
        coEvery { deleteChatUseCase(chatId) } just runs

        viewModel.deleteChat(chatId)
        advanceUntilIdle()

        coVerify { deleteChatUseCase(chatId) }
        assert(viewModel.uiState.first() is ChatUiState.Success)
    }

    @Test
    fun `deleteChat updates uiState to Failure on exception`() = runTest {
        val chatId = "chat123"
        val errorMessage = "Failed to delete chat"
        coEvery { deleteChatUseCase(chatId) } throws RuntimeException(errorMessage)

        viewModel.deleteChat(chatId)
        advanceUntilIdle()

        coVerify { deleteChatUseCase(chatId) }
        val state = viewModel.uiState.first()
        assert(state is ChatUiState.Failure)
        assertEquals("Failed to delete chat: $errorMessage", (state as ChatUiState.Failure).message)
    }

    @Test
    fun `updateMessageInput updates the messageInput state`() = runTest {
        val newInput = "Updated message"
        viewModel.updateMessageInput(newInput)

        assertEquals(newInput, viewModel.messageInput.first())
    }
}
