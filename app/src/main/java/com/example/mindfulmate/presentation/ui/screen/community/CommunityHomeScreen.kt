package com.example.mindfulmate.presentation.ui.screen.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMateMainHeaderSection
import com.example.mindfulmate.presentation.ui.component.MindfulMateSearchField
import com.example.mindfulmate.presentation.ui.component.util.SearchItem
import com.example.mindfulmate.presentation.ui.screen.community.component.community_home.TabSection
import com.example.mindfulmate.presentation.ui.screen.community.component.community_home.TopCommunityRow
import com.example.mindfulmate.presentation.ui.screen.community.util.CommunitySectionParams
import com.example.mindfulmate.presentation.view_model.community.community_home.CommunityHomeUiState
import com.example.mindfulmate.presentation.view_model.community.community_home.CommunityHomeViewModel

@Composable
fun CommunityHomeScreen(
    viewModel: CommunityHomeViewModel,
    onCommunityClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: CommunityHomeUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val communities by viewModel.communitiesRow.collectAsStateWithLifecycle()
    val topCommunities by viewModel.topCommunitiesRow.collectAsStateWithLifecycle()
    val userCommunities by viewModel.userCommunitiesRow.collectAsStateWithLifecycle()
    val searchCommunities by viewModel.searchCommunities.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchCommunities(onCommunityClick)
        viewModel.fetchTopCommunities(onCommunityClick)
        viewModel.getUserCommunities(onCommunityClick)
        viewModel.fetchCommunitiesSearch()
    }

    when (uiState) {
        is CommunityHomeUiState.Loading -> {
            LoadingPlaceholder()
        }
        is CommunityHomeUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = {})
        }
        else -> {
            var textState by remember { mutableStateOf(TextFieldValue("")) }

            CommunityHomeScreen(
                allSearchItems = searchCommunities,
                topCommunities = topCommunities,
                communities = textState,
                onSearchCommunitiesChange = { textState = it },
                allCommunities = communities,
                myCommunities = userCommunities,
                onSearchItemClick = {},
                modifier = modifier
            )
        }
    }
}

@Composable
private fun CommunityHomeScreen(
    allSearchItems: List<SearchItem>,
    topCommunities: List<CommunitySectionParams>,
    communities: TextFieldValue,
    onSearchCommunitiesChange: (TextFieldValue) -> Unit,
    allCommunities: List<CommunitySectionParams>,
    myCommunities: List<CommunitySectionParams>,
    onSearchItemClick: (SearchItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                horizontal = dimensionResource(id = R.dimen.padding_default)
            )
    ) {
        MindfulMateMainHeaderSection(
            iconRes = R.drawable.ic_community,
            title = stringResource(id = R.string.community)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        MindfulMateSearchField(
            text = communities,
            placeholder = stringResource(id = R.string.search_text_field_placeholder),
            allSearchItems = allSearchItems,
            onTextValueChange = onSearchCommunitiesChange,
            onSearchItemClick = onSearchItemClick
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        TopCommunityRow(topCommunities = topCommunities)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        TabSection(
            allCommunities = allCommunities,
            myCommunities = myCommunities
        )
    }
}

@Preview
@Composable
private fun CommunityScreenPreview() {
    MindfulMateTheme {
        var searchCommunities by remember { mutableStateOf(TextFieldValue("")) }

        CommunityHomeScreen(
            communities = searchCommunities,
            onSearchCommunitiesChange = { searchCommunities = it },
            allSearchItems = listOf(
                SearchItem(
                    id = "",
                    label = "fd"
                ),
                SearchItem(
                    id = "",
                    label = "fd"
                ),
                SearchItem(
                    id = "",
                    label = "fd"
                )
            ),
            topCommunities = emptyList(),
            onSearchItemClick = {},
            allCommunities = emptyList(),
            myCommunities = emptyList()
        )
    }
}
