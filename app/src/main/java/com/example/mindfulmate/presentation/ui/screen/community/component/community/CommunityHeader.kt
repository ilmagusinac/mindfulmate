package com.example.mindfulmate.presentation.ui.screen.community.component.community

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.theme.Yellow

@Composable
fun CommunityHeader(
    title: String,
    membersCount: String,
    description: String,
    isSaved: Boolean,
    onBackButtonClick: () -> Unit,
    onSaveStateChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int = R.drawable.ic_splash
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)))
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Grey.copy(alpha = 0.2f))
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_xxmedium),
                    vertical = dimensionResource(id = R.dimen.padding_mediumx)
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = DuskyWhite,
                    modifier = Modifier
                        .size(
                            width = dimensionResource(id = R.dimen.width_medium),
                            height = dimensionResource(id = R.dimen.height_medium)
                        )
                        .clickable { onBackButtonClick() }
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xdefault)))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(color = DuskyWhite),
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(
                        id = if (isSaved) R.drawable.ic_saved else R.drawable.ic_save
                    ),
                    contentDescription = null,
                    tint = DuskyWhite,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.height_medium))
                        .clickable { onSaveStateChanged(isSaved) }
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxdefault)))
            }
            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_default))){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        tint = Yellow
                    )
                    Text(
                        text = "$membersCount members",
                        style = MaterialTheme.typography.bodyMedium.copy(color = DuskyWhite),
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_xsmall))
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                Text(
                    text = "\"$description\"",
                    style = MaterialTheme.typography.bodyMedium.copy(color = DuskyWhite),
                )
            }
        }
    }
}

@Preview
@Composable
private fun CommunityHeaderPreview() {
    MindfulMateTheme {
        CommunityHeader(
            title = "Stress Reliefe",
            membersCount = "12",
            description = "fjrubfvrbhjndkljihugzhcfnbjnklhugzuftcgvhbjnkjigzfgvhbjnkjh oihgvbjnk ghijv fhfhfhhff ",
            isSaved = true,
            onBackButtonClick = {},
            onSaveStateChanged = {}
        )
    }
}
