package com.example.mindfulmate.presentation.ui.screen.community.component.community_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.theme.Yellow
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton

@Composable
fun CommunitySection(
    title: String,
    membersCount: String,
    backgroundImage: String,
    communityId: String,
    onViewCommunityClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)))
    ) {
        AsyncImage(
            model = backgroundImage,
            contentDescription = "community image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Grey.copy(alpha = 0.2f))
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_xxmedium),
                    vertical = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(color = DuskyWhite),
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxdefault)))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    tint = Yellow
                )
                Text(
                    text = "$membersCount members",
                    style = MaterialTheme.typography.bodyMedium.copy(color = DuskyWhite),
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_xsmall))
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxmedium)))
            MindfulMateButton(
                text = stringResource(id = R.string.view_community),
                onClick = { onViewCommunityClick(communityId) },
                textPadding = PaddingValues(
                    vertical = dimensionResource(R.dimen.padding_small),
                    horizontal = dimensionResource(R.dimen.padding_xxmedium)
                ),
                containerColor = DuskyWhite,
                contentColor = DuskyWhite,
                borderColor = DuskyWhite,
                textColor = Blue
            )
        }
    }
}

@Preview
@Composable
private fun CommunitySectionPreview() {
    MindfulMateTheme {
        CommunitySection(
            title = "Stress Relief",
            membersCount = "12345",
            backgroundImage = "R.drawable.ic_launcher_background",
            onViewCommunityClick = {},
            communityId = ""
        )
    }
}
