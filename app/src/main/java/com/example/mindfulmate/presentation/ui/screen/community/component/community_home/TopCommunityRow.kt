package com.example.mindfulmate.presentation.ui.screen.community.component.community_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.community.util.CommunitySectionParams

@Composable
fun TopCommunityRow(
    topCommunities: List<CommunitySectionParams>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.top_communities),
            style = MaterialTheme.typography.titleLarge.copy(
                color = DuskyBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        if (topCommunities.isEmpty()) {
            Text(
                text = stringResource(id = R.string.no_top_communities),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxxmedium)))
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium))
            ) {
                items(topCommunities) { community ->
                    CommunityProfile(
                        imageRes = community.imageRes,
                        name = community.title,
                        onCommunityClick = { communityId ->
                            community.onViewCommunityClick(communityId)
                        },
                        communityId = community.communityId
                    )
                }
            }
        }
    }
}

@Composable
fun CommunityProfile(
    imageRes: Int,
    name: String,
    communityId: String,
    onCommunityClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.icon_large))
                .shadow(
                    elevation = dimensionResource(id = R.dimen.elevation_medium),
                    shape = CircleShape,
                    clip = false
                )
                .clip(CircleShape)
                .background(Color.White)
                .clickable { onCommunityClick(communityId) }
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Grey,
                fontSize = 12.sp
            )
        )
    }
}

@Preview
@Composable
private fun AllCommunitiesRowPreview() {
    MindfulMateTheme {
        TopCommunityRow(
            topCommunities = emptyList()
        )
    }
}

@Preview
@Composable
private fun CommunityProfilePreview() {
    MindfulMateTheme {
        CommunityProfile(
            imageRes = R.drawable.ic_profile,
            name = "New Community",
            onCommunityClick = {},
            communityId = ""
        )
    }
}
