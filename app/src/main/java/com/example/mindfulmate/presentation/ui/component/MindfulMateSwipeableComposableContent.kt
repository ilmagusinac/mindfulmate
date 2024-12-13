package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun MindfulMateSwipeableComposableContent(
    contents: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier
) {
    var currentPage by remember { mutableStateOf(0) }
    val lazyListState = rememberLazyListState()

    Column {
        LazyRow(
            state = lazyListState,
            modifier = modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        if (dragAmount > 0 && currentPage > 0) {
                            currentPage -= 1
                        } else if (dragAmount < 0 && currentPage < contents.size - 1) {
                            currentPage += 1
                        }
                    }
                }
        ) {
            items(contents.size) { index ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .fillParentMaxHeight()
                        .padding(dimensionResource(id = R.dimen.padding_default))
                ) {
                    contents[index]()
                }
            }
        }
        LaunchedEffect(currentPage) {
            lazyListState.animateScrollToItem(currentPage)
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            repeat(contents.size) { index ->
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = if (index == currentPage) Blue else LightGrey,
                            shape = CircleShape
                        )
                )
                if (index < contents.size - 1) {
                    Spacer(modifier = Modifier.width(8.dp)) // Fixed spacing
                }
            }
        }
    }
}

@Preview
@Composable
private fun MindfulMateSwipeablePostsWithIndicatorsPreview() {
    MindfulMateTheme {
        val exampleContents = listOf<@Composable () -> Unit>(
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Page 1", color = Color.White)
                }
            },
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Page 2", color = Color.White)
                }
            },
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Green),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Page 3", color = Color.White)
                }
            }
        )

        MindfulMateSwipeableComposableContent(
            contents = exampleContents,
            modifier = Modifier.fillMaxSize()
        )
    }
}