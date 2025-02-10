package com.example.mindfulmate.presentation.view_model.community.community_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.usecase.community.GetAllCommunitiesUseCase
import com.example.mindfulmate.domain.usecase.community.GetUserCommunities
import com.example.mindfulmate.presentation.ui.component.util.SearchItem
import com.example.mindfulmate.presentation.ui.screen.community.util.CommunitySectionParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityHomeViewModel @Inject constructor(
    private val getAllCommunitiesUseCase: GetAllCommunitiesUseCase,
    private val getUserCommunities: GetUserCommunities
) : ViewModel() {

    private val _uiState: MutableStateFlow<CommunityHomeUiState> = MutableStateFlow(CommunityHomeUiState.Init)
    val uiState: StateFlow<CommunityHomeUiState> = _uiState.asStateFlow()

    private val _communitiesRow: MutableStateFlow<List<CommunitySectionParams>> = MutableStateFlow(emptyList())
    val communitiesRow: StateFlow<List<CommunitySectionParams>> = _communitiesRow.asStateFlow()

    private val _userCommunitiesRow: MutableStateFlow<List<CommunitySectionParams>> = MutableStateFlow(emptyList())
    val userCommunitiesRow: StateFlow<List<CommunitySectionParams>> = _userCommunitiesRow.asStateFlow()

    private val _topCommunitiesRow: MutableStateFlow<List<CommunitySectionParams>> = MutableStateFlow(emptyList())
    val topCommunitiesRow: StateFlow<List<CommunitySectionParams>> = _topCommunitiesRow.asStateFlow()

    private val _searchCommunities = MutableStateFlow<List<SearchItem>>(emptyList())
    val searchCommunities: StateFlow<List<SearchItem>> = _searchCommunities.asStateFlow()

    fun fetchCommunities(onCommunityClick: (String) -> Unit) {
        _uiState.value = CommunityHomeUiState.Loading
        viewModelScope.launch {
            try {
                val communities = getAllCommunitiesUseCase.invoke()
                println("communities:$communities")
                val communitiesRow = communities.map { community ->
                    CommunitySectionParams(
                        title = community.communityName,
                        membersCount = community.membersCount.toString(),
                        profileImageUrl = community.profilePicture,
                        backgroundImageUrl = community.backgroundPicture,
                        onViewCommunityClick = { onCommunityClick(community.id) }
                    )
                }
                _communitiesRow.value = communitiesRow
                _uiState.value = CommunityHomeUiState.Success
            } catch (e: Exception) {
                _uiState.value = CommunityHomeUiState.Failure(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchTopCommunities(onCommunityClick: (String) -> Unit) {
        _uiState.value = CommunityHomeUiState.Loading
        viewModelScope.launch {
            try {
                val allCommunities = getAllCommunitiesUseCase.invoke()
                val topCommunities = allCommunities.filter { it.membersCount > 50 }

                val topCommunitiesRow = topCommunities.map { community ->
                    CommunitySectionParams(
                        title = community.communityName,
                        profileImageUrl = community.profilePicture,
                        backgroundImageUrl = community.backgroundPicture,
                        membersCount = community.membersCount.toString(),
                        onViewCommunityClick = { onCommunityClick(community.id) }
                    )
                }
                _topCommunitiesRow.value = topCommunitiesRow
                _uiState.value = CommunityHomeUiState.Success
            } catch (e: Exception) {
                _uiState.value = CommunityHomeUiState.Failure(e.message ?: "Unknown error")
            }
        }
    }

    fun getUserCommunities(onCommunityClick: (String) -> Unit) {
        _uiState.value = CommunityHomeUiState.Loading
        viewModelScope.launch {
            try {
                val communities = getUserCommunities.invoke()
                println("communities:$communities")
                val communitiesRow = communities.map { community ->
                    CommunitySectionParams(
                        title = community.communityName,
                        membersCount = community.membersCount.toString(),
                        profileImageUrl = community.profilePicture,
                        backgroundImageUrl = community.backgroundPicture,
                        onViewCommunityClick = { onCommunityClick(community.id) }
                    )
                }
                _userCommunitiesRow.value = communitiesRow
                _uiState.value = CommunityHomeUiState.Success
            } catch (e: Exception) {
                _uiState.value = CommunityHomeUiState.Failure(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchCommunitiesSearch() {
        viewModelScope.launch {
            try {
                val communities = getAllCommunitiesUseCase.invoke()
                println("community search: $communities")
                _searchCommunities.value = communities.map { pair ->
                    SearchItem(
                        id = pair.id,
                        label = pair.communityName,
                        placeholderRes = pair.profilePicture
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
