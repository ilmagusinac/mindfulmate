package com.example.mindfulmate.presentation.ui.screen.chat.component.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.domain.model.chat.Message
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun ChatMessageRow(
    currentUser: String?,
    messageModel: Message,
    modifier: Modifier = Modifier
) {
    val isSecondUser = messageModel.senderId != currentUser

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(if (isSecondUser) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if (isSecondUser) dimensionResource(id = R.dimen.padding_xsmall) else dimensionResource(
                            id = R.dimen.padding_xlarge
                        ),
                        end = if (isSecondUser) dimensionResource(id = R.dimen.padding_xlarge) else dimensionResource(
                            id = R.dimen.padding_xsmall
                        ),
                        top = dimensionResource(id = R.dimen.padding_xsmall),
                        bottom = dimensionResource(id = R.dimen.padding_xsmall)
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if (isSecondUser) DuskyBlue else Blue)
                    .padding(dimensionResource(id = R.dimen.padding_default))
            ) {
                SelectionContainer {
                    Text(
                        text = messageModel.text,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = DuskyWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MessageRowPreview() {
    MindfulMateTheme {
        ChatMessageRow(
            messageModel = Message(
                id = "1",
                senderId = "user1",
                text = "Hello!",
                timestamp = null,
                isRead = true
            ),
            currentUser = "user1"
        )
    }
}

@Preview
@Composable
private fun MessageRowSecondUserPreview() {
    MindfulMateTheme {
        ChatMessageRow(
            messageModel = Message(
                id = "1",
                senderId = "user2",
                text = "Hello!",
                timestamp = null,
                isRead = true
            ),
            currentUser = ""
        )
    }
}
