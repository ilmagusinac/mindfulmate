package com.example.mindfulmate.presentation.view_model.community.community_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.model.community.Comment
import com.example.mindfulmate.domain.usecase.chat.CreateOrGetChatUseCase
import com.example.mindfulmate.domain.usecase.community.DeleteCommentUseCase
import com.example.mindfulmate.domain.usecase.community.EditCommentUseCase
import com.example.mindfulmate.domain.usecase.community.GetCommentsUseCase
import com.example.mindfulmate.domain.usecase.community.GetCurrentUserIdUseCase
import com.example.mindfulmate.domain.usecase.community.GetPostUseCase
import com.example.mindfulmate.domain.usecase.community.IsPostLikedUseCase
import com.example.mindfulmate.domain.usecase.community.LikePostUseCase
import com.example.mindfulmate.domain.usecase.community.UnlikePostUseCase
import com.example.mindfulmate.domain.usecase.community.WriteCommentUseCase
import com.example.mindfulmate.domain.usecase.user.GetUserByIdUseCase
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
class CommunityPostViewModel @Inject constructor(
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val writeCommentUseCase: WriteCommentUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val unlikePostUseCase: UnlikePostUseCase,
    private val isPostLikedUseCase: IsPostLikedUseCase,
    private val editCommentUseCase: EditCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val createOrGetChatUseCase: CreateOrGetChatUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CommunityPostUiState> =
        MutableStateFlow(CommunityPostUiState.Init)
    val uiState: StateFlow<CommunityPostUiState> = _uiState.asStateFlow()

    private val _comments: MutableStateFlow<List<Comment>> = MutableStateFlow(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    private val _likedPostsState = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val likedPostsState: StateFlow<Map<String, Boolean>> = _likedPostsState.asStateFlow()

    private val _postLikeCounts = MutableStateFlow<Map<String, Int>>(emptyMap())
    val postLikeCounts: StateFlow<Map<String, Int>> = _postLikeCounts.asStateFlow()

    private val _navigationEvent: Channel<DeleteCommentNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId

    private val _postUserProfileImage = MutableStateFlow<String?>(null)
    val postUserProfileImage: StateFlow<String?> = _postUserProfileImage.asStateFlow()

    private val _commentUserProfiles = MutableStateFlow<Map<String, String?>>(emptyMap())
    val commentUserProfiles: StateFlow<Map<String, String?>> = _commentUserProfiles.asStateFlow()

    private val _deleteCommentUiState =
        MutableStateFlow<DeleteCommentUiState>(DeleteCommentUiState.Init)
    val deleteCommentUiState: StateFlow<DeleteCommentUiState> = _deleteCommentUiState.asStateFlow()

    private val _selectedCommentId = MutableStateFlow<String?>(null)
    val selectedCommentId: StateFlow<String?> = _selectedCommentId.asStateFlow()

    private val _isDeletePopupVisible = MutableStateFlow(false)
    val isDeletePopupVisible: StateFlow<Boolean> = _isDeletePopupVisible.asStateFlow()

    private val _editingCommentId = MutableStateFlow<String?>(null)
    val editingCommentId: StateFlow<String?> = _editingCommentId.asStateFlow()

    private val _editingCommentText = MutableStateFlow<String?>(null)
    val editingCommentText: StateFlow<String?> = _editingCommentText.asStateFlow()

    private val _toastMessage = Channel<String>(Channel.CONFLATED)
    val toastMessage = _toastMessage.receiveAsFlow()

    private val _isPopupVisible = MutableStateFlow(false)
    val isPopupVisible: StateFlow<Boolean> = _isPopupVisible.asStateFlow()

    private val _selectedUsername = MutableStateFlow<String?>(null)
    val selectedUsername: StateFlow<String?> = _selectedUsername.asStateFlow()

    private val _selectedProfileImage = MutableStateFlow<String?>(null)
    val selectedProfileImage: StateFlow<String?> = _selectedProfileImage.asStateFlow()

    private val _selectedUserId = MutableStateFlow<String?>(null)
    val selectedUserId: StateFlow<String?> = _selectedUserId.asStateFlow()

    init {
        viewModelScope.launch {
            _currentUserId.value = getCurrentUserIdUseCase.invoke()
        }
    }

    fun showDeletePopup(commentId: String) {
        _selectedCommentId.value = commentId
        _isDeletePopupVisible.value = true
    }

    fun hideDeletePopup() {
        _selectedCommentId.value = null
        _isDeletePopupVisible.value = false
    }

    fun fetchPostAndComments(communityId: String, postId: String) {
        viewModelScope.launch {
            _uiState.value = CommunityPostUiState.Loading
            try {
                val post = getPostUseCase(communityId, postId)
                val postUser = getUserByIdUseCase.invoke(post.userId)

                _postUserProfileImage.value = postUser.profileImageUrl

                val comments = getCommentsUseCase(communityId, postId)
                val userProfiles = comments.associate { comment ->
                    val user = getUserByIdUseCase.invoke(comment.userId)
                    comment.userId to user.profileImageUrl
                }

                _commentUserProfiles.value = userProfiles
                _comments.value = comments
                _uiState.value = CommunityPostUiState.Success(post)
            } catch (e: Exception) {
                _uiState.value =
                    CommunityPostUiState.Failure(e.message ?: "Error loading post or comments")
            }
        }
    }

    fun checkIfPostIsLiked(postId: String) {
        viewModelScope.launch {
            val isLiked = isPostLikedUseCase.invoke(postId)
            _likedPostsState.value = _likedPostsState.value + (postId to isLiked)
        }
    }

    fun toggleLikePost(communityId: String, postId: String) {
        viewModelScope.launch {
            try {
                val isLiked = _likedPostsState.value[postId] ?: false

                if (isLiked) {
                    unlikePostUseCase.invoke(communityId, postId)
                } else {
                    likePostUseCase.invoke(communityId, postId)
                }

                val updatedPost = getPostUseCase(communityId, postId)

                _likedPostsState.value = _likedPostsState.value.toMutableMap().apply {
                    this[postId] = !isLiked
                }
                _postLikeCounts.value = _postLikeCounts.value.toMutableMap().apply {
                    this[postId] = updatedPost.likes
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteComment(communityId: String, postId: String, commentId: String) {
        viewModelScope.launch {
            _deleteCommentUiState.value = DeleteCommentUiState.Loading
            try {
                deleteCommentUseCase.invoke(communityId, postId, commentId)
                fetchPostAndComments(communityId, postId)
                hideDeletePopup()
                _deleteCommentUiState.value = DeleteCommentUiState.Success
                _toastMessage.send("Comment deleted successfully!")
            } catch (e: Exception) {
                _deleteCommentUiState.value =
                    DeleteCommentUiState.Failure(e.message ?: "Failed to delete comment")
            }
        }
    }

    fun startEditingComment(commentId: String, commentText: String) {
        _editingCommentId.value = commentId
        _editingCommentText.value = commentText
    }

    fun cancelEditingComment() {
        _editingCommentId.value = null
        _editingCommentText.value = null
    }

    fun sendOrEditComment(communityId: String, postId: String, commentText: String) {
        viewModelScope.launch {
            val commentId = _editingCommentId.value
            if (commentId != null) {
                editCommentUseCase.invoke(communityId, postId, commentId, commentText)
                cancelEditingComment()
            } else {
                writeCommentUseCase.invoke(communityId, postId, commentText)
            }
            fetchPostAndComments(communityId, postId)
        }
    }

    fun updateEditingCommentText(newText: String) {
        _editingCommentText.value = newText
    }

    suspend fun getCurrentUserId(): String {
        val userId = getCurrentUserIdUseCase.invoke()
        return userId
    }

    fun formatDate(timestamp: Timestamp?): String {
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
                    CommunityPostUiState.Failure("Failed to start chat: ${e.localizedMessage}")
            }
        }
    }
}
