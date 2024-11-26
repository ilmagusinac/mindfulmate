package com.example.mindfulmate.presentation.ui.screen.chat.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.util.MessageModel
/*
@Composable
fun MessageList(
    messageList: List<MessageModel>,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackButtonClick: (() -> Unit)? = null
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackButton && onBackButtonClick != null) {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(id = R.string.navigate_back_description),
                    modifier = Modifier.size(
                        width = dimensionResource(id = R.dimen.width_medium),
                        height = dimensionResource(id = R.dimen.height_medium)
                    )
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xxxmedium)))
        }
        Text(
            text = stringResource(id = R.string.chat_with_your_mate),
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
            style = MaterialTheme.typography.titleLarge.copy(
                color = Grey,
                fontSize = 20.sp
            )
        )
        Spacer(modifier = Modifier.weight(1f))
    }
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        items(messageList.reversed()) {
            MessageRow(messageModel = it)
        }
    }
}*/

@Composable
fun MessageList(
    messageList: List<MessageModel>,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackButtonClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
        ) {
            if (showBackButton && onBackButtonClick != null) {
                IconButton(onClick = { onBackButtonClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(id = R.string.navigate_back_description),
                        modifier = Modifier.size(
                            width = dimensionResource(id = R.dimen.width_medium),
                            height = dimensionResource(id = R.dimen.height_medium)
                        )
                    )
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xxxmedium)))
            }
            Text(
                text = stringResource(id = R.string.chat_with_your_mate),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Grey,
                    fontSize = 20.sp
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }

    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        items(messageList.reversed()) {
            MessageRow(messageModel = it)
        }
    }
}

@Preview
@Composable
private fun MessageListPreview() {
    MindfulMateTheme {
        MessageList(
            messageList = listOf(),
            onBackButtonClick = { println("Back button clicked") }
        )
    }
}