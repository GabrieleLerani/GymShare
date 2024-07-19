package com.project.gains.presentation.plan

//noinspection UsingMaterialAndMaterial3Libraries

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.Plan
import com.project.gains.data.PlotType
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.generateRandomTrainingData
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.presentation.plan.components.SummaryCard
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.toLowerCaseString
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun TabScreen(planViewModel: PlanViewModel, onPlanClicked: (Int) -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Plans", "Progresses")

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> PlansScreen(planViewModel, onPlanClicked)
            1 -> ProgressDetailsScreen()
        }
    }
}

@Composable
fun PlansScreen(
    planViewModel: PlanViewModel,
    onPlanClicked: (Int) -> Unit
) {
    val plans by planViewModel.plans.observeAsState()

    Column {
        plans!!.forEach { plan ->
            CustomCard(plan = plan, onNavigate = { onPlanClicked(plan.id) })
        }
    }
}

@Composable
fun CustomCard(
    plan: Plan,
    onNavigate: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onNavigate() }
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = plan.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            plan.workouts.forEach { workout ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = workout.name,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressDetailsScreen() {
    var selectedMetric by remember { mutableStateOf(TrainingMetricType.BPM) }
    var selectedPeriod by remember { mutableStateOf(PeriodMetricType.MONTH) }
    var selectedPlot by remember { mutableStateOf(PlotType.BAR) }

    val trainingData: MutableList<TrainingData> = generateRandomTrainingData(10).toMutableList()

    var expandedPeriod by remember { mutableStateOf(false) }
    var expandedMetric by remember { mutableStateOf(false) }
    var expandedPlot by remember { mutableStateOf(false) }

    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp),
            ) {
                item {
                    SummaryCard(workouts = 57, calories = 10779, minutes = 539)
                }

                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp)
                    ) {
                        // Period Column
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {

                            ExposedDropdownMenuBox(
                                expanded = expandedPeriod,
                                onExpandedChange = { expandedPeriod = !expandedPeriod }) {

                                OutlinedTextField(
                                    value = toLowerCaseString(selectedPeriod.toString()),
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    label = {Text("Period")},
                                    singleLine = true,
                                    onValueChange = {},
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandedPeriod
                                        )

                                    },
                                    readOnly = true,
                                    modifier = Modifier
                                        .menuAnchor(),
                                    //.fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary, // Set the contour color when focused
                                        unfocusedBorderColor = MaterialTheme.colorScheme.primary // Set the contour color when not focused
                                    )

                                )


                                ExposedDropdownMenu(
                                    expanded = expandedPeriod,
                                    onDismissRequest = { expandedPeriod = false})
                                {
                                    PeriodMetricType.entries.forEach { period ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = toLowerCaseString(period.toString()).take(15) + if (toLowerCaseString(period.toString()).length > 15) "..." else "",
                                                )
                                            },
                                            onClick = {
                                                selectedPeriod = period
                                                expandedPeriod = false
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()

                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // Metric Column
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Top,
                        ) {

                            ExposedDropdownMenuBox(
                                expanded = expandedMetric,
                                onExpandedChange = { expandedMetric = !expandedMetric }) {

                                OutlinedTextField(
                                    value = toLowerCaseString(selectedMetric.toString()),
                                    textStyle = MaterialTheme.typography.bodySmall,
                                    label = { Text("Metric") },
                                    singleLine = true,
                                    onValueChange = {},
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandedMetric
                                        )

                                    },
                                    readOnly = true,
                                    modifier = Modifier
                                        .menuAnchor(),
                                    //.fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary, // Set the contour color when focused
                                        unfocusedBorderColor = MaterialTheme.colorScheme.primary // Set the contour color when not focused
                                    )

                                )

                                ExposedDropdownMenu(
                                    expanded = expandedMetric,
                                    onDismissRequest = { expandedMetric = false })
                                {
                                    TrainingMetricType.entries.forEach { metric ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = toLowerCaseString(metric.toString()).take(
                                                        15
                                                    ) + if (toLowerCaseString(metric.toString()).length > 15) "..." else "",
                                                )
                                            },
                                            onClick = {
                                                selectedMetric = metric
                                                expandedMetric = false
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()

                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // Plot Column
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Top,
                        ) {

                            ExposedDropdownMenuBox(
                                expanded = expandedPlot,
                                onExpandedChange = { expandedPlot = !expandedPlot }) {

                                OutlinedTextField(
                                    value = toLowerCaseString(selectedPlot.toString()),
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    label = {Text("Plot")},
                                    singleLine = true,
                                    onValueChange = {},
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandedPlot
                                        )

                                    },
                                    readOnly = true,
                                    modifier = Modifier
                                        .menuAnchor(),
                                    //.fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary, // Set the contour color when focused
                                        unfocusedBorderColor = MaterialTheme.colorScheme.primary // Set the contour color when not focused
                                    )

                                )

                                ExposedDropdownMenu(
                                    expanded = expandedPlot,
                                    onDismissRequest = { expandedPlot = false})
                                {
                                    PlotType.entries.forEach { plot ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = toLowerCaseString(plot.toString()).take(15) + if (toLowerCaseString(plot.toString()).length > 15) "..." else "",
                                                )
                                            },
                                            onClick = {
                                                selectedPlot = plot
                                                expandedPlot = false
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()

                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                    }
                                }
                            }
                        }
                    }

                    TrainingOverviewChart(
                        trainingData = trainingData,
                        selectedMetric = selectedMetric,
                        selectedPeriod = selectedPeriod,
                        selectedPlotType = selectedPlot
                    )
                }
            }
        }
    }
}

