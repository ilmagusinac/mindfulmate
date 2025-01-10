package com.example.mindfulmate.presentation.view_model.community.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.usecase.community.AddUserToCommunityUseCase
import com.example.mindfulmate.domain.usecase.community.GetCommunityDetailsUseCase
import com.example.mindfulmate.domain.usecase.community.GetCommunityPostsUseCase
import com.example.mindfulmate.domain.usecase.community.IsCommunitySavedByUserUseCase
import com.example.mindfulmate.domain.usecase.community.IsPostLikedUseCase
import com.example.mindfulmate.domain.usecase.community.LikePostUseCase
import com.example.mindfulmate.domain.usecase.community.RemoveUserFromCommunityUseCase
import com.example.mindfulmate.domain.usecase.community.UnlikePostUseCase
import com.example.mindfulmate.presentation.ui.screen.community.util.CommunityPostParams
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val getCommunityDetailsUseCase: GetCommunityDetailsUseCase,
    private val getCommunityPostsUseCase: GetCommunityPostsUseCase,
    private val addUserToCommunityUseCase: AddUserToCommunityUseCase,
    private val removeUserFromCommunityUseCase: RemoveUserFromCommunityUseCase,
    private val isCommunitySavedByUserUseCase: IsCommunitySavedByUserUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<CommunityUiState> = MutableStateFlow(CommunityUiState.Loading)
    val uiState: StateFlow<CommunityUiState> = _uiState.asStateFlow()

    private val _posts: MutableStateFlow<List<CommunityPostParams>> = MutableStateFlow(emptyList())
    val posts: StateFlow<List<CommunityPostParams>> = _posts.asStateFlow()

    private val _isCommunitySaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCommunitySaved: StateFlow<Boolean> = _isCommunitySaved.asStateFlow()

    fun fetchCommunityDetails(communityId: String, navigateToPost: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                val community = getCommunityDetailsUseCase(communityId)
                val fetchedPosts = getCommunityPostsUseCase(communityId)
                _posts.value = fetchedPosts.map { post ->
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
                        }
                    )
                }
                _uiState.value = CommunityUiState.Success(community)
            } catch (e: Exception) {
                _uiState.value = CommunityUiState.Failure(e.message ?: "Error fetching community details")
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

    private fun formatDate(timestamp: Timestamp?): String {
        val formatter = java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.getDefault())
        return formatter.format(timestamp?.toDate())
    }
}
