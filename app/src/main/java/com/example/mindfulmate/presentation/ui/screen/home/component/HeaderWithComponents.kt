package com.example.mindfulmate.presentation.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateProfileImage
import com.example.mindfulmate.presentation.util.innerShadow

@Composable
fun HeaderWithComponents(
    username: String,
    profileImage: String,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        BackgroundHeader()
        Column(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_xxmedium),
                vertical = dimensionResource(id = R.dimen.padding_xxxmedium)
            ),
            horizontalAlignment = Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hamburger_menu),
                    contentDescription = stringResource(id = R.string.hamburger_menu_description),
                    tint = DuskyWhite,
                    modifier = Modifier.clickable { onMenuClick() }
                )
                Spacer(modifier = Modifier.weight(1f))
                MindfulMateProfileImage(
                    imageUrl = profileImage,
                    placeholderRes = R.drawable.ic_profile,
                    modifier = Modifier.clickable { onProfileClick() }
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxxmedium)))
            Text(
                text = stringResource(id = R.string.home_hi_section) + username,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 36.sp,
                    lineHeight = 48.sp,
                    color = DuskyWhite
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
            Text(
                text = stringResource(id = R.string.home_subtitle),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    color = DuskyWhite
                )
            )
        }
    }
}


@Composable
fun BackgroundHeader(
    heightIn: Dp = dimensionResource(id = R.dimen.height_xlarge),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = heightIn)
            .background(Blue)
            .innerShadow(
                blur = dimensionResource(id = R.dimen.blur_medium),
                color = DuskyBlue,
                offsetX = dimensionResource(id = R.dimen.offset_default),
                offsetY = dimensionResource(id = R.dimen.offset_default)
            )
    )
}

@Preview
@Composable
private fun BackgroundHeaderPreview() {
    MindfulMateTheme {
        BackgroundHeader()
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    MindfulMateTheme {
        Box {
            Column(modifier = Modifier.fillMaxSize()) {
                HeaderWithComponents(
                    username = "username",
                    profileImage = "",
                    onMenuClick = {},
                    onProfileClick = {}
                )
            }
        }
    }
}
