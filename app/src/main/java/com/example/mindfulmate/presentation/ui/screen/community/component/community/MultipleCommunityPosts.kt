package com.example.mindfulmate.presentation.ui.screen.community.component.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.community.util.CommunityPostParams

@Composable
fun MultipleCommunityPosts(
    communityPosts: List<CommunityPostParams>,
    modifier: Modifier = Modifier
) {
    if(communityPosts.isEmpty()){
        Text(
            text = stringResource(id = R.string.no_community_posts),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Grey,
                fontSize = 12.sp,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Center
            ),
            modifier = modifier.fillMaxWidth()
        )
    } else {
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_default))
        ) {
            items(communityPosts) { post ->
                CommunityPost(
                    username = post.username,
                    date = post.date,
                    questionTitle = post.title,
                    questionDescription = post.body,
                    likeCount = post.likesCount,
                    commentCount = post.commentsCount,
                    placeholderRes = post.profilePictureUrl,
                    onCommentsClick = post.onCommentsClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun MultipleCommunityPostsPreview() {
    MindfulMateTheme {
        MultipleCommunityPosts(
            communityPosts = listOf(
                CommunityPostParams(
                    username = "username",
                    date = "12/3/2024",
                    title = "How will you manage stress",
                    body = "Hello everyone can you please tell if you are feeling stressed during exams and how do you cope with stress management",
                    likesCount = "23",
                    commentsCount = "14",
                    profilePictureUrl = R.drawable.ic_analytics,
                    onCommentsClick = {}
                ),
                CommunityPostParams(
                    username = "fefefe",
                    date = "12/3/fefe",
                    title = "How will you manage stress",
                    body = "Hello everyone can you please tell if you are feeling stressed during exams and how do you cope with stress management",
                    likesCount = "23",
                    commentsCount = "14",
                    onCommentsClick = {}
                ),
                CommunityPostParams(
                    username = "username",
                    date = "12/3/2024",
                    title = "How will you manage stress",
                    body = "Hello everyone can you please tell if you are feeling stressed during exams and how do you cope with stress management",
                    likesCount = "23",
                    commentsCount = "14",
                    profilePictureUrl = R.drawable.ic_launcher_background,
                    onCommentsClick = {}
                ),
            )
        )
    }
}

@Preview
@Composable
private fun NoCommunityPostsPreview() {
    MindfulMateTheme {
        MultipleCommunityPosts(communityPosts = emptyList())
    }
}
