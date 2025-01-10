package com.example.mindfulmate.presentation.ui.screen.emotional_analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMateSwipeableComposableContent
import com.example.mindfulmate.presentation.ui.screen.emotional_analytics.component.MoodAnalyticsHeaderSection
import com.example.mindfulmate.presentation.ui.screen.emotional_analytics.component.MoodAnalyticsLineChart
import com.example.mindfulmate.presentation.ui.screen.emotional_analytics.component.MoodAnalyticsPieChart
import com.example.mindfulmate.presentation.view_model.daily_checkin.DailyCheckInUiState
import com.example.mindfulmate.presentation.view_model.daily_checkin.DailyCheckInViewModel
import kotlin.math.roundToInt

@Composable
fun MoodAnalyticsScreen(
    viewModel: DailyCheckInViewModel,
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chartData by viewModel.chartData.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pieChartData by viewModel.pieChartData.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchCheckIns()
        viewModel.fetchMoodAveragesForPieChart()
    }

    when (uiState) {
        is DailyCheckInUiState.Loading -> {
            LoadingPlaceholder()
        }

        is DailyCheckInUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = {})
        }

        else -> {
            println("PIE CHART: $pieChartData")
            MoodAnalyticsScreen(
                moodData = chartData,
                pieData = pieChartData,
                happyValue = pieChartData["Happy"]?.let {
                    if (it.isNaN() || it.isInfinite()) 0 else it.roundToInt()
                }?.toString() + "%",

                neutralValue = pieChartData["Neutral"]?.let {
                    if (it.isNaN() || it.isInfinite()) 0 else it.roundToInt()
                }?.toString() + "%",

                sadValue = pieChartData["Sad"]?.let {
                    if (it.isNaN() || it.isInfinite()) 0 else it.roundToInt()
                }?.toString() + "%",
                onGoBackClick = onGoBackClick,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun MoodAnalyticsScreen(
    moodData: List<Pair<String, Float>>,
    pieData: Map<String, Float>,
    happyValue: String = "",
    neutralValue: String = "",
    sadValue: String = "",
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                horizontal = dimensionResource(id = R.dimen.padding_default)
            )
            .verticalScroll(rememberScrollState())
    ) {
        MoodAnalyticsHeaderSection(onGoBackClick = onGoBackClick)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        Text(
            text = stringResource(id = R.string.mood_analytics_title),
            style = MaterialTheme.typography.titleMedium.copy(
                color = DuskyBlue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text = stringResource(id = R.string.mood_analytics_label),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LightGrey,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        if (pieData.isEmpty() || moodData.isEmpty()) {
            Text(
                text = stringResource(id = R.string.no_daily_check_in),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
                    textAlign = TextAlign.Center
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.spacing_xdefault))
            )
        } else {
            MindfulMateSwipeableComposableContent(
                contents = listOf<@Composable () -> Unit>(
                    {
                        MoodAnalyticsPieChart(
                            pieData = pieData,
                            happyValue = happyValue,
                            neutralValue = neutralValue,
                            sadValue = sadValue
                        )
                    },
                    { MoodAnalyticsLineChart(moodData = moodData) }
                )
            )
        }
    }
}

@Preview
@Composable
private fun MoodAnalyticsPreview() {
    MindfulMateTheme {
        MoodAnalyticsScreen(
            moodData = listOf(
                "2023-11-20" to 30f,
                "2023-11-21" to 55f,
                "2023-11-22" to 30f,
                "2023-11-23" to 55f,
                "2023-11-24" to 80f
            ),
        pieData = mapOf(
            "Happy" to 50f,
            "Neutral" to 30f,
            "Sad" to 20f
        ),
        happyValue = "12",
        neutralValue = "10",
        sadValue = "14",
        onGoBackClick = {},
        )
    }
}

@Preview
@Composable
private fun NoMoodAnalyticsPreview() {
    MindfulMateTheme {
        MoodAnalyticsScreen(
            moodData = emptyList(),
            pieData = emptyMap(),
            happyValue = "12",
            neutralValue = "10",
            sadValue = "14",
            onGoBackClick = {},
        )
    }
}
