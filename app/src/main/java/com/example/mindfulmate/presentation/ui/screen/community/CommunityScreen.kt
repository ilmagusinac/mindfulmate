package com.example.mindfulmate.presentation.ui.screen.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.ui.screen.emotional_analytics.component.MoodAnalyticsLineChart

@Composable
fun CommunityScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_default))
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "COMMUNITY SCREEN",
            style = MaterialTheme.typography.titleMedium
        )
    }
}
