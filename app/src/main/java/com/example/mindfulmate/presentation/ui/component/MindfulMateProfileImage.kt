package com.example.mindfulmate.presentation.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun MindfulMateProfileImage(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    @DrawableRes placeholderRes: Int = R.drawable.ic_profile,
    backgroundColor: Color = Color.White,
    size: Dp = 28.dp,
    padding: Dp = 2.dp,
    tint: Color = LightGrey
) {
    Box(
        modifier = modifier
            .size(size)
            .background(color = backgroundColor, shape = CircleShape)
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(size - padding * 2)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = placeholderRes),
                contentDescription = null,
                modifier = Modifier.size(size/2),
                colorFilter = ColorFilter.tint(tint)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileImagePreview() {
    MindfulMateTheme {
        MindfulMateProfileImage(
            imageUrl = null,
            placeholderRes = R.drawable.ic_profile
        )
    }
}
