package com.example.mindfulmate.presentation.ui.screen.community.component.community_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.community.util.CommunitySectionParams

@Composable
fun TabSection(
    allCommunities: List<CommunitySectionParams>,
    myCommunities: List<CommunitySectionParams>,
    modifier: Modifier = Modifier
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.all_communities),
        stringResource(R.string.my_communities)
    )

    Column(modifier = modifier.fillMaxWidth()) {
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_default)),
            selectedTabIndex = tabIndex,
            containerColor = Color.Transparent,
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.box_height_small))
                        .background(Blue)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (tabIndex == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (tabIndex == index) Blue else Grey
                        )
                    },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            when (tabIndex) {
                0 -> {
                    if(allCommunities.isEmpty()){
                        Text(
                            text = stringResource(id = R.string.no_communities),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Grey,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W300,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_xxlarge)))
                    } else {
                        allCommunities.forEach { section ->
                            CommunitySection(
                                title = section.title,
                                membersCount = section.membersCount,
                                backgroundImage = section.backgroundImageUrl,
                                onViewCommunityClick = { communityId ->
                                    section.onViewCommunityClick(communityId)
                                },
                                communityId = section.communityId
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
                        }
                    }
                }

                1 -> {
                    if(myCommunities.isEmpty()){
                        Text(
                            text = stringResource(id = R.string.no_my_communities),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Grey,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W300,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_xxlarge)))
                    } else {
                        myCommunities.forEach { section ->
                            CommunitySection(
                                title = section.title,
                                membersCount = section.membersCount,
                                backgroundImage = section.backgroundImageUrl,
                                onViewCommunityClick = { communityId ->
                                    section.onViewCommunityClick(communityId)
                                },
                                communityId = section.communityId
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TabSectionPreview() {
    MindfulMateTheme {
        val mockAllCommunities = listOf(
            CommunitySectionParams(
                title = "Community A",
                membersCount = "150",
                backgroundImageUrl = "R.drawable.ic_splash",
                onViewCommunityClick = {}
            ),
            CommunitySectionParams(
                title = "Community B",
                membersCount = "300",
                backgroundImageUrl = "R.drawable.ic_launcher_background",
                onViewCommunityClick = {}
            )
        )

        val mockMyCommunities = listOf(
            CommunitySectionParams(
                title = "My Community X",
                membersCount = "75",
                backgroundImageUrl = "R.drawable.ic_launcher_background",
                onViewCommunityClick = {}
            ),
            CommunitySectionParams(
                title = "My Community Y",
                membersCount = "120",
                backgroundImageUrl = "R.drawable.ic_splash",
                onViewCommunityClick = {}
            )
        )

        TabSection(
            allCommunities = mockAllCommunities,
            myCommunities = mockMyCommunities
        )
    }
}

@Preview
@Composable
private fun NoTabSectionPreview() {
    MindfulMateTheme {
        val mockAllCommunities = emptyList<CommunitySectionParams>()
        val mockMyCommunities = emptyList<CommunitySectionParams>()

        TabSection(
            allCommunities = mockAllCommunities,
            myCommunities = mockMyCommunities
        )
    }
}