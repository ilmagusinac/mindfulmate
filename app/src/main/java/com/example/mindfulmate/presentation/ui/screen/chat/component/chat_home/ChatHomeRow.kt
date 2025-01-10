package com.example.mindfulmate.presentation.ui.screen.chat.component.chat_home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateIconPlacement

@Composable
fun ChatHomeRow(
    username: String,
    lastMessage: String,
    date: String?,
    time: String?,
    isChatClicked: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes placeholderRes: Int = R.drawable.ic_profile,
    newMessage: Boolean? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { isChatClicked() }
    ) {
        MindfulMateIconPlacement(placeholderRes = placeholderRes)
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xdefault)))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "@$username",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.spacing_small))
            )
            Text(
                text = lastMessage,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = LightGrey,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W600
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$date - $time",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W500
                ),
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.spacing_small))
            )
            if(newMessage == true) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    contentDescription = null,
                    tint = Blue,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_small))
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChatHomeRowPreview() {
    MindfulMateTheme {
        ChatHomeRow(
            username = "username",
            lastMessage = "This is our last message",
            date = "12/7/2024",
            time = "12:33",
            isChatClicked = {}
        )
    }
}

@Preview
@Composable
private fun ChatHomeRowNoNewMessagesPreview() {
    MindfulMateTheme {
        ChatHomeRow(
            username = "username",
            lastMessage = "This is our last messagegggggggggggmmmmmmmmmmmmmmmmmmmmmmmmmmmmmgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg",
            date = "12/7/2024",
            time = "12:33",
            isChatClicked = {}
        )
    }
}
