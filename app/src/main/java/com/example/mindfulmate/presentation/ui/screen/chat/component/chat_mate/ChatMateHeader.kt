package com.example.mindfulmate.presentation.ui.screen.chat.component.chat_mate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.example.mindfulmate.presentation.theme.SuperLightGrey

@Composable
fun ChatMateHeader(modifier: Modifier = Modifier) {
    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = null,
                tint = SuperLightGrey
            )
            Text(
                text = stringResource(id = R.string.chat_with_your_mate),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Grey,
                    fontSize = 20.sp
                )
            )
        }
        HorizontalDivider(thickness = dimensionResource(id = R.dimen.border_light))
    }
}

@Preview
@Composable
private fun ChatMateHeaderPreview() {
    MindfulMateTheme {
        ChatMateHeader()
    }
}
