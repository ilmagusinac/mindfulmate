package com.example.mindfulmate.presentation.ui.screen.chat.component

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.util.MessageModel

@Composable
fun MessageRow(
    messageModel: MessageModel,
    modifier: Modifier = Modifier
) {
    val isModel = messageModel.role == stringResource(id = R.string.assistant)

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if (isModel) dimensionResource(id = R.dimen.padding_xsmall) else dimensionResource(
                            id = R.dimen.padding_xlarge
                        ),
                        end = if (isModel) dimensionResource(id = R.dimen.padding_xlarge) else dimensionResource(
                            id = R.dimen.padding_xsmall
                        ),
                        top = dimensionResource(id = R.dimen.padding_xsmall),
                        bottom = dimensionResource(id = R.dimen.padding_xsmall)
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if (isModel) DuskyBlue else Blue)
                    .padding(dimensionResource(id = R.dimen.padding_default))
            ) {
                SelectionContainer {
                    Text(
                        text = messageModel.message,
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
        MessageRow(
            messageModel = MessageModel(
                message = "New message",
                role = "user"
            )
        )
    }
}
