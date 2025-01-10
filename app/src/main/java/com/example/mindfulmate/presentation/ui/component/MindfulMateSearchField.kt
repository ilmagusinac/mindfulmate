package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.util.SearchItem
import com.example.mindfulmate.presentation.util.filterSearchResults

@Composable
fun MindfulMateSearchField(
    text: TextFieldValue,
    placeholder: String,
    allSearchItems: List<SearchItem>,
    onTextValueChange: (TextFieldValue) -> Unit,
    onSearchItemClick: (SearchItem) -> Unit,
    background: Color = DuskyGrey,
    isSearching: Boolean = false,
    modifier: Modifier = Modifier
) {
    val filteredResults = remember(text.text, allSearchItems) {
        filterSearchResults(allSearchItems, text.text)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)))
                .background(background)
        ) {
            TextField(
                value = text,
                placeholder = {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.titleMedium.copy(color = LightGrey)
                    )
                },
                onValueChange = onTextValueChange,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small))),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Grey,
                    unfocusedTextColor = Grey,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_xsmall))
                    )
                }
            )
        }

        if (text.text.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .weight(1f)
            ) {
                if (filteredResults.isNotEmpty()) {
                    if (isSearching) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicatorIcon(modifier = Modifier.align(Alignment.Center))
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(filteredResults) { item ->
                                SearchResultItem(
                                    searchItem = item,
                                    onSearchItemClick = {
                                        onTextValueChange(TextFieldValue(item.label))
                                        onSearchItemClick(item)
                                    }
                                )
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_default)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_matches_for_given_input),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Grey,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(
    searchItem: SearchItem,
    onSearchItemClick: (SearchItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_default))
            .clickable { onSearchItemClick(searchItem) }
    ) {
        MindfulMateIconPlacement(placeholderRes = searchItem.placeholderRes)
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_default)))
        Text(
            text = searchItem.label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Grey,
                fontSize = 14.sp
            )
        )
    }
}

@Preview
@Composable
private fun SearchResultItemPreview() {
    MindfulMateTheme {
        SearchResultItem(
            searchItem = SearchItem(
                label = "Label"
            ),
            onSearchItemClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun MindfulMateSearchFieldLeadingIconPreview() {
    MindfulMateTheme {
        var textState by remember { mutableStateOf(TextFieldValue("")) }
        val searchItems = listOf(
            SearchItem("123", "JohnDoe", R.drawable.ic_profile),
            SearchItem("123", "JaneDoe", R.drawable.ic_profile),
            SearchItem("123", "OtherUser", R.drawable.ic_profile),
            SearchItem("123", "SampleUser", R.drawable.ic_profile),
            SearchItem("123", "AnotherUser", R.drawable.ic_profile)
        )

        Box {
            Column(modifier = Modifier.fillMaxSize()) {

                Box {
                    MindfulMateSearchField(
                        text = textState,
                        placeholder = "Search users...",
                        allSearchItems = searchItems,
                        onTextValueChange = { textState = it },
                        onSearchItemClick = { selectedItem ->
                            println("Selected User: ${selectedItem.label}")
                        },
                        isSearching = false
                    )
                }
                Text(
                    text = "somethinv"
                )
            }
        }
    }
}
