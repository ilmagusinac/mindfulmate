package com.example.mindfulmate.presentation.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun MindfulMateIconPlacement(
    modifier: Modifier = Modifier,
    @DrawableRes placeholderRes: Int = R.drawable.ic_profile,
    backgroundColor: Color = DuskyGrey,
    size: Dp = dimensionResource(id = R.dimen.icon_large),
    tint: Color = DuskyBlue
) {
    Box(
        modifier = modifier
            .size(size)
            .background(color = backgroundColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = placeholderRes),
            contentDescription = null,
            modifier = Modifier.size(size / 2),
            colorFilter = ColorFilter.tint(tint)
        )
    }
}

@Preview
@Composable
private fun IconPlacementPreview() {
    MindfulMateTheme {
        MindfulMateIconPlacement()
    }
}