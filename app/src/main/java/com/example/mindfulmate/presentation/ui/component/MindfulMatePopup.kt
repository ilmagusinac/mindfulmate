package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.util.PopupMenuItem

@Composable
fun MindfulMatePopup(
    expanded: Boolean,
    items: List<PopupMenuItem>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val offsetY = with(LocalDensity.current) { dimensionResource(id = R.dimen.spacing_xxxxmedium).toPx().toInt() }
    val offsetX = with(LocalDensity.current) { dimensionResource(id = R.dimen.spacing_xmedium).toPx().toInt() }

    if (expanded) {
        Popup(
            alignment = Alignment.TopEnd,
            offset = IntOffset(-offsetX, offsetY),
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = modifier
                    .shadow(elevation = dimensionResource(id = R.dimen.elevation_medium))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                items.forEach { item ->
                    DropdownItem(
                        item = item,
                        onDismissRequest = onDismissRequest,
                        modifier = Modifier.background(
                            Color.Transparent
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun DropdownItem(
    item: PopupMenuItem,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_default)
            )
            .clickable(
                onClick = {
                    item.onClick()
                    onDismissRequest()
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item.icon?.let { painter ->
            Icon(
                painter = painter,
                contentDescription = null,
                tint = Grey
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xxdefault)))
        }
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Grey,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(
                if (item.icon == null) dimensionResource(id = R.dimen.padding_small) else  0.dp
            )
        )
    }
}

@Preview
@Composable
private fun DropdownItemPreview() {
    MindfulMateTheme {
        DropdownItem(
            item = PopupMenuItem(
                icon = painterResource(id = R.drawable.ic_edit),
                label = "New Department",
                onClick = {}
            ),
            onDismissRequest = { /*TODO*/ }
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun EMPPopupMenuPreview() {
    MindfulMateTheme {
        MindfulMatePopup(
            expanded = true,
            onDismissRequest = {},
            items = listOf(
                PopupMenuItem(
                    icon = painterResource(id = R.drawable.ic_edit),
                    label = "Add",
                    onClick = {}
                )
            )
        )
    }
}
