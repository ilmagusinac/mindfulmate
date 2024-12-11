package com.example.mindfulmate.presentation.ui.screen.emotional_analytics.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateMoodRow

@Composable
fun MoodAnalyticsLineChart(
    moodData: List<Pair<String, Float>>,
    modifier: Modifier = Modifier,
    chartBackgroundColor: Color = Color.White
) {
    if (moodData.isNotEmpty()) {
        val yAxisValues = listOf(30f, 50f, 80f)
        val minY = yAxisValues.first()
        val maxY = yAxisValues.last()

        val pointsData = moodData.mapIndexed { index, entry ->
            val normalizedY = (entry.second - minY) / (maxY - minY)
            Point(index.toFloat(), normalizedY)
        }

        val xAxisLabels = moodData.map { it.first } // Dates for X-axis

        // X-Axis Data
        val xAxisData = AxisData.Builder()
            .steps(pointsData.size - 1)
            .axisStepSize(100.dp)
            .labelData { index -> xAxisLabels.getOrNull(index) ?: "" }
            .labelAndAxisLinePadding(15.dp)
            .build()


        val yAxisLabels = mapOf(
            30f to "Sad",
            50f to "Neutral",
            80f to "Happy"
        )

        val yAxisData = AxisData.Builder()
            .steps(yAxisValues.size - 1)
            .labelData { step ->
                val value = yAxisValues.getOrNull(step)
                value?.let { yAxisLabels[it] ?: it.toString() } ?: ""
            }
            .labelAndAxisLinePadding(30.dp)
            .build()

        val line = Line(
            dataPoints = pointsData,
            lineStyle = LineStyle(
                color = Color.Gray,
                width = 2f
            ),
            intersectionPoint = IntersectionPoint(
                radius = 3.dp,
                color = Color.Transparent
            )
        )

        val lineChartData = LineChartData(
            linePlotData = LinePlotData(lines = listOf(line)),
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(
                color = Color.LightGray,
                lineWidth = 0.5.dp
            ),
            backgroundColor = chartBackgroundColor
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
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
                        text = stringResource(id = R.string.daily_check_in_line_tracking_title),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Grey,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                    Text(
                        text = stringResource(id = R.string.daily_check_in_line_tracking_label),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = LightGrey,
                            fontSize = 10.sp
                        )
                    )
                }
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.height_mlarge)),
                    lineChartData = lineChartData
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
                MindfulMateMoodRow()
            }
        }
    }
}

@Preview
@Composable
private fun MoodAnalyticsLiceChartPreview() {
    MindfulMateTheme {
        val moodData = listOf(
            "2023-11-20" to 30f, // Example mood score for sad
            "2023-11-21" to 55f, // Example mood score for neutral
            "2023-11-22" to 30f, // Example mood score for sad
            "2023-11-23" to 55f, // Example mood score for neutral
            "2023-11-24" to 80f  // Example mood score for happy
        )
        MoodAnalyticsLineChart(moodData = moodData)
    }
}
