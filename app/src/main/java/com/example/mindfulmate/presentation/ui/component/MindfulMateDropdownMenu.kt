package com.example.mindfulmate.presentation.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun MindfulMateDropdownMenu(
    title: String,
    onSelect: (String) -> Unit,
    categories: List<AutocompleteDropdownItems>,
    modifier: Modifier = Modifier
) {
    var category by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(true) }
    val borderColor = if (expanded) Blue else Grey

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = dimensionResource(R.dimen.border_thin),
                    color = borderColor,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners))
                )
                .padding(
                    start = if (selectedCategory) dimensionResource(id = R.dimen.spacing_default) else 0.dp
                )
                .horizontalScroll(rememberScrollState())
        )
        AnimatedVisibility(visible = expanded) {
            Card(
                modifier = Modifier
                    .shadow(
                        elevation = dimensionResource(id = R.dimen.spacing_xxmedium),
                        shape = RectangleShape,
                        clip = false
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                shape = RectangleShape,
            ) {
                categories.forEach { category ->
                    CategoryItems(
                        title = category.username,
                        onSelect = onSelect
                    )
                }
            }
        }
    }
}

data class AutocompleteDropdownItems(
    val username: String
)

@Composable
private fun CategoryItems(
    title: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(
                top = dimensionResource(id = R.dimen.padding_default),
                start = dimensionResource(id = R.dimen.padding_default)
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Blue,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_default)))
        HorizontalDivider(thickness = dimensionResource(id = R.dimen.border_light))
    }
}

@Preview
@Composable
private fun CategoryItemPreview() {
    MindfulMateTheme {
        CategoryItems(
            title = "Username",
            onSelect = {}
        )
    }
}

@Preview
@Composable
private fun MindfulMateDropdownMenuPreview() {
    MindfulMateTheme {
        MindfulMateDropdownMenu(
            title = "Username",
            onSelect = {"ddd"},
            categories = listOf(
                AutocompleteDropdownItems(
                    username = "Ilma"
                ),
                AutocompleteDropdownItems(
                    username = "Dzenisa"
                ),
            )
        )
    }
}