@Composable
fun TrainingOverviewChart(
    trainingData: List<TrainingData>,
    selectedMetric: TrainingMetricType,
    selectedPeriod: PeriodMetricType,
    selectedPlotType: PlotType
) {
    val values= remember { mutableListOf<Float>() }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        trainingData.forEachIndexed { _, data ->
            values.add(data.value.toFloat())
        }
        when (selectedPlotType) {
            PlotType.BAR -> {
                // Bar plot (Vertical bar plot)
                BarPlot(trainingData, selectedPeriod.toString(), selectedMetric.toString())
            }
            PlotType.PIE -> {
                // Pie plot (Circular pie chart)
                PiePlot(trainingData, selectedPeriod.toString())
            }
        }
    }
}

@Composable
fun BarPlot(trainingData: List<TrainingData>, valueType: String, metricType: String) {
    val maxValue = trainingData.maxOf { it.value }
    val barSpacing: Dp = 4.dp
    val density = LocalDensity.current

    Canvas(modifier = Modifier
        .size(400.dp)
        .padding(20.dp)
    ) {
        val totalBars = trainingData.size
        val totalSpacing = (totalBars - 1) * barSpacing.toPx()
        val barWidth = (size.width - totalSpacing) / totalBars
        val graphHeight = size.height

        // Draw X-axis label (Metric Type)
        drawIntoCanvas {
            val textWidth = with(density) { size.width.toDp() }
            val textHeight = with(density) { 16.dp.toPx() }

            it.nativeCanvas.drawText(
                valueType,
                (size.width - textWidth.toPx()) / 2,
                graphHeight + textHeight + 24.dp.toPx(),
                Paint().apply {
                    color = Color.Black.toArgb()
                    textSize = density.run { 16f.sp.toPx() }
                    isAntiAlias = true
                }
            )
        }

        // Draw X-axis
        drawRoundRect(
            color = Color.Gray,
            topLeft = Offset(0f, graphHeight),
            size = Size(size.width, 4.dp.toPx()),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )

        // Draw Y-axis label
        drawIntoCanvas {
            val maxLabel = maxValue.toString()
            val textWidth = with(density) { 16.dp.toPx() }
            val textHeight = with(density) { 12.dp.toPx() }
            rotate(degrees = 270f, pivot = Offset(-textWidth -20 , textHeight + 900)) {

                it.nativeCanvas.drawText(
                    metricType,
                    -textWidth - 50,
                    textHeight + 900,
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = density.run { 14f.sp.toPx() }
                        isAntiAlias = true
                    }
                )
            }

            it.nativeCanvas.drawText(
                maxLabel,
                -textWidth -50 ,
                textHeight,
                Paint().apply {
                    color = Color.Black.toArgb()
                    textSize = density.run { 14f.sp.toPx() }
                    isAntiAlias = true
                }
            )
        }

        // Draw Y-axis
        drawRoundRect(
            color = Color.Gray,
            topLeft = Offset(0f, 0f),
            size = Size(4.dp.toPx(), graphHeight),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )

        // Draw X-axis labels (Bar indices)
        trainingData.forEachIndexed { index, _ ->
            val startX = index * (barWidth + barSpacing.toPx())
            val label = "${index + 1}"

            drawIntoCanvas {
                val textWidth = with(density) { (barWidth - 8.dp.toPx()).toDp() }
                val textHeight = with(density) { 16.dp.toPx() }

                it.nativeCanvas.drawText(
                    label,
                    startX + (barWidth - textWidth.toPx()) / 2,
                    graphHeight + textHeight + 4.dp.toPx(),
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = density.run { 14f.sp.toPx() }
                        isAntiAlias = true
                    }
                )
            }
        }

        // Draw bars
        trainingData.forEachIndexed { index, data ->
            val barHeight = data.value / maxValue.toFloat()
            val startX = index * (barWidth + barSpacing.toPx())
            val startY = graphHeight * (1 - barHeight)

            // Random color generation for the bars
            val color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())

            // Draw background bar
            drawRoundRect(
                color = Color.LightGray,
                topLeft = Offset(startX, startY),
                size = Size(barWidth, graphHeight * barHeight),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )

            // Draw actual bar
            drawRoundRect(
                color = color,
                topLeft = Offset(startX, startY),
                size = Size(barWidth, graphHeight * barHeight),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )
        }
    }
}

