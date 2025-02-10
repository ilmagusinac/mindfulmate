package com.example.mindfulmate.presentation.view_model.community.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.usecase.chat.CreateOrGetChatUseCase
import com.example.mindfulmate.domain.usecase.community.AddUserToCommunityUseCase
import com.example.mindfulmate.domain.usecase.community.DeletePostUseCase
import com.example.mindfulmate.domain.usecase.community.EditPostUseCase
import com.example.mindfulmate.domain.usecase.community.GetCommunityDetailsUseCase
import com.example.mindfulmate.domain.usecase.community.GetCommunityPostsUseCase
import com.example.mindfulmate.domain.usecase.community.GetCurrentUserIdUseCase
import com.example.mindfulmate.domain.usecase.community.IsCommunitySavedByUserUseCase
import com.example.mindfulmate.domain.usecase.community.RemoveUserFromCommunityUseCase
import com.example.mindfulmate.domain.usecase.user.GetUserByIdUseCase
import com.example.mindfulmate.domain.usecase.user.GetUserUseCase
import com.example.mindfulmate.presentation.ui.screen.community.util.CommunityPostParams
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
class CommunityViewModel @Inject constructor(
    private val getCommunityDetailsUseCase: GetCommunityDetailsUseCase,
    private val getCommunityPostsUseCase: GetCommunityPostsUseCase,
    private val addUserToCommunityUseCase: AddUserToCommunityUseCase,
    private val removeUserFromCommunityUseCase: RemoveUserFromCommunityUseCase,
    private val isCommunitySavedByUserUseCase: IsCommunitySavedByUserUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val createOrGetChatUseCase: CreateOrGetChatUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CommunityUiState> = MutableStateFlow(CommunityUiState.Loading)
    val uiState: StateFlow<CommunityUiState> = _uiState.asStateFlow()

    private val _posts: MutableStateFlow<List<CommunityPostParams>> = MutableStateFlow(emptyList())
    val posts: StateFlow<List<CommunityPostParams>> = _posts.asStateFlow()

    private val _isCommunitySaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCommunitySaved: StateFlow<Boolean> = _isCommunitySaved.asStateFlow()

    private val _editPostUiState: MutableStateFlow<EditPostUiState> = MutableStateFlow(EditPostUiState.Init)
    val editPostUiState: StateFlow<EditPostUiState> = _editPostUiState.asStateFlow()

    private val _navigationEvent: Channel<DeletePostNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _deletePostUiState = MutableStateFlow<DeletePostUiState>(DeletePostUiState.Init)
    val deletePostUiState: StateFlow<DeletePostUiState> = _deletePostUiState.asStateFlow()

    private val _toastMessage = Channel<String>(Channel.CONFLATED)
    val toastMessage = _toastMessage.receiveAsFlow()

    private val _selectedPostId = MutableStateFlow<String?>(null)
    val selectedPostId: StateFlow<String?> = _selectedPostId.asStateFlow()

    private val _isPopupVisible = MutableStateFlow(false)
    val isPopupVisible: StateFlow<Boolean> = _isPopupVisible.asStateFlow()

    private val _selectedUsername = MutableStateFlow<String?>(null)
    val selectedUsername: StateFlow<String?> = _selectedUsername.asStateFlow()

    private val _selectedProfileImage = MutableStateFlow<String?>(null)
    val selectedProfileImage: StateFlow<String?> = _selectedProfileImage.asStateFlow()

    private val _selectedUserId = MutableStateFlow<String?>(null)
    val selectedUserId: StateFlow<String?> = _selectedUserId.asStateFlow()

    private fun showDeletePopup(postId: String) {
        _selectedPostId.value = postId
    }

    fun hideDeletePopup() {
        _selectedPostId.value = null
    }

    fun fetchCommunityDetails(
        communityId: String,
        navigateToPost: (String, String) -> Unit,
        navigateToEdit: (String, String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val community = getCommunityDetailsUseCase(communityId)
                val fetchedPosts = getCommunityPostsUseCase(communityId)
                val userId = getCurrentUserIdUseCase.invoke()

                _posts.value = fetchedPosts.map { post ->
                    val user = getUserByIdUseCase.invoke(post.userId)
                    val isOwner = post.userId == userId

                    CommunityPostParams(
                        postId = post.postId,
                        username = post.username,
                        date = formatDate(post.date),
                        title = post.title,
                        body = post.body,
                        likesCount = post.likes.toString(),
                        commentsCount = post.commentsCount.toString(),
                        onCommentsClick = {
                            navigateToPost(communityId, post.postId)
                        },
                        onDeleteClick = { showDeletePopup(post.postId) },
                        onEditClick = {
                            navigateToEdit(communityId, post.postId)
                        },
                        isOwner = isOwner,
                        profilePictureUrl = user.profileImageUrl,
                        onUserClick = {
                            if (!isOwner) {
                                showUserPopup(user.id, user.username, user.profileImageUrl)
                            }
                        }
                    )
                }
                _uiState.value = CommunityUiState.Success(community)
            } catch (e: Exception) {
                _uiState.value =
                    CommunityUiState.Failure(e.message ?: "Error fetching community details")
            }
        }
    }

    fun checkIfCommunityIsSaved(communityId: String) {
        viewModelScope.launch {
            try {
                val isSaved = isCommunitySavedByUserUseCase.invoke(communityId)
                _isCommunitySaved.value = isSaved
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleSaveCommunity(communityId: String) {
        viewModelScope.launch {
            try {
                if (_isCommunitySaved.value) {
                    removeUserFromCommunityUseCase.invoke(communityId)
                } else {
                    addUserToCommunityUseCase.invoke(communityId)
                }
                checkIfCommunityIsSaved(communityId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deletePost(communityId: String, postId: String) {
        viewModelScope.launch {
            _deletePostUiState.value = DeletePostUiState.Loading
            try {
                deletePostUseCase.invoke(communityId, postId)
                fetchCommunityDetails(
                    communityId = communityId,
                    navigateToPost = { _, _ -> },
                    navigateToEdit = { _, _ -> },
                )
                _deletePostUiState.value = DeletePostUiState.Success
                _toastMessage.send("Post deleted successfully!")
                hideDeletePopup()
            } catch (e: Exception) {
                e.printStackTrace()
                _deletePostUiState.value = DeletePostUiState.Failure("Failed to delete post: ${e.message}")
            }
        }
    }

    fun resetDeletePostState() {
        _deletePostUiState.value = DeletePostUiState.Init
    }

    fun fetchPostForEditing(communityId: String, postId: String) {
        viewModelScope.launch {
            _editPostUiState.value = EditPostUiState.Loading
            try {
                val post = getCommunityPostsUseCase(communityId).first { it.postId == postId }
                _editPostUiState.value = EditPostUiState.Success(post.title, post.body)
            } catch (e: Exception) {
                _editPostUiState.value = EditPostUiState.Failure(e.message ?: "Error loading post")
            }
        }
    }

    fun editPost(communityId: String, postId: String, newTitle: String, newBody: String) {
        viewModelScope.launch {
            try {
                editPostUseCase.invoke(communityId, postId, newTitle, newBody)
                _toastMessage.send("Post edited")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun formatDate(timestamp: Timestamp?): String {
        val formatter = java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.getDefault())
        return formatter.format(timestamp?.toDate())
    }

    fun showUserPopup(userId: String, username: String, profileImage: String?) {
        _selectedUserId.value = userId
        _selectedUsername.value = username
        _selectedProfileImage.value = profileImage
        _isPopupVisible.value = true
    }

    fun hideUserPopup() {
        _isPopupVisible.value = false
        _selectedUsername.value = null
        _selectedProfileImage.value = null
    }

    fun startChatWithUser(otherUserId: String, onChatCreated: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val chatId = createOrGetChatUseCase(otherUserId)
                onChatCreated(chatId)
            } catch (e: Exception) {
                _uiState.value =
                    CommunityUiState.Failure("Failed to start chat: ${e.localizedMessage}")
            }
        }
    }
}
