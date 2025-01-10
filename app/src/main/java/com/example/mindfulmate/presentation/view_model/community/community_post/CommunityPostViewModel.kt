package com.example.mindfulmate.presentation.view_model.community.community_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.model.community.Comment
import com.example.mindfulmate.domain.usecase.community.GetCommentsUseCase
import com.example.mindfulmate.domain.usecase.community.GetPostUseCase
import com.example.mindfulmate.domain.usecase.community.IsPostLikedUseCase
import com.example.mindfulmate.domain.usecase.community.LikePostUseCase
import com.example.mindfulmate.domain.usecase.community.UnlikePostUseCase
import com.example.mindfulmate.domain.usecase.community.WriteCommentUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityPostViewModel @Inject constructor(
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val writeCommentUseCase: WriteCommentUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val unlikePostUseCase: UnlikePostUseCase,
    private val isPostLikedUseCase: IsPostLikedUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CommunityPostUiState> = MutableStateFlow(CommunityPostUiState.Init)
    val uiState: StateFlow<CommunityPostUiState> = _uiState.asStateFlow()

    private val _comments: MutableStateFlow<List<Comment>> = MutableStateFlow(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    private val _isSendingComment: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSendingComment: StateFlow<Boolean> = _isSendingComment.asStateFlow()

    private val _likedPostsState = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val likedPostsState: StateFlow<Map<String, Boolean>> = _likedPostsState.asStateFlow()

    private val _postLikeCounts = MutableStateFlow<Map<String, Int>>(emptyMap())
    val postLikeCounts: StateFlow<Map<String, Int>> = _postLikeCounts.asStateFlow()

    fun fetchPostAndComments(communityId: String, postId: String) {
        viewModelScope.launch {
            _uiState.value = CommunityPostUiState.Loading
            try {
                val post = getPostUseCase(communityId, postId)
                val comments = getCommentsUseCase(communityId, postId)
                _comments.value = comments
                _uiState.value = CommunityPostUiState.Success(post)
            } catch (e: Exception) {
                _uiState.value = CommunityPostUiState.Failure(e.message ?: "Error loading post or comments")
            }
        }
    }

    fun sendComment(communityId: String, postId: String, commentText: String) {
        viewModelScope.launch {
            _isSendingComment.value = true
            try {
                writeCommentUseCase.invoke(communityId, postId, commentText)
                val updatedComments = getCommentsUseCase(communityId, postId)
                _comments.value = updatedComments

                val updatedPost = getPostUseCase(communityId, postId)
                _uiState.value = CommunityPostUiState.Success(updatedPost)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isSendingComment.value = false
            }
        }
    }

    fun checkIfPostIsLiked(postId: String) {
        viewModelScope.launch {
            val isLiked = isPostLikedUseCase.invoke(postId)
            _likedPostsState.value = _likedPostsState.value + (postId to isLiked)
        }
    }
/*
    fun toggleLikePost(communityId: String, postId: String) {
        viewModelScope.launch {
            val isLiked = _likedPostsState.value[postId] ?: false
            try {
                if (isLiked) {
                    unlikePostUseCase.invoke(communityId, postId)
                } else {
                    likePostUseCase.invoke(communityId, postId)
                }
                // Update the local state
                _likedPostsState.value = _likedPostsState.value + (postId to !isLiked)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }*/

    fun toggleLikePost(communityId: String, postId: String) {
        viewModelScope.launch {
            val isLiked = _likedPostsState.value[postId] ?: false
            val currentLikes = _postLikeCounts.value[postId] ?: 0

            try {
                if (isLiked) {
                    // Unlike Post
                    unlikePostUseCase.invoke(communityId, postId)

                    // Update state safely, prevent negative likes
                    _likedPostsState.value = _likedPostsState.value.toMutableMap().apply {
                        this[postId] = false
                    }
                    _postLikeCounts.value = _postLikeCounts.value.toMutableMap().apply {
                        this[postId] = maxOf(0, currentLikes - 1)  // Ensure it doesn't drop below 0
                    }
                } else {
                    // Like Post
                    likePostUseCase.invoke(communityId, postId)

                    // Increase like count
                    _likedPostsState.value = _likedPostsState.value.toMutableMap().apply {
                        this[postId] = true
                    }
                    _postLikeCounts.value = _postLikeCounts.value.toMutableMap().apply {
                        this[postId] = currentLikes + 1
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun formatDate(timestamp: Timestamp?): String {
        val formatter = java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.getDefault())
        return formatter.format(timestamp?.toDate())
    }
}
