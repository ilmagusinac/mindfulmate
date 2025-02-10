package com.example.mindfulmate.presentation.ui.screen.community.component.community_post

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
import com.example.mindfulmate.presentation.ui.screen.community.util.CommunityAnswerParams

@Composable
fun MultipleCommunityPostComments(
    communityComments: List<CommunityAnswerParams>,
    modifier: Modifier = Modifier
) {
    if(communityComments.isEmpty()){
        Text(
            text = stringResource(id = R.string.no_comments),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Grey,
                fontSize = 12.sp,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_xxdefault))
        ) {
            items(communityComments) { comment ->
                CommunityPostComment(
                    username = comment.username,
                    comment = comment.comment,
                    isOwner = comment.isOwner,
                    userProfileImageUrl = comment.placeholderRes,
                    onDeleteClick = comment.onDeleteClick,
                    onEditClick = comment.onEditClick,
                    onUserClick = comment.onUserClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun MultipleCommunityPostCommentsPreview() {
    MindfulMateTheme {
        MultipleCommunityPostComments(
            communityComments = listOf(
                CommunityAnswerParams(
                    username = "username",
                    comment = "nfbfuindcsgziuhojlnjb nfhuigzuvjchbjnklflrhuewds ifohuigebhfwcsnkjfehwfjs",
                    onDeleteClick = {},
                    onEditClick = {},
                    onUserClick = {}
                ),
                CommunityAnswerParams(
                    username = "username",
                    comment = "nfbfuindcsgziuhojlnjb nfhuigcjidhujvswbnekmkfovjichu<vhwzuvjchbjnklflrhuewds ifohuigebhfwcsnkjfehwfjs",
                    placeholderRes = "R.drawable.ic_launcher_background",
                    onDeleteClick = {},
                    onEditClick = {},
                    onUserClick = {}
                ),
                CommunityAnswerParams(
                    username = "username",
                    comment = "something else",
                    onDeleteClick = {},
                    onEditClick = {},
                    onUserClick = {}
                ),
                CommunityAnswerParams(
                    username = "username",
                    comment = "other comment 123456",
                    onDeleteClick = {},
                    onEditClick = {},
                    onUserClick = {}
                ),
                CommunityAnswerParams(
                    username = "username",
                    comment = "nfbfuindcsgziuhojlnjb nfhuigzuvjchbjnklflrhuewds ifohuigebhfwcsnkjfehwfjs",
                    onDeleteClick = {},
                    onEditClick = {},
                    onUserClick = {}
                )
            )
        )
    }
}

@Preview
@Composable
private fun NoMultipleCommunityPostCommentsPreview() {
    MindfulMateTheme {
        MultipleCommunityPostComments(
            communityComments = emptyList()
        )
    }
}
