package com.example.mindfulmate.presentation.ui.screen.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun CommunityRow(
    title: String,
    communities: List<Community>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = Grey,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_xsmall))
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_xdefault))) {
            items(communities) { community ->
                CommunityCard(
                    title = community.title,
                    membersCount = community.membersCount,
                    backgroundImage = community.backgroundImage,
                    logo = community.logo
                )
            }
        }
    }
}

data class Community(
    val title: String,
    val membersCount: String,
    val backgroundImage: Int,
    val logo: Int
)

@Composable
fun CommunityCard(
    title: String,
    membersCount: String,
    @DrawableRes backgroundImage: Int,
    @DrawableRes logo: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(150.dp)
            .height(220.dp)
            .shadow(
                elevation = dimensionResource(id = R.dimen.elevation_medium),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)),
                clip = false
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)))
            .background(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = dimensionResource(id = R.dimen.rounded_corners_small),
                            topEnd = dimensionResource(id = R.dimen.rounded_corners_small)
                        )
                    )
            ) {
                Image(
                    painter = painterResource(id = backgroundImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Image(
                painter = painterResource(id = logo),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterHorizontally)
                    .offset(y = (-24).dp)
                    .background(
                        color = DuskyWhite,
                        shape = CircleShape
                    )
                    .padding(dimensionResource(id = R.dimen.padding_xsmall))
                    .clip(CircleShape)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Grey,
                    fontSize = if (title.length > 16) 14.sp else 16.sp
                ),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
            Text(
                text = "$membersCount members",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = LightGrey,
                    fontWeight = FontWeight.W200
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityCardPreview() {
    MindfulMateTheme {
        CommunityCard(
            title = "Anxiety Management",
            membersCount = "23,600",
            backgroundImage = R.drawable.ic_resources,
            logo = R.drawable.ic_heart
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityRowPreview() {
    val sampleCommunities = listOf(
        Community(
            title = "Anxiety Management 123",
            membersCount = "23,600",
            backgroundImage = R.drawable.ic_splash,
            logo = R.drawable.ic_google
        ),
        Community(
            title = "Stress Relief",
            membersCount = "7,600",
            backgroundImage = R.drawable.ic_launcher_background,
            logo = R.drawable.ic_heart
        ),
        Community(
            title = "Mindfulness",
            membersCount = "18,200",
            backgroundImage = R.drawable.ic_splash,
            logo = R.drawable.ic_resources
        )
    )

    MindfulMateTheme {
        CommunityRow(
            communities = sampleCommunities,
            title = "Top Communities"
        )
    }
}
