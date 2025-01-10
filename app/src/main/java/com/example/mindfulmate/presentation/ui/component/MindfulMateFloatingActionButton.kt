package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun MindfulMateFloatingActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Blue,
    contentColor: Color = Blue,
    textColor: Color = DuskyWhite,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall.copy(color = textColor),
    leadingIcon: Painter? = null
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_default)),
        containerColor = containerColor,
        contentColor = contentColor
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_default))
        ) {
            if (leadingIcon != null) {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null,
                    tint = DuskyWhite,
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.spacing_xxdefault))
                )
            }
            Text(
                text = text,
                style = textStyle,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun MindfulMateFloatingActionButtonPreview() {
    MindfulMateTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            MindfulMateFloatingActionButton(
                text = "New Post",
                onClick = {},
                leadingIcon = painterResource(R.drawable.ic_new_post),
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
