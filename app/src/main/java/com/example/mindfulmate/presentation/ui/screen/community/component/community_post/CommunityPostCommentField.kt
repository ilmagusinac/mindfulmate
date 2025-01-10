package com.example.mindfulmate.presentation.ui.screen.community.component.community_post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun CommunityPostCommentField(
    onMessageSend: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var message by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(
            thickness = dimensionResource(id = R.dimen.border_light),
            color = LightGrey
        )
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(color = DuskyWhite)
            ) {
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    textStyle = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners))),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.type_message),
                            style = MaterialTheme.typography.titleMedium.copy(color = LightGrey)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (message.isNotEmpty()) {
                                onMessageSend(message)
                                message = ""
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_send),
                                contentDescription = stringResource(id = R.string.send_icon_description)
                            )
                        }
                    },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedTextColor = Grey,
                        unfocusedTextColor = Grey,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun CommunityPostCommentFieldPreview() {
    MindfulMateTheme {
        CommunityPostCommentField(onMessageSend = {})
    }
}
