package com.example.mindfulmate.presentation.view_model.community.community_write_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.model.community.Post
import com.example.mindfulmate.domain.usecase.chat.GetCurrentUserIdUseCase
import com.example.mindfulmate.domain.usecase.community.GetUsernameUseCase
import com.example.mindfulmate.domain.usecase.community.WritePostUseCase
import com.example.mindfulmate.presentation.view_model.chat.chat.ChatNavigationEvent
import com.example.mindfulmate.presentation.view_model.community.community_post.CommunityPostNavigationEvent
import com.example.mindfulmate.presentation.view_model.community.community_post.CommunityPostUiState
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CommunityWritePostViewModel @Inject constructor(
    private val writePostUseCase: WritePostUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUsernameUseCase: GetUsernameUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CommunityWritePostUiState> = MutableStateFlow(CommunityWritePostUiState.Init)
    val uiState: StateFlow<CommunityWritePostUiState> = _uiState.asStateFlow()

    private val _isPostEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPostEnabled: StateFlow<Boolean> = _isPostEnabled.asStateFlow()

    private val _navigationEvent: Channel<CommunityWritePostNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _toastMessage = Channel<String>(Channel.CONFLATED)
    val toastMessage = _toastMessage.receiveAsFlow()

    fun validatePost(title: String, body: String) {
        _isPostEnabled.value = title.isNotBlank() && body.isNotBlank()
    }

    fun createPost(communityId: String, title: String, body: String) {
        viewModelScope.launch {
            _uiState.value = CommunityWritePostUiState.Loading
            val currentUserId = getCurrentUserIdUseCase.invoke()
            try {
                val post = Post(
                    postId = "",
                    username = getUsernameUseCase(currentUserId).toString(),
                    date = Timestamp.now(),
                    title = title,
                    body = body
                )
                writePostUseCase(communityId, post)

                _uiState.value = CommunityWritePostUiState.Success
                _toastMessage.send("New post created")
                triggerNavigation(CommunityWritePostNavigationEvent.Navigate)
            } catch (e: Exception) {
                _uiState.value = CommunityWritePostUiState.Failure(e.message ?: "Error creating post")
            }
        }
    }

    private fun triggerNavigation(event: CommunityWritePostNavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }
}
