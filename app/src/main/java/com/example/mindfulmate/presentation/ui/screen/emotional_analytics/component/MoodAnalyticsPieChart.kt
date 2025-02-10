package com.example.mindfulmate.presentation.ui.screen.emotional_analytics.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.Green
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.theme.Red
import com.example.mindfulmate.presentation.ui.component.MindfulMateMoodRow

@Composable
fun MoodAnalyticsPieChart(
    pieData: Map<String, Float>,
    modifier: Modifier = Modifier,
    happyValue: String = "",
    neutralValue: String = "",
    sadValue: String = ""
) {
    if (pieData.isEmpty()) {
        Text(
            text = "No data available for the pie chart",
            modifier = modifier.padding(16.dp)
        )
        return
    }
    val pieChartConfig = PieChartConfig(
        labelType = PieChartConfig.LabelType.PERCENTAGE,
        isAnimationEnable = true,
        showSliceLabels = false,
        animationDuration = 1500
    )

    val slices = pieData.map { (label, percentage) ->
        val color = when (label) {
            "Happy" -> Green
            "Neutral" -> Blue
            "Sad" -> Red
            else -> Color.Gray
        }
        PieChartData.Slice(label, percentage, color)
    }

    val pieChartData = PieChartData(
        plotType = PlotType.Pie,
        slices = slices
    )


    Box(
        modifier = modifier
            .shadow(
                elevation = dimensionResource(id = R.dimen.elevation_medium),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)),
                clip = false
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)))
            .background(Color.White)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_default))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_default))) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(id = R.string.total_check_in_pie_tracking_title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Grey,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                Text(
                    text = stringResource(id = R.string.total_check_in_pie_tracking_label),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = LightGrey,
                        fontSize = 10.sp
                    )
                )
            }
            PieChart(
                modifier = Modifier.size(dimensionResource(id = R.dimen.height_mlarge)),
                pieChartData = pieChartData,
                pieChartConfig = pieChartConfig
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
            MindfulMateMoodRow(
                happyValue = happyValue,
                neutralValue = neutralValue,
                sadValue = sadValue
            )
        }
    }
}


@Preview
@Composable
private fun MoodAnalyticsPieChartPreview() {
    val mockData = mapOf(
        "Happy" to 50f,
        "Neutral" to 30f,
        "Sad" to 20f
    )

    MindfulMateTheme {
        MoodAnalyticsPieChart(
            pieData = mockData,
            happyValue = "50%",
            neutralValue = "30%",
            sadValue = "20%"
        )
    }
}
