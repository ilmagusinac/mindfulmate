package com.example.mindfulmate.presentation.ui.screen.emotional_analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMateSwipeableComposableContent
import com.example.mindfulmate.presentation.ui.screen.emotional_analytics.component.MoodAnalyticsHeaderSection
import com.example.mindfulmate.presentation.ui.screen.emotional_analytics.component.MoodAnalyticsLineChart
import com.example.mindfulmate.presentation.ui.screen.emotional_analytics.component.MoodAnalyticsPieChart
import com.example.mindfulmate.presentation.ui.screen.profile.component.profile.ProfileRowSection
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
                happyValue = (pieChartData["Happy"] ?: 0f).roundToInt().toString() + "%",
                neutralValue = (pieChartData["Neutral"] ?: 0f).roundToInt().toString() + "%",
                sadValue = (pieChartData["Sad"] ?: 0f).roundToInt().toString() + "%",
                onGoBackClick = onGoBackClick,
                onSavePdfClick = {},
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
    onSavePdfClick: () -> Unit,
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
        MindfulMateSwipeableComposableContent(
            contents = listOf<@Composable () -> Unit>(
                { MoodAnalyticsLineChart(moodData = moodData) },
                {
                    MoodAnalyticsPieChart(
                        pieData = pieData,
                        happyValue = happyValue,
                        neutralValue = neutralValue,
                        sadValue = sadValue
                    )
                }
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        ProfileRowSection(
            title = stringResource(id = R.string.get_emotional_analytics_pdf_title),
            label = stringResource(id = R.string.get_emotional_analytics_pdf_label),
            placeholderRes = R.drawable.ic_analytics,
            onRowClick = onSavePdfClick,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.spacing_xdefault))
        )
    }
}
