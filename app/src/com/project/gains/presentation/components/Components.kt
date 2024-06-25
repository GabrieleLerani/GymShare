package com.project.gains.presentation.components

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.GymPost
import com.project.gains.data.Option
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout
import com.project.gains.data.bottomNavItems
import com.project.gains.data.generateOptions
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.MusicEvent
import com.project.gains.presentation.events.ShareContentEvent

@Composable
fun MusicPopup(popup: Boolean, musicHandler: (MusicEvent) -> Unit,currentSong:String) {
    if (popup) {
        val play = remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .padding(16.dp)
                .width(500.dp)
                .height(150.dp)
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
            ) {
                Image(
                    painter = painterResource(id = R.drawable.spotify_icon),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(64.dp)
                        .padding(16.dp)
                )
                Text(text = currentSong, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    ElevatedButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(bottom = 16.dp),
                        onClick = {
                            musicHandler(MusicEvent.Music)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastRewind,
                            contentDescription ="rewind",
                            modifier = Modifier.size(20.dp), // Adjust the size of the icon
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    ElevatedButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(bottom = 16.dp),
                        onClick = {
                            play.value = true
                            musicHandler(MusicEvent.Music)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = if(play.value) Icons.Default.Stop else Icons.Default.PlayArrow ,
                            contentDescription ="play",
                            modifier = Modifier.size(20.dp), // Adjust the size of the icon
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    ElevatedButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(bottom = 16.dp),
                        onClick = {
                            musicHandler(MusicEvent.Music)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastForward,
                            contentDescription ="forward",
                            modifier = Modifier.size(20.dp), // Adjust the size of the icon
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
    trainingData: List<TrainingData>, selectedMetric: TrainingMetricType, selectedPeriod: PeriodMetricType,
    shareHandler:(ShareContentEvent.SharePlot)->Unit, selectedPlotType: ProgressChartPreview) {

    val rowColors = listOf(Color.Red, Color.Blue, Color.Green) // Example colors, you can customize as needed
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        trainingData.forEachIndexed { index, data ->
            val color = rowColors[index % rowColors.size] // Selecting color based on index
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material.Text(
                    text = "$selectedPeriod ${index + 1}", style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                if (selectedPlotType.imageResId == R.drawable.plo1) {
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width((data.value * 20).dp)
                            .background(
                                color = color,
                                shape = RoundedCornerShape(10.dp)
                            )
                    )

                }
                else if (selectedPlotType.imageResId == R.drawable.plot2) {
                    Box(
                        modifier = Modifier
                            .height((data.value * 20).dp)
                            .width(20.dp)
                            .background(
                                color = color,
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                }
                else{
                    Box(
                        modifier = Modifier
                            .size((data.value * 20).dp)
                            .background(color = color, shape = CircleShape)
                    )
                }
            }
        }
        Row ( modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            ShareButton(onClick = { shareHandler(ShareContentEvent.SharePlot(trainingData,selectedMetric,selectedPeriod))}, isEnabled = true)

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
fun TopBar(navController: NavController, message: String, button: @Composable () -> Unit) {
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
            BackButton {
                navController.popBackStack()
            }

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
                                    com.project.gains.presentation.events.CreateEvent.CreatePlan(
                                        options,
                                        selectedPeriod,
                                        selectedExerciseTypes,
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
fun ShareContentPagePopup(showPopup: MutableState<Boolean>, apps: MutableList<Int>,  show: MutableState<Boolean>,onItemClick: (Int)->Unit ){
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

@Composable
fun SetWorkoutPagePopup(showPopup: MutableState<Boolean>, show: MutableState<Boolean>, onItemClick: (Int)->Unit ){
    var workoutTitle by remember { mutableStateOf(TextFieldValue("")) }
    var selectedExercises by remember { mutableStateOf(listOf<Exercise>()) }


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
                            AddExerciseItem(exercise = exercise, onItemClick = {}, isSelected = true)
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

                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }

                item {
                    Button(
                        onClick = { showPopup.value = false
                            show.value=true},
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
fun PlanPagePopup(showPopup : MutableState<Boolean>, workouts:MutableList<Workout>, onItemClick: () -> Unit) {
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
                        text = "Add Pre-Made Workout",
                        style = MaterialTheme.typography.headlineMedium
                    ) }
                    item {
                        Text(
                        text = "Choose a pre-made workout from our library or use the workout builder to create your own.",
                        style = MaterialTheme.typography.bodySmall,
                    ) }
                    item { Spacer(modifier = Modifier.height(10.dp)) }

                    item {
                        Button(
                            onClick = { showPopup.value = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                   //         colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)

                        ) {
                            Text(text = "USE WORKOUT BUILDER")
                        }
                    }

                    item { Spacer(modifier = Modifier.height(10.dp)) }


                    item {
                        Button(
                            onClick = { showPopup.value=false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                           // colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                        ) {
                            Text(text = "ADD PRE-MADE WORKOUT")
                        }
                    }

                    workouts.forEach{workout ->
                        item {
                            GeneralCard(imageResId = R.drawable.logo, title = "Workout1", onItemClick = onItemClick)
                        }

                    }

                }
            }
        }
    }


@Composable
fun ChoicePopup(){
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
                Text(text = "Hammer Strength", style = MaterialTheme.typography.headlineSmall)
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
fun AddExerciseItem(exercise: Exercise, onItemClick: (Exercise) -> Unit,isSelected: Boolean) {
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
                onItemClick(exercise) }) {
                Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "Exercise Button", tint = MaterialTheme.colorScheme.surface)

            }
        }
    }
}

@Composable
fun MuscleGroupItem(imageResId: Int, title: String,onItemClick: () -> Unit) {
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
        Row( modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        )  {
            IconButton(onClick = {
                onItemClick() }) {
                Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "Exercise Button", tint = MaterialTheme.colorScheme.surface)

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
            LogoUser(modifier = Modifier.size(60.dp),gymPost.userResourceId) {
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
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

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
fun WorkoutHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter= painterResource(id = R.drawable.pexels1),
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
fun WorkoutDaysList(onItemClick:()->Unit) {
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
        rememberAsyncImagePainter(ImageRequest // Optional: Apply transformations
            .Builder(LocalContext.current).data(data = gif).apply(block = fun ImageRequest.Builder.() {
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
fun ExerciseGif(exercise: Exercise,onClick: (Exercise) -> Unit) {
    val context = LocalContext.current
    val gifResourceId = R.drawable.gi

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(gifResourceId)
            .size(Size.ORIGINAL)
            .parameters(coil.request.Parameters.Builder()
                .set("coil-request-animated", true)
                .build())
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: MutableState<String>,
    allExercises: List<Exercise>?,
    searchedExercises: MutableList<Exercise>?
) {
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.DarkGray,
            focusedTextColor = MaterialTheme.colorScheme.surface,
            cursorColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor =  MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor =  MaterialTheme.colorScheme.surface,
            focusedLabelColor = MaterialTheme.colorScheme.surface, // Focused label color
            unfocusedLabelColor = MaterialTheme.colorScheme.surface // Unfocused label color
        ),
        value = searchQuery.value,
        onValueChange = { query ->
            searchQuery.value = query
            searchedExercises?.clear()
            allExercises?.filter {
                it.name.contains(query, ignoreCase = true)
            }?.let { searchedExercises?.addAll(it) }
        },
        label = { Text("Search Exercise") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 16.dp)
            .clip(RoundedCornerShape(20.dp)),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = MaterialTheme.colorScheme.surface)
        }
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

@Composable
fun ShareButton(
    onClick: () -> Unit,
    isEnabled: Boolean
) {
    IconButton(
        onClick = { onClick() },
        modifier=Modifier.size(50.dp),
        colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Icon(
            imageVector = Icons.Default.Send,
            contentDescription = "Share Icon",
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp),
            tint = if (isEnabled) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onBackground
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
                Column(Modifier.padding(top=20.dp)) {
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