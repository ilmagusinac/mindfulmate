package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun MindfulMateButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textPadding: PaddingValues = PaddingValues(
        vertical = dimensionResource(R.dimen.padding_small),
        horizontal = dimensionResource(R.dimen.padding_xxlarge)
    ),
    containerColor: Color = Blue,
    contentColor: Color = Blue,
    disabledContainerColor: Color = Blue.copy(alpha = 0.5f),
    disabledContentColor: Color = Blue.copy(alpha = 0.5f),
    borderColor: Color = if (enabled) Blue else Color.Transparent,
    textColor: Color = DuskyWhite,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall.copy(color = textColor),
    leadingIcon: Painter? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier.wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
        ),
        contentPadding = textPadding,
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.border_thin),
            color = borderColor
        ),
        enabled = enabled
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
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

@Preview(showBackground = true)
@Composable
private fun MindfulMateButtonPreview() {
    MindfulMateTheme {
        MindfulMateButton(
            onClick = {},
            text = "Sign In With Google",
            textStyle = MaterialTheme.typography.labelSmall.copy(
                color = DuskyWhite,
                fontSize = 10.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MindfulMateButtonWithLeadingIconPreview() {
    MindfulMateTheme {
        MindfulMateButton(
            onClick = {},
            text = "Sign In With Google",
            textStyle = MaterialTheme.typography.labelSmall.copy(
                color = DuskyWhite,
                fontSize = 10.sp
            ),
            leadingIcon = painterResource(id = R.drawable.ic_resources)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MindfulMateButtonDisabledPreview() {
    MindfulMateTheme {
        MindfulMateButton(
            onClick = {},
            text = "Sign In With Google",
            textStyle = MaterialTheme.typography.labelSmall.copy(
                color = DuskyWhite,
                fontSize = 10.sp
            ),
            enabled = false
        )
    }
}
