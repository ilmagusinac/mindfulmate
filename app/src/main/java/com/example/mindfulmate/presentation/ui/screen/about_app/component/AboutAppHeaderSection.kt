package com.example.mindfulmate.presentation.ui.screen.about_app.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun AboutAppHeaderSection(
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            tint = Grey,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.icon_xmedium))
                .clickable { onGoBackClick() }
        )
        Text(
            text = stringResource(id = R.string.about_app),
            style = MaterialTheme.typography.titleLarge.copy(
                color = Grey,
                fontSize = 20.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(0.5f)
        )
    }
}

@Preview
@Composable
private fun ProfileHeaderSectionPreview() {
    MindfulMateTheme {
        AboutAppHeaderSection(onGoBackClick = {})
    }
}
