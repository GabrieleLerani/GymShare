package com.project.gains.presentation.components

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AlertDialog
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries,
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.GymPost
import com.project.gains.data.Option
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.Song
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout
import com.project.gains.data.bottomNavItems
import com.project.gains.data.generateOptions
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.MusicEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.events.ShareContentEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.NewPlanScreen
import com.project.gains.theme.GainsAppTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(45.dp),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Share Icon",
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp),
            tint = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun AddExerciseItem(exercise: Exercise, onItemClick: (Exercise) -> Unit,onItemClick2: () -> Unit,isSelected: Boolean,isToAdd: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = exercise.gifResId ?: R.drawable.logo),
            contentDescription = exercise.name,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = exercise.name,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row( modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        )  {
            IconButton(onClick = {
                if (isToAdd==true){
                    onItemClick2()
                }else{
                    onItemClick(exercise)
                }
            }) {
                Icon(imageVector = if(isToAdd) Icons.Default.Add else Icons.Default.ArrowForwardIos, contentDescription = "Exercise Button", tint = MaterialTheme.colorScheme.surface)

            }
        }
    }
}


@Composable
fun TrainingTypePopup(
    popupVisible: MutableState<Boolean>,
    onDismiss: () -> Unit,
    onOptionSelected: (TrainingType) -> Unit,
    selectedMetric: TrainingType
) {
    if (popupVisible.value) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {

            },
            text = {
                Spacer(modifier = Modifier.height(10.dp))
                Column(Modifier.padding(top=20.dp)) {
                    TrainingType.entries.forEach { metric ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (metric == selectedMetric) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onOptionSelected(metric) }
                        ) {
                            Text(
                                text = metric.name,
                                color = if (metric == selectedMetric)MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            },
            buttons = {
                Column {
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { popupVisible.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel",color = Color.Red)
                        }
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(50.dp)
                                .background(Color.Gray)
                        )
                        TextButton(
                            onClick = { popupVisible.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Confirm", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp) // Optional padding to reduce width
        )
    }
}
@Composable
fun PeriodPopup(
    selectedPeriodMap: MutableList<PeriodMetricType>?,
    popupVisible: MutableState<Boolean>,
    onDismiss: () -> Unit,
    onOptionSelected: (PeriodMetricType) -> Unit,
    selectedMetric: PeriodMetricType
) {
    if (popupVisible.value) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {

            },
            text = {
                Spacer(modifier = Modifier.height(10.dp))
                Column(Modifier.padding(top=20.dp)) {
                    selectedPeriodMap?.forEach { metric ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (metric == selectedMetric) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onOptionSelected(metric) }
                        ) {
                            Text(
                                text = metric.name,
                                color = if (metric == selectedMetric)MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            },
            buttons = {
                Column {
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { popupVisible.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel",color = Color.Red)
                        }
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(50.dp)
                                .background(Color.Gray)
                        )
                        TextButton(
                            onClick = { popupVisible.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Confirm", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp) // Optional padding to reduce width
        )
    }
}

@Preview
@Composable
fun p(){
    GainsAppTheme {
        MusicPopup(popup = true, musicHandler = {} , currentSong = Song("ciao","ciao","ciao"), totalTime = "12")
    }
}
// Utility function to format time
fun formatTime(seconds: Float): String {
    val minutes = (seconds / 60).toInt()
    val remainingSeconds = (seconds % 60).toInt()
    return String.format("%d:%02d", minutes, remainingSeconds)
}
@Composable
fun MusicPopup(popup: Boolean, musicHandler: (MusicEvent) -> Unit, currentSong: Song, totalTime: String) {
    if (popup) {
        val play = remember { mutableStateOf(false) }
        var currentTime by remember { mutableStateOf(0f) }
        var songTotalTime:Float = 165f

        // Simulate a timer to update the current playback position
        LaunchedEffect(play.value) {
            if (play.value) {
                while (currentTime < songTotalTime && play.value) {
                    delay(100L) // Shorter delay for finer granularity
                    currentTime += 0.1f // Increment by 0.1 second

                    // Adjust the timer to handle seconds incrementing correctly
                    if (currentTime % 1.0f >= 0.9f) {
                        currentTime = (currentTime - (currentTime % 1.0f)) + 1f
                    }

                    if (currentTime >= songTotalTime) {
                        play.value = false
                        musicHandler(MusicEvent.Forward)
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .padding(16.dp)
                .width(500.dp)
                .height(250.dp)
                .background(Color.Green, RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color.Green)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.spotify_icon),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(64.dp)
                        .padding(16.dp)
                )
                Text(
                    text = currentSong.title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(  horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = "Artist : ${currentSong.singer}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Album : ${currentSong.album}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Time: ${formatTime(currentTime)}/${formatTime(songTotalTime)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ElevatedButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = {
                            musicHandler(MusicEvent.Rewind)
                            currentTime=0f
                            songTotalTime=Random.nextFloat() * (300 - 120) + 120

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastRewind,
                            contentDescription = "rewind",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    ElevatedButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = {
                            play.value = !play.value
                            musicHandler(MusicEvent.Music)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = if (play.value) Icons.Default.Stop else Icons.Default.PlayArrow,
                            contentDescription = "play",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    ElevatedButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = {
                            musicHandler(MusicEvent.Forward)
                            currentTime=0f
                            songTotalTime=Random.nextFloat() * (300 - 120) + 120 },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastForward,
                            contentDescription = "forward",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun ProgressChartCard(
    chartPreview: ProgressChartPreview,
    onItemClick: (ProgressChartPreview) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(chartPreview)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer, // Orange
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Text color
        ),
        shape = RoundedCornerShape(8.dp) // Add rounded corners to the card
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = chartPreview.name,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = chartPreview.imageResId),
                contentDescription = null, // Provide a meaningful content description
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
@Composable
fun SocialMediaIcon(icon: Int, onClick: () -> Unit, isSelected: Boolean) {
    androidx.compose.material.IconButton(onClick = onClick) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Transparent,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .padding(6.dp) // Add padding between the border and the image icon
        ) {
            androidx.compose.material.Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(38.dp) // Adjust the size of the icon to fit within the padding
            )
        }
    }
}

@Composable
fun OptionCheckbox(
    option: Option,
    onOptionSelected: (Boolean) -> Unit
) {
    val isChecked = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = option.name,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = {
                    isChecked.value = it
                    onOptionSelected(it)
                },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedColor = MaterialTheme.colorScheme.primary,
                    checkedColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun TrainingOverviewChart(
    trainingData: List<TrainingData>,
    selectedMetric: TrainingMetricType,
    selectedPeriod: PeriodMetricType,
    shareHandler: (ShareContentEvent.SharePlot) -> Unit,
    selectedPlotType: ProgressChartPreview
) {
    val values= remember {
        mutableListOf<Float>()
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        trainingData.forEachIndexed { index, data ->
            values.add(data.value.toFloat())
           /* Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Y-axis label (period)
                androidx.compose.material3.Text(
                    text = "${selectedPeriod.name} ${index + 1}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                // Spacer to separate Y-axis label from plot
                Spacer(modifier = Modifier.width(16.dp))
            }*/
        }
            when (selectedPlotType.imageResId) {
                R.drawable.plot2 -> {
                    // Bar plot (Vertical bar plot)
                    BarPlot(trainingData,"Months","Bpm")
                }
                R.drawable.plot3 -> {
                    // Pie plot (Circular pie chart)
                    PiePlot(trainingData,"Months","Bpm")
                }
            }
    }
}




@Composable
fun BarPlot(trainingData: List<TrainingData>, valueType: String, metricType: String) {
    val maxValue = trainingData.maxOf { it.value }
    val barSpacing: Dp = 4.dp
    val density = LocalDensity.current

    Canvas(modifier = Modifier.size(400.dp).padding(20.dp)) {
        val totalBars = trainingData.size
        val totalSpacing = (totalBars - 1) * barSpacing.toPx()
        val barWidth = (size.width - totalSpacing) / totalBars
        val graphHeight = size.height

        // Draw X-axis label (Metric Type)
        drawIntoCanvas {
            val label = valueType
            val textWidth = with(density) { size.width.toDp() }
            val textHeight = with(density) { 16.dp.toPx() }

            it.nativeCanvas.drawText(
                label,
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
            size = androidx.compose.ui.geometry.Size(size.width, 4.dp.toPx()),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )

        // Draw Y-axis label
        drawIntoCanvas {
            val maxLabel = maxValue.toString()
            val textWidth = with(density) { 16.dp.toPx() }
            val textHeight = with(density) { 12.dp.toPx() }

            it.nativeCanvas.drawText(
                metricType,
                -textWidth -50 ,
                textHeight + 900,
                Paint().apply {
                    color = Color.Black.toArgb()
                    textSize = density.run { 14f.sp.toPx() }
                    isAntiAlias = true
                }
            )

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
            size = androidx.compose.ui.geometry.Size(4.dp.toPx(), graphHeight),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )

        // Draw X-axis labels (Bar indices)
        trainingData.forEachIndexed { index, data ->
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
                size = androidx.compose.ui.geometry.Size(barWidth, graphHeight * barHeight),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )

            // Draw actual bar
            drawRoundRect(
                color = color,
                topLeft = Offset(startX, startY),
                size = androidx.compose.ui.geometry.Size(barWidth, graphHeight * barHeight),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )
        }
    }
}
@Composable
fun PiePlot(trainingData: List<TrainingData>, valueType: String, metricType: String) {
    val totalValue = trainingData.sumOf { it.value }
    val colorMap = HashMap<Int,Color>()

    Row(modifier = Modifier.padding(20.dp)) {
        // Canvas for pie plot
        Canvas(modifier = Modifier.size(240.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val radius = size.minDimension / 2

            var startAngle = 0f
            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2

            trainingData.forEachIndexed { index, data ->
                val sweepAngle = (data.value / totalValue.toFloat()) * 360

                // Calculate slice color
                val color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())

                colorMap.set(index,color)

                // Draw pie segment
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset(centerX - radius, centerY - radius),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                )

                startAngle += sweepAngle
            }
        }

        // Canvas for legend
        Canvas(modifier = Modifier.weight(1.5f)) {
            val legendOffsetY = 20.dp.toPx()
            val legendBoxSize = 20.dp.toPx()
            val legendTextSize = 30f

            trainingData.forEachIndexed { index, data ->
                val legendX = 20.dp.toPx()
                val legendY = legendOffsetY + index * (legendBoxSize + 8.dp.toPx())

                // Legend box
                drawRoundRect(
                    color = colorMap.get(index) ?: Color.White,
                    topLeft = Offset(legendX, legendY),
                    size = androidx.compose.ui.geometry.Size(legendBoxSize, legendBoxSize),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx(), 4.dp.toPx())
                )

                // Legend text
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        "Month ${index +1}",
                        legendX + legendBoxSize + 8.dp.toPx(),
                        legendY + legendBoxSize * 0.75f,
                        Paint().apply {
                            color = Color.Black.toArgb()
                            textSize = legendTextSize
                            isAntiAlias = true
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun SocialMediaRow(
    icon: Int,
    isLinked: Boolean,
    linkHandler: (LinkAppEvent) -> Unit,
    clickedApps: MutableState<MutableList<Int>>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        SocialMediaIcon(icon = icon, onClick = {  }, isSelected = isLinked)
        Spacer(modifier = Modifier.width(50.dp))
        if (isLinked || clickedApps.value.contains(icon)) {
            androidx.compose.material.Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Linked Icon",
                modifier = Modifier.padding(20.dp)
            )
        } else if (!isLinked && !clickedApps.value.contains(icon)){
            IconButton(
                onClick =
                {
                    clickedApps.value = clickedApps.value.toMutableList().apply { add(icon) }
                    linkHandler(LinkAppEvent.LinkApp(icon)) },
                modifier = Modifier.size(60.dp),
                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Save Icon",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(10.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun GeneralCard(imageResId: Int, title: String, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .height(150.dp)
            .background(Color.Gray, RoundedCornerShape(16.dp))
            .clickable {
                onItemClick()
            }
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)) // Clip to the rounded corners
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 300f
                    ),
                    RoundedCornerShape(16.dp)
                )
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = currentRoute(navController)

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.onSurface,
        contentColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.height(64.dp) // Adjust the height of the BottomNavigation
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute == item.route
            val iconColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.surface
            val iconSize = 32.dp
            val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.surface

            BottomNavigationItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(6.dp)) // Add spacing between icon and text
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(iconSize), // Adjust the size of the icon
                            tint = iconColor
                        )
                        //Spacer(modifier = Modifier.height(6.dp)) // Add spacing between icon and text
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp, // Set the desired text size here
                        maxLines = 1, // Limit to one line to prevent overflow
                        color = textColor
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                alwaysShowLabel = true // This will hide labels when not selected
            )
        }
    }
}

@Composable
fun TopBar(navController: NavController, message: String, button: @Composable () -> Unit,button1: @Composable () -> Unit) {
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            button1()

            Text(
                text = message,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
            button()
        }
    }
}
@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
@Composable
fun NewPlanPagePopup(showPopup : MutableState<Boolean>, onItemClick: () -> Unit,navController: NavController,
                     createHandler: (CreateEvent) -> Unit) {
    val allOptions = remember { generateOptions() } // List to store selected options
    val options = remember { mutableStateListOf<Option>() } // List to store selected options
    var popupVisible = remember { mutableStateOf(false) }
    var selectedPeriod by remember { mutableStateOf(PeriodMetricType.MONTH) }
    var selectedTraining by remember { mutableStateOf(TrainingType.STRENGTH) }
    val selectedExerciseTypes = remember { mutableStateListOf<TrainingType>() } // List to store selected options
    val selectedMetrics = remember { mutableStateListOf<TrainingMetricType>() } // List to store selected options
    val selectedMusic = remember { mutableStateOf(false) } // List to store selected options
    val selectedBackup = remember { mutableStateOf(false) } // List to store selected options

    // Function to handle checkbox state change
    fun onOptionSelected(option: Option, isChecked: Boolean) {
        if (isChecked) {
            options.add(option)
        } else {
            options.remove(option)
        }
    }
    if (showPopup.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(20.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
            ) {
                item {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 290.dp),
                        horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = { showPopup.value=false }) {
                            Icon(imageVector = Icons.Default.Close , contentDescription = "Close Icon")
                        }
                    }  }

                item { Text(
                    text = "Create New Plan",
                    style = MaterialTheme.typography.headlineMedium
                ) }
                item {
                    Text(
                        text = "Set the following options and press the generate plan button to create a personalized workout plan based on your needs.",
                        style = MaterialTheme.typography.bodySmall,
                    ) }
                item { Spacer(modifier = Modifier.height(10.dp)) }

                    item {
                        androidx.compose.foundation.layout.Spacer(
                            modifier = androidx.compose.ui.Modifier.height(
                                10.dp
                            )
                        )
                    }
                    item {
                        Text(
                            text = "Choose if you want to have music while training",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                            color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp) // Add padding for better spacing
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(16.dp)
                                ) // Optional background for emphasis
                                .padding(16.dp) // Inner padding for the text itself
                        )
                    }
                    item {
                        com.project.gains.presentation.components.OptionCheckbox(
                            option = allOptions[0],
                            onOptionSelected = { isChecked ->
                                selectedMusic.value = true
                                onOptionSelected(
                                    options[0],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        androidx.compose.material.Text(
                            text = "Choose if you want to have backup on your workout",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                            color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp) // Add padding for better spacing
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(16.dp)
                                )
                                .padding(16.dp) // Inner padding for the text itself
                        )
                    }
                    item {
                        com.project.gains.presentation.components.OptionCheckbox(
                            option = allOptions[1],
                            onOptionSelected = { isChecked ->
                                selectedBackup.value = true
                                onOptionSelected(
                                    options[1],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        androidx.compose.material.Text(
                            text = "Choose type of exercises to include",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                            color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp) // Add padding for better spacing
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(16.dp)
                                )
                                .padding(16.dp) // Inner padding for the text itself
                        )
                    }
                    item {
                        com.project.gains.presentation.components.OptionCheckbox(
                            option = allOptions[2],
                            onOptionSelected = { isChecked ->
                                selectedExerciseTypes.add(com.project.gains.data.TrainingType.STRENGTH)
                                onOptionSelected(
                                    options[2],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        com.project.gains.presentation.components.OptionCheckbox(
                            option = allOptions[3],
                            onOptionSelected = { isChecked ->
                                selectedExerciseTypes.add(com.project.gains.data.TrainingType.CALISTHENICS)

                                onOptionSelected(
                                    options[3],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        com.project.gains.presentation.components.OptionCheckbox(
                            option = allOptions[4],
                            onOptionSelected = { isChecked ->
                                selectedExerciseTypes.add(com.project.gains.data.TrainingType.STRENGTH)

                                onOptionSelected(
                                    options[4],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        com.project.gains.presentation.components.OptionCheckbox(
                            option = allOptions[5],
                            onOptionSelected = { isChecked ->
                                selectedExerciseTypes.add(com.project.gains.data.TrainingType.CROSSFIT)

                                onOptionSelected(
                                    options[5],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        androidx.compose.material.Text(
                            text = "Choose the training type",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                            color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp) // Add padding for better spacing
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(16.dp)
                                )
                                .padding(16.dp) // Inner padding for the text itself
                        )
                    }
                    item {
                        androidx.compose.foundation.layout.Row(
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                            modifier = androidx.compose.ui.Modifier.padding(vertical = 8.dp)
                        ) {
                            androidx.compose.material.Text(
                                selectedTraining.name,
                                style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                            )

                            androidx.compose.material.IconButton(onClick = {
                                popupVisible.value = true
                            }) {
                                androidx.compose.material3.Icon(
                                    androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                                    contentDescription = "Change Metric"
                                )
                            }
                        }
                    }
                    item {
                        androidx.compose.material.Text(
                            text = "Choose the plan period",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                            color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp) // Add padding for better spacing
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(16.dp)
                                )
                                .padding(16.dp) // Inner padding for the text itself
                        )
                    }
                    item {
                        androidx.compose.foundation.layout.Row(
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                            modifier = androidx.compose.ui.Modifier.padding(vertical = 8.dp)
                        ) {
                            androidx.compose.material.Text(
                                selectedPeriod.name,
                                style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                            )

                            androidx.compose.material.IconButton(onClick = {
                                popupVisible.value = true
                            }) {
                                androidx.compose.material3.Icon(
                                    androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                                    contentDescription = "Change Metric"
                                )
                            }
                        }
                    }
                    item {
                        androidx.compose.material.Text(
                            text = "Choose the metrics to track",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                            color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp) // Add padding for better spacing
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(16.dp)
                                )
                                .padding(16.dp) // Inner padding for the text itself
                        )
                    }
                    item {
                        com.project.gains.presentation.components.OptionCheckbox(
                            option = allOptions[6],
                            onOptionSelected = { isChecked ->
                                selectedMetrics.add(com.project.gains.data.TrainingMetricType.BPM)

                                onOptionSelected(
                                    options[6],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        com.project.gains.presentation.components.OptionCheckbox(
                            option = allOptions[7],
                            onOptionSelected = { isChecked ->
                                selectedMetrics.add(com.project.gains.data.TrainingMetricType.KCAL)

                                onOptionSelected(
                                    options[7],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        com.project.gains.presentation.components.OptionCheckbox(
                            option = allOptions[8],
                            onOptionSelected = { isChecked ->
                                selectedMetrics.add(com.project.gains.data.TrainingMetricType.INTENSITY)

                                onOptionSelected(
                                    options[8],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        com.project.gains.presentation.components.OptionCheckbox(
                            option = allOptions[9],
                            onOptionSelected = { isChecked ->
                                selectedMetrics.add(com.project.gains.data.TrainingMetricType.DURATION)

                                onOptionSelected(
                                    options[9],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        com.project.gains.presentation.components.OptionCheckbox(
                            option = allOptions[10],
                            onOptionSelected = { isChecked ->
                                selectedMetrics.add(com.project.gains.data.TrainingMetricType.DISTANCE)

                                onOptionSelected(
                                    options[10],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        com.project.gains.presentation.components.PeriodPopup(
                            selectedPeriodMap = com.project.gains.data.PeriodMetricType.entries.toMutableList(),
                            popupVisible = popupVisible,
                            onDismiss = { popupVisible.value = false },
                            onOptionSelected = { period ->
                                selectedPeriod = period
                                popupVisible.value = false
                            },
                            selectedMetric = selectedPeriod
                        )
                    }
                    item {
                        com.project.gains.presentation.components.TrainingTypePopup(
                            popupVisible = popupVisible,
                            onDismiss = { popupVisible.value = false },
                            onOptionSelected = { training ->
                                selectedTraining = training
                                popupVisible.value = false
                            },
                            selectedMetric = selectedTraining
                        )
                    }



                options.forEach{option ->
                    item {
                        OptionCheckbox(
                            option = allOptions[1],
                            onOptionSelected = { isChecked ->
                                selectedBackup.value = true
                                onOptionSelected(
                                    options[1],
                                    isChecked
                                )
                            }
                        )
                    }

                }

                item { Spacer(modifier = Modifier.height(10.dp)) }


                item {
                    Button(
                        onClick = {
                                createHandler(
                                    CreateEvent.CreatePlan(
                                        options,
                                        selectedPeriod,
                                        selectedMetrics,
                                        selectedTraining,
                                        selectedMusic.value,
                                        selectedBackup.value
                                    )
                                )

                                  showPopup.value = false
                                  onItemClick()},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(text = "GENERATE PLAN", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }

            }
        }
    }
}

@Composable
fun ShareContentPagePopup(showPopup: MutableState<Boolean>, apps: MutableList<Int>,  show: MutableState<Boolean>,onItemClick: (Int)->Unit,navController: NavController ){
    var clickedApp by remember { mutableIntStateOf(1) }


    if (showPopup.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(20.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
            ) {
                item {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 290.dp),
                        horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = { showPopup.value=false }) {
                            Icon(imageVector = Icons.Default.Close , contentDescription = "Close Icon")
                        }
                    }  }

                item { Text(
                    text = "Share Your Content!",
                    style = MaterialTheme.typography.headlineMedium
                ) }
                item {
                    Text(
                        text = "Share Your Content with your friends showing your progress, your workouts and your plans",
                        style = MaterialTheme.typography.bodySmall,
                    ) }
                item { Spacer(modifier = Modifier.height(10.dp)) }

                item { Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(8.dp)) {
                    apps.forEach{app ->
                            SocialMediaIcon(icon = app, onClick = {
                                onItemClick(app)
                                clickedApp = app
                            }, clickedApp == app)
                    }
                } }

                item { Spacer(modifier = Modifier.height(20.dp)) }


                item {
                    if (apps.isEmpty()){
                        Text(
                            text = "You have no linked apps to link an app go to settings -> sharing preferences or click the link apps button below",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { showPopup.value = false
                                navController.navigate(Route.SettingScreen.route)},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                        ) {
                            Text(text = "LINK APPS")
                        }

                    }else{
                        Button(
                            onClick = { showPopup.value = false
                                show.value=true},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                        ) {
                            Text(text = "SHARE CONTENT")
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun SetWorkoutPagePopup(showPopup: MutableState<Boolean>, show: MutableState<Boolean>, onItemClick: ()->Unit,onItemClick2: ()->Unit ,selectedExercises:MutableList<Exercise>){
    var workoutTitle by remember { mutableStateOf(TextFieldValue("")) }


    if (showPopup.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(20.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
            ) {

                item {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 290.dp),
                        horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = { showPopup.value=false }) {
                            Icon(imageVector = Icons.Default.Close , contentDescription = "Close Icon")
                        }
                    }  }

                item { Text(
                    text = "Create Your Workout!",
                    style = MaterialTheme.typography.headlineMedium
                ) }
                item {
                    Text(
                        text = "Manually add each exercise to your workout day, press the button + to search for an exercise and then click save button to submit",
                        style = MaterialTheme.typography.bodySmall,
                    ) }
                item { Spacer(modifier = Modifier.height(10.dp)) }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                color = Color.Gray.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp) // Inner padding to prevent text from touching the edges
                    ) {
                        BasicTextField(
                            value = workoutTitle,
                            onValueChange = { workoutTitle = it },
                            decorationBox = { innerTextField ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    if (workoutTitle.text.isEmpty()) {
                                        Text(
                                            text = "Enter workout name...",
                                            color = Color.Gray // Placeholder text color
                                        )
                                    }
                                    innerTextField()
                                }
                            },
                            modifier = Modifier.fillMaxWidth() // Ensure the text field fills the width of the box
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    selectedExercises.forEach {exercise ->

                        Row(horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically) {
                            AddExerciseItem(exercise = exercise, onItemClick = {onItemClick()}, onItemClick2 = {onItemClick2()},isSelected = false,isToAdd = true)
                            Spacer(modifier = Modifier.width(20.dp))
                            DeleteExerciseButton {

                            }

                        }
                    }

                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        TextButton(onClick = { /* Add exercise action */ }) {
                            Text(text = "Add new exercise", color = Color.Cyan)
                        }
                        Spacer(modifier = Modifier.width(80.dp))
                        Spacer(modifier = Modifier.width(20.dp))
                        AddExerciseButton {
                            onItemClick()

                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }

                item {
                    Button(
                        onClick = { showPopup.value = false
                                  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(text = "SAVE PLAN", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }

            }
        }
    }
}


@Composable
fun AddExerciseButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(color = Color.Cyan, shape = CircleShape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "+",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DeleteExerciseButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(color = Color.Red, shape = CircleShape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "-",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LogoUser(modifier: Modifier,res:Int, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .size(70.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(res),
            contentDescription = "User Profile Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(35.dp)),  // Half of the size to make it fully rounded
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun PlanPagePopup(showPopup : MutableState<Boolean>, workouts:MutableList<Workout>,selectHandler: (SelectEvent) -> Unit,createHandler: (CreateEvent) -> Unit,navController: NavController,generalViewModel: GeneralViewModel,showCompleteWorkout:MutableState<Boolean>,showCompletePlan:MutableState<Boolean>,) {

    val selectedExerciseToAdd by generalViewModel.addedExercises.observeAsState()

    val clicked = remember {
        mutableStateOf(false)
    } // CREATE PLAN CLICKED
    var showPopup3 = remember { mutableStateOf(false) } // LAST PLAN GENERATION SCREEN
    var showPopup4 = remember { mutableStateOf(false) } // LAST  WORKOUT SET SCREEN
    var showDialog = remember { mutableStateOf(false) } //
    if (showPopup.value) { // PLAN PAGE POPUP OR WORKOUT SET
                if (clicked.value == false && showPopup3.value == false && showPopup4.value == false) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp)
                            .background(
                                MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(20.dp)
                            )
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(40.dp)
                        ) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 290.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    IconButton(onClick = { clicked.value = true }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close Icon"
                                        )
                                    }
                                }
                            }
                            item {
                                Text(
                                    text = "Add Pre-Made Workout",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                            item {
                                Text(
                                    text = "Choose a pre-made workout from our library or use the workout builder to create your own.",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                            item { Spacer(modifier = Modifier.height(10.dp)) }
                            item {
                                Button(
                                    onClick = {
                                        clicked.value = true
                                        showPopup4.value = false

                                        showPopup3.value = true
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(55.dp),
                                    //         colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)

                                ) {
                                    Text(text = "USE PLAN BUILDER")
                                }
                            }
                            item { Spacer(modifier = Modifier.height(10.dp)) }
                            item {
                                Button(
                                    onClick = {
                                        clicked.value = true
                                        showPopup3.value = false
                                        showPopup4.value = true
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(55.dp),
                                    // colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                                ) {
                                    Text(text = "ADD MANUAL WORKOUT")
                                }
                            }
                            workouts.forEach { workout ->
                                item {
                                    GeneralCard(
                                        imageResId = R.drawable.logo,
                                        title = workout.name,
                                        onItemClick = {
                                            selectHandler(SelectEvent.SelectWorkout(workout))
                                            navController.navigate(Route.WorkoutScreen.route)
                                        }
                                    )
                                }

                            }
                        }
                    }
                } else if (clicked.value == true && showPopup3.value == false && showPopup4.value == false) {
                 //INTERMEDIATE SCREEN
                        NewPlanScreen(createHandler, navController, showPopup3)

                } else if (clicked.value == true && showPopup3.value == true && showPopup4.value == false) {

                        // LAST SCREEN
                        NewPlanPagePopup(
                            showPopup3,
                            {showCompletePlan.value=true},
                            navController,
                            { showPopup3.value = false })

                } else if (clicked.value == true && showPopup3.value == false && showPopup4.value == true) {

                        SetWorkoutPagePopup(
                            showPopup4,
                            showCompleteWorkout,
                            {
                                navController.navigate(Route.TypedExerciseScreen.route)
                            },
                            selectedExercises = selectedExerciseToAdd ?: mutableListOf<Exercise>(),
                            onItemClick2 = {
                                navController.navigate(Route.TypedExerciseScreen.route)
                            })
                    }

                }
            }

    @Composable
    fun ChoicePopup() {
        var showPopup = remember { mutableStateOf(true) }
        if (showPopup.value) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        showPopup.value = false
                    }
                    .background(
                        MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(20.dp)
                    )
            ) {
                // Your main screen content here
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(10.dp)
                        .background(Color.White, shape = RoundedCornerShape(10.dp),)
                ) {
                    Text(
                        text = "Hammer Strength",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Are you sure you want to finish workout?",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { showPopup.value = false },
                        ) {
                            Text(text = "No")
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = { /* on yes click */ },
                        ) {
                            Text(text = "Yes")
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun MuscleGroupItem(imageResId: Int, title: String, onItemClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = title,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.onSurface,
                        RoundedCornerShape(8.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    onItemClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = "Exercise Button",
                        tint = MaterialTheme.colorScheme.surface
                    )

                }
            }
        }
    }


@Composable
fun NotificationCard(
    message: String,
    onClose: () -> Unit
) {
    androidx.compose.material.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                androidx.compose.material.MaterialTheme.colors.secondary,
                RoundedCornerShape(16.dp)
            ),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = androidx.compose.material.MaterialTheme.colors.secondary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            androidx.compose.material.Text(
                text = message,
                style = androidx.compose.material.MaterialTheme.typography.body2,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            androidx.compose.material.IconButton(onClick = onClose) {
                androidx.compose.material.Icon(
                    Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = Color.Black
                )
            }
        }
    }
}


@Composable
fun FeedPost(gymPost: GymPost) {
    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Header Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            // User Info Section
            LogoUser(modifier = Modifier.size(60.dp), gymPost.userResourceId) {
                // Placeholder for user logo
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                androidx.compose.material.Text(
                    text = gymPost.username,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                androidx.compose.material.Text(
                    text = gymPost.time,
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.width(150.dp))
            Image(
                painter = painterResource(id = gymPost.randomSocialId),
                contentDescription = "Social Icon",
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

        }
        Spacer(modifier = Modifier.height(8.dp))

        // Image Section
        Image(
            painter = painterResource(id = gymPost.imageResourceId),
            contentDescription = "Post Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Text Content Section
        androidx.compose.material.Text(
            text = gymPost.caption,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Separator

        // Interaction Section (Likes and Comments)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            // Likes Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material.Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                androidx.compose.material.Text(
                    text = gymPost.likes,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Comments Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material.Icon(
                    imageVector = Icons.Default.Comment,
                    contentDescription = "Comment Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                androidx.compose.material.Text(
                    text = gymPost.comment,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())

}


@Composable
fun WorkoutHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.pexels1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material.Text(
            text = "GENERAL MUSCLE BUILDING",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        androidx.compose.material.Text(
            text = "For gym • Expert",
            fontSize = 16.sp,

            )
        androidx.compose.material.Text(
            text = "32 workout days (3 sessions per week)",
            fontSize = 14.sp,
        )
        androidx.compose.material.Text(
            text = "Workouts done: 0",
            fontSize = 14.sp,
        )
    }
}

@Composable
fun WorkoutDaysList(onItemClick: () -> Unit) {
    val workoutDays = listOf(
        "chest",
        "back",
        "legs",
        "arms+shoulders"
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        androidx.compose.material.Text(
            text = "Workout days",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        workoutDays.forEachIndexed { index, day ->
            androidx.compose.material.Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clickable { onItemClick() },
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material.Icon(
                        imageVector = if (index == 0) Icons.Default.Circle else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (index == 0) Color.Red else Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        androidx.compose.material.Text(
                            text = "${index + 1} workout day",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        androidx.compose.material.Text(
                            text = day,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GifLoader(gif: Int) {

    val context = LocalContext.current


    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(GifDecoder.Factory()) // Use built-in decoder for Android P+
        }
        .build()
    val painter = // Adjust the size as needed
        rememberAsyncImagePainter(
            ImageRequest // Optional: Apply transformations
                .Builder(LocalContext.current).data(data = gif)
                .apply(block = fun ImageRequest.Builder.() {
                    size(Size.ORIGINAL) // Adjust the size as needed
                    transformations(CircleCropTransformation()) // Optional: Apply transformations
                }).build(), imageLoader = imageLoader
        )

    Image(
        painter = painter,
        contentDescription = null, // Provide content description if necessary
        modifier = Modifier.size(200.dp) // Example: Set image size
    )
}

@Composable
fun InstructionCard(text: String) {
    androidx.compose.material.Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            androidx.compose.material.Text(
                text = text,
                style = androidx.compose.material.MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun WarningCard(message: String) {
    androidx.compose.material.Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.errorContainer,
                RoundedCornerShape(16.dp)
            ),
        backgroundColor = MaterialTheme.colorScheme.errorContainer
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.WarningAmber,
                contentDescription = "Warning",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ExerciseGif(exercise: Exercise, onClick: (Exercise) -> Unit) {
    val context = LocalContext.current
    val gifResourceId = R.drawable.gi

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(gifResourceId)
            .size(Size.ORIGINAL)
            .parameters(
                coil.request.Parameters.Builder()
                    .set("coil-request-animated", true)
                    .build()
            )
            .build(),
        contentDescription = "Exercise GIF",
        modifier = Modifier
            .size(200.dp)
            .clickable { onClick(exercise) }
            .graphicsLayer {
                // Add any additional transformations or animations if needed
            },
    )
}




@Composable
fun FeedbackAlertDialog(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String,
    dismissButtonText: String
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(
                    text = confirmButtonText,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = dismissButtonText,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        shape = RoundedCornerShape(20.dp),
    )
}

@Composable
fun SettingItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        androidx.compose.material.Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}



// PROGRESS SCREEN
@Composable
fun MetricPopup(
    selectedMetricMap: MutableList<TrainingMetricType>?,
    popupVisible: MutableState<Boolean>,
    onDismiss: () -> Unit,
    onOptionSelected: (TrainingMetricType) -> Unit,
    selectedMetric: TrainingMetricType
) {
    if (popupVisible.value) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {

            },
            text = {
                Spacer(modifier = Modifier.height(10.dp))
                Column(Modifier.padding(top = 20.dp)) {
                    selectedMetricMap?.forEach { metric ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (metric == selectedMetric) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onOptionSelected(metric) }
                        ) {
                            Text(
                                text = metric.name,
                                color = if (metric == selectedMetric) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            },
            buttons = {
                Column {
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(5.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { popupVisible.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", color = Color.Red)
                        }
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(50.dp)
                                .background(Color.Gray)
                        )
                        TextButton(
                            onClick = { popupVisible.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Confirm", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp) // Optional padding to reduce width
        )
    }
}


