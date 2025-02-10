package com.example.mindfulmate.presentation.ui.screen.about_app.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun AboutAppRow(
    title: String,
    expandedContent: (@Composable () -> Unit)? = null,
    isExpandable: Boolean = true,
    modifier: Modifier = Modifier,
    onArrowClick: () -> Unit = {},
    isDefaultExpanded: Boolean = false
) {
    var isExpanded by remember { mutableStateOf(isDefaultExpanded) }

    Box(
        modifier = modifier
            .background(Color.White)
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Grey,
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                if (isExpandable) {
                    Icon(
                        painter = if (isExpanded) painterResource(id = R.drawable.ic_arrow_down)
                        else painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.icon_small))
                            .clickable { isExpanded = !isExpanded },
                        tint = LightGrey
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.icon_small))
                            .clickable { onArrowClick() },
                        tint = LightGrey
                    )
                }
            }
            if (isExpandable && isExpanded && expandedContent != null) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                expandedContent()
            }
        }
    }
}

@Preview
@Composable
private fun ContentRowPreview() {
    MindfulMateTheme {
        AboutAppRow(
            title = "My Account",
            expandedContent = {}
        )
    }
}
