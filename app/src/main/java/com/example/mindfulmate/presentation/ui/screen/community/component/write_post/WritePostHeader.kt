package com.example.mindfulmate.presentation.ui.screen.community.component.write_post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton

@Composable
fun WritePostHeader(
    onCloseClick: () -> Unit,
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            tint = Grey,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.padding_default))
                .clickable { onCloseClick() }
        )
        MindfulMateButton(
            text = stringResource(id = R.string.post),
            onClick = onPostClick,
            textPadding = PaddingValues(
                vertical = dimensionResource(R. dimen. padding_small),
                horizontal = dimensionResource(R. dimen. padding_medium)
            ),
            enabled = enabled
        )
    }
}

@Preview
@Composable
private fun WritePostHeaderPreview() {
    MindfulMateTheme {
        WritePostHeader(
            onCloseClick = {},
            onPostClick = {},
            enabled = true
        )
    }
}

@Preview
@Composable
private fun WritePostHeaderDisabledPreview() {
    MindfulMateTheme {
        WritePostHeader(
            onCloseClick = {},
            onPostClick = {},
            enabled = false
        )
    }
}
