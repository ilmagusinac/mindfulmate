package com.example.mindfulmate.presentation.ui.screen.community.util

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton
import com.example.mindfulmate.presentation.ui.component.MindfulMateIconPlacement

@Composable
fun CommunityUserPopup(
    username: String,
    onSendMessageClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes placeholderRes: Int = R.drawable.ic_profile,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize().background(LightGrey.copy(alpha = 0.2f))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .shadow(
                    elevation = dimensionResource(id = R.dimen.elevation_medium),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)),
                    clip = false
                )
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)))
                .background(Color.White)
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_default),
                    horizontal = dimensionResource(id = R.dimen.padding_xxxmedium)
                )
        ) {
            MindfulMateIconPlacement(
                placeholderRes = placeholderRes
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
            Text(
                text = "@$username",
                style = MaterialTheme.typography.bodyMedium.copy(color = Grey)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
            MindfulMateButton(
                text = stringResource(id = R.string.send_message),
                onClick = onSendMessageClick,
                textPadding = PaddingValues(
                    vertical = dimensionResource(R.dimen.padding_small),
                    horizontal = dimensionResource(R.dimen.padding_xxmedium)
                )
            )
        }
    }
}

@Preview
@Composable
private fun CommunityUserPopupPreview() {
    MindfulMateTheme {
        CommunityUserPopup(
            username = "username",
            onSendMessageClick = {}
        )
    }
}
