package com.example.mindfulmate.presentation.ui.screen.resources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.util.DevicesPreview

@Composable
fun ResourcesScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xxxmedium))
    ) {
        Text(
            text = "RESOURCES SCREEN",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@DevicesPreview
@Composable
private fun ResourcesScreenPreview() {
    MindfulMateTheme {
        ResourcesScreen()
    }
}