@Composable
fun PiePlot(trainingData: List<TrainingData>, valueType: String) {
    val totalValue = trainingData.sumOf { it.value }
    val colorMap = HashMap<Int, Color>()

    Row(modifier = Modifier.padding(30.dp)) {
        // Canvas for pie plot
        Canvas(modifier = Modifier.size(200.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val radius = size.minDimension / 2
            val innerRadius = radius * 0.4f // Adjust inner radius to create the donut effect

            var startAngle = 0f
            val centerX = canvasWidth / 2
            val centerY = canvasHeight - radius / 2

            trainingData.forEachIndexed { index, data ->
                val sweepAngle = (data.value / totalValue.toFloat()) * 360

                // Calculate slice color
                val sliceColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())

                colorMap[index] = sliceColor

                // Draw pie segment
                drawArc(
                    color = sliceColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(centerX - radius, centerY - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = radius - innerRadius)
                )

                // Calculate the middle angle for label position
                val middleAngle = startAngle + sweepAngle / 2
                val middleAngleRad = middleAngle * (PI / 180) // Convert to radians
                val labelRadius = (radius + innerRadius) / 1.3f

                val labelX = centerX + labelRadius * cos(middleAngleRad).toFloat()
                val labelY = centerY + labelRadius * sin(middleAngleRad).toFloat()

                // Draw percentage label
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        "${(data.value / totalValue.toFloat() * 100).toInt()}%",
                        labelX,
                        labelY,
                        Paint().apply {
                            color = if (sliceColor != Color.Yellow) android.graphics.Color.WHITE else android.graphics.Color.BLACK
                            textSize = 30f
                            textAlign = Paint.Align.CENTER
                            isAntiAlias = true
                        }
                    )
                }

                startAngle += sweepAngle
            }
        }

        Spacer(modifier = Modifier.width(30.dp))
        // Canvas for legend
        Canvas(modifier = Modifier.size(100.dp)) {
            val legendOffsetY = 20.dp.toPx()
            val legendBoxSize = 20.dp.toPx()
            val legendTextSize = 30f

            trainingData.forEachIndexed { index, _ ->
                val legendX = 20.dp.toPx()
                val legendY = legendOffsetY + index * (legendBoxSize + 8.dp.toPx())

                // Legend box
                drawRoundRect(
                    color = colorMap[index] ?: Color.White,
                    topLeft = Offset(legendX, legendY),
                    size = Size(legendBoxSize, legendBoxSize),
                    cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                )

                // Legend text
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        "$valueType ${index + 1}",
                        legendX + legendBoxSize + 8.dp.toPx(),
                        legendY + legendBoxSize * 0.75f,
                        Paint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = legendTextSize
                            isAntiAlias = true
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressDetailsScreenPreview() {
    ProgressDetailsScreen()
}