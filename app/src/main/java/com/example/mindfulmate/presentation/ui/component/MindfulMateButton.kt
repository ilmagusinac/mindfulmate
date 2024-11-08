package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
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
    textPadding: PaddingValues = PaddingValues(
        vertical = dimensionResource(R.dimen.padding_small),
        horizontal = dimensionResource(R.dimen.padding_medium)
    ),
    containerColor: Color = Blue,
    contentColor: Color = Blue,
    disabledContainerColor: Color = Blue,
    disabledContentColor: Color = Blue,
    borderColor: Color = Blue,
    textColor: Color = DuskyWhite,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall.copy(color = textColor)
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
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
        )
    ) {
        Text(
            text = text,
            style = textStyle,
            maxLines = 1
        )
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
