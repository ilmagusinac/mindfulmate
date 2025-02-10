package com.example.mindfulmate.presentation.ui.screen.community.component.community_post

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateProfileImage
import com.example.mindfulmate.presentation.ui.screen.profile.component.edit_credential.IconPlacement

@Composable
fun CommunityPostQuestion(
    username: String,
    date: String,
    questionTitle: String,
    questionDescription: String,
    likeCount: String,
    commentCount: String,
    isSaved: Boolean,
    onLikeClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    userProfileImageUrl: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DuskyGrey)
            .padding(dimensionResource(id = R.dimen.padding_default))
    ) {
        Row {
            MindfulMateProfileImage(
                imageUrl = userProfileImageUrl,
                backgroundColor = DuskyWhite,
                size = dimensionResource(id = R.dimen.icon_large),
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_medium)))
            Column {
                Text(
                    text = username,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Grey,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = LightGrey,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
            }
        }
        Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_xxxmedium))) {
            Text(
                text = questionTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Grey,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxdefault)))
            Text(
                text = questionDescription,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(
                        id = if (isSaved) R.drawable.ic_liked else R.drawable.ic_like
                    ),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier
                        .padding(end = dimensionResource(id = R.dimen.spacing_small))
                        .clickable { onLikeClick(isSaved) }
                )
                Text(
                    text = likeCount,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = LightGrey,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W300
                    )
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xdefault)))
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.spacing_small))
                )
                Text(
                    text = commentCount,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = LightGrey,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W300
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun CommunityPostQuestionPreview() {
    MindfulMateTheme {
        CommunityPostQuestion(
            username = "username",
            date = "12/3/2024",
            questionTitle = "How will you manage stress",
            questionDescription = "Hello everyone can you please tell if you are feeling stressed during exams and how do you cope with stress management. New new new new new new new newgtgjtgrnjrntrnjtrnbt btt ntgtnjgntngtj gnttngjtnj  gntjgt",
            likeCount = "23",
            commentCount = "14",
            onLikeClick = {},
            isSaved = true
        )
    }
}
