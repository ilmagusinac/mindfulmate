package com.example.mindfulmate.presentation.ui.screen.community.component.community_post

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.profile.component.edit_credential.IconPlacement

@Composable
fun CommunityPostComment(
    username: String,
    comment: String,
    modifier: Modifier = Modifier,
    @DrawableRes placeholderRes: Int = R.drawable.ic_profile
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DuskyGrey)
            .padding(dimensionResource(id = R.dimen.padding_default))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconPlacement(
                placeholderRes = placeholderRes,
                backgroundColor = DuskyWhite
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_medium)))
            Text(
                text = "@$username",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_xxxmedium))) {
            Text(
                text = comment,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
            )
        }
    }
}


@Preview
@Composable
private fun CommunityPostCommentPreview() {
    MindfulMateTheme {
        CommunityPostComment(
            username = "username",
            comment = "comment comment comment comment comment comment comment comment comment"
        )
    }
}
