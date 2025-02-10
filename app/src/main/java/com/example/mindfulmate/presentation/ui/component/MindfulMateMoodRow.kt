package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.Green
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.theme.Red

@Composable
fun MindfulMateMoodRow(
    modifier: Modifier = Modifier,
    happyValue: String = "",
    neutralValue: String = "",
    sadValue: String = ""
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_circle),
            contentDescription = null,
            tint = Green,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_small))
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text = happyValue + " " + stringResource(id = R.string.happy),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LightGrey,
                fontSize = 10.sp
            )
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xdefault)))
        Icon(
            painter = painterResource(id = R.drawable.ic_circle),
            contentDescription = null,
            tint = Blue,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_small))
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text =  neutralValue + " " + stringResource(id = R.string.neutral),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LightGrey,
                fontSize = 10.sp
            )
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xdefault)))
        Icon(
            painter = painterResource(id = R.drawable.ic_circle),
            contentDescription = null,
            tint = Red,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_small))
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text =  sadValue + " " + stringResource(id = R.string.sad),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LightGrey,
                fontSize = 10.sp
            )
        )
    }
}

@Preview
@Composable
private fun MoodRowPreview() {
    MindfulMateTheme {
        MindfulMateMoodRow()
    }
}
