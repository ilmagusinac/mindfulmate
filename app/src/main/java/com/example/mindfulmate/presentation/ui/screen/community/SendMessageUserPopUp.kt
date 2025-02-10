package com.example.mindfulmate.presentation.ui.screen.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton
import com.example.mindfulmate.presentation.ui.component.MindfulMateProfileImage

@Composable
fun SendMessageUserPopUp(
    imageUrl: String,
    username: String,
    onSendMessageClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Grey.copy(alpha = 0.5f))
    ) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = onDismissRequest
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                    .shadow(
                        elevation = dimensionResource(id = R.dimen.elevation_medium),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)),
                        clip = false
                    )
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)))
                    .background(Color.White)
                    .padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                ) {
                    MindfulMateProfileImage(
                        imageUrl = imageUrl,
                        size = dimensionResource(id = R.dimen.icon_xlarge)
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                    Text(
                        text = "@$username",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Grey,
                            lineHeight = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MindfulMateButton(
                            text = "Send message",
                            onClick = onSendMessageClick,
                            containerColor = Blue,
                            contentColor = DuskyWhite,
                            disabledContainerColor = Blue,
                            disabledContentColor = DuskyWhite,
                            textColor = DuskyWhite,
                            textPadding = PaddingValues(
                                horizontal = dimensionResource(id = R.dimen.padding_small),
                                vertical = dimensionResource(id = R.dimen.padding_xsmall)
                            ),
                            modifier = Modifier.weight(1f)
                        )

                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun MindfulMateDialogWithSaveAndCancelConfirmationPreview() {
    MindfulMateTheme {
        SendMessageUserPopUp(
            onDismissRequest = {},
            imageUrl = "Ups...",
            username = "@username",
            onSendMessageClick = {}
        )
    }
}
