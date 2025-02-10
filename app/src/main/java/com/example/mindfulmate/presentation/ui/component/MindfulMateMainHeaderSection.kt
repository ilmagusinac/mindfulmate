package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun MindfulMateMainHeaderSection(
    iconRes: Int,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_medium))
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = Grey,
                fontSize = 20.sp
            )
        )
    }
}

@Preview
@Composable
private fun MindfulMateMainHeaderSectionPreview() {
    MindfulMateTheme {
        MindfulMateMainHeaderSection(
            iconRes = R.drawable.ic_community,
            title = "Community"
        )
    }
}
