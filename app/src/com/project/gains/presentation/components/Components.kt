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
import androidx.compose.foundation.layout.widthIn

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.GymPost
import com.project.gains.data.Option
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.Plan
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.Session
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout
import com.project.gains.data.bottomNavItems
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.MusicEvent
import com.project.gains.presentation.events.SaveSharingPreferencesEvent
import com.project.gains.presentation.events.ShareContentEvent
import com.project.gains.presentation.navgraph.Route
import kotlin.system.exitProcess

// MERGE
@Composable
fun ShareButton(
    onClick: () -> Unit,
    isEnabled: Boolean
) {
    IconButton(
        onClick = { onClick() },
        modifier=Modifier.size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Share Icon",
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp),
            tint = if (isEnabled) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onBackground
        )
    }
}
@Composable
fun MusicPopup(popup: Boolean, musicHandler: (MusicEvent) -> Unit,currentSong:String) {
    if (popup) {
        val play = remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(min = 300.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(16.dp),
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
                        .padding(top = 16.dp)
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
                            exitProcess(0)
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
                            exitProcess(0)
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
                            exitProcess(0)
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
fun SharePopup(popup: MutableState<Boolean>, icon: Int,shareHandler: (ShareContentEvent) -> Unit) {
    if (popup.value) {
        val play = remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(min = 300.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(64.dp)
                        .padding(top = 16.dp)
                )

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    ElevatedButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(bottom = 16.dp),
                        onClick = {
                            shareHandler(ShareContentEvent.ShareSession(Session(2, 2, 2, 2,2,2)))
                            exitProcess(0)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PostAdd,
                            contentDescription ="post",
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
                            shareHandler(ShareContentEvent.ShareSession(Session(2, 2, 2, 2,2,1)))

                            play.value = true
                            exitProcess(0)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector =Icons.Default.AddCircleOutline ,
                            contentDescription ="story",
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
fun BackupPopup(popup: MutableState<Boolean>,shareHandler: (ShareContentEvent) -> Unit) {
    if (popup.value) {
        val play = remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(min = 300.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.drive_google_icon),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(64.dp)
                        .padding(top = 16.dp)
                )

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    ElevatedButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(bottom = 16.dp),
                        onClick = {
                            shareHandler(ShareContentEvent.ShareSession(Session(2, 2, 2, 2,1,1)))
                            exitProcess(0)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Backup,
                            contentDescription ="export",
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
                            shareHandler(ShareContentEvent.ShareSession(Session(2, 2, 2, 2,1,1)))

                            play.value = true
                            exitProcess(0)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download ,
                            contentDescription ="import",
                            modifier = Modifier.size(20.dp), // Adjust the size of the icon
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                    }
                }
            }
        }
    }
}


// PROGRESS SCREEN
@Composable
fun MetricPopup(
    selectedMetricMap: MutableList<TrainingMetricType>?,
    popupVisible: Boolean,
    onDismiss: () -> Unit,
    onOptionSelected: (TrainingMetricType) -> Unit,
    selectedMetric: TrainingMetricType
) {
    if (popupVisible) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = false
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    selectedMetricMap?.forEach { metric ->
                        androidx.compose.material.Text(
                            modifier = Modifier
                                .clickable { onOptionSelected(metric) },
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = if (metric == selectedMetric) Color.Blue else Color.Black,
                            text = metric.name
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun TrainingTypePopup(
    popupVisible: Boolean,
    onDismiss: () -> Unit,
    onOptionSelected: (TrainingType) -> Unit,
    selectedMetric: TrainingType
) {
    if (popupVisible) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = false
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TrainingType.entries.forEach { metric ->
                        androidx.compose.material.Text(
                            modifier = Modifier
                                .clickable { onOptionSelected(metric) },
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = if (metric == selectedMetric) Color.Blue else Color.Black,
                            text = metric.name
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun PeriodPopup(
    selectedPeriodMap: MutableList<PeriodMetricType>?,
    popupVisible: Boolean,
    onDismiss: () -> Unit,
    onOptionSelected: (PeriodMetricType) -> Unit,
    selectedMetric: PeriodMetricType
) {
    if (popupVisible) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = false
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    selectedPeriodMap?.forEach { metric ->
                        androidx.compose.material.Text(
                            modifier = Modifier
                                .clickable { onOptionSelected(metric) },
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = if (metric == selectedMetric) Color.Blue else Color.Black,
                            text = metric.name
                        )
                    }
                }
            }
        }
    }
}


// SHARE SCREEN

@Composable
fun DialogOption(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Composable
fun SelectContentPopup(
    onItemClickPlan: (Plan) -> Unit,
    onItemClickWorkout: (Workout) -> Unit,
    onItemClickExercise: (Exercise) -> Unit,
    showDialog: MutableState<Boolean>
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val exercises = listOf(
        Exercise(
            "Exercise 1",
            R.drawable.gi,
            "Description 1",
            ExerciseType.ARMS,
            TrainingType.RUNNING
        ),
        Exercise(
            "Exercise 2",
            R.drawable.gi,
            "Description 2",
            ExerciseType.BACK,
            TrainingType.STRENGTH
        )
    )

    val workouts = listOf(
        Workout(1, "Workout 1", exercises.toMutableList()),
        Workout(2, "Workout 2", exercises.toMutableList())
    )

    val plans = listOf(
        Plan(1, "Plan 1", PeriodMetricType.WEEK, workouts.toMutableList()),
        Plan(2, "Plan 2", PeriodMetricType.MONTH, workouts.toMutableList())
    )
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        shape = RoundedCornerShape(20.dp),
        confirmButton =
        {
            Button(
                onClick = { showDialog.value = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.padding(5.dp)
            ) {
                Text(
                    text = "Confirm",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        title= {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Choose an option",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                DialogOption(text = "Show Plans") {
                    selectedOption = "Plan"

                }
                DialogOption(text = "Show Workouts") {
                    selectedOption = "Workout"

                }
                DialogOption(text = "Show Exercises") {
                    selectedOption = "Exercise"

                }
            }
        },
    )

    selectedOption?.let { option ->
        when (option) {
            "Plan" -> PlanDialog(items = plans, onDismiss = { selectedOption = null }, onItemClickPlan)
            "Workout" -> WorkoutDialog(items = workouts, onDismiss = { selectedOption = null }, onItemClickWorkout)
            "Exercise" -> ExerciseDialog(items = exercises, onDismiss = { selectedOption = null }, onItemClickExercise)
        }
    }
}


@Composable
fun PlanDialog(items: List<Plan>, onDismiss: () -> Unit,onItemClick: (Plan) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Choose a plan",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                LazyColumn {
                    items(items) { plan ->
                        SelectPlanItem(
                            plan = plan,
                            onCloseDialog = onDismiss, onItemClick
                        )
                    }
                }
            }
            },
        confirmButton = {
        },
        dismissButton = {  Button(
            onClick = { onDismiss() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = "Close",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }},
        shape = RoundedCornerShape(20.dp)
    )
}
@Composable
fun WorkoutDialog(items: List<Workout>, onDismiss: () -> Unit,onItemClick: (Workout) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(
                    text = "Choose a plan",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                LazyColumn {
                    items(items) { workout ->
                        SelectWorkoutItem(
                            workout = workout,
                            onCloseDialog = onDismiss, onItemClick = onItemClick
                        )
                    }
                }
            }
        },
        confirmButton = {
        },
        dismissButton = {  Button(
            onClick = { onDismiss() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = "Close",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }},
        shape = RoundedCornerShape(20.dp)
    )
}
@Composable
fun ExerciseDialog(items: List<Exercise>, onDismiss: () -> Unit,onItemClick: (Exercise) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Choose a plan",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                LazyColumn {
                    items(items) { exercise ->
                        SelectExerciseItem(
                            exercise = exercise,
                            onCloseDialog = onDismiss, onItemClick
                        )
                    }
                }
            }
        },
        confirmButton = {
        },
        dismissButton = {  Button(
            onClick = { onDismiss() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = "Close",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }},
        shape = RoundedCornerShape(20.dp)
    )
}
@Composable
fun ExerciseItem(exercise: Exercise, onDelete: (Exercise) -> Unit,onItemClick: (Exercise) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = {
                onItemClick(exercise)
            }),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Text(
                text = exercise.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            androidx.compose.material.IconButton(
                onClick = {onItemClick(exercise)},
                modifier = Modifier.padding(start = 8.dp)
            ) {
                androidx.compose.material.Icon(Icons.Default.Share, contentDescription = "Edit Icon")
            }
            androidx.compose.material.IconButton(
                onClick = {onDelete(exercise)},
                modifier = Modifier.padding(start = 8.dp)
            ) {
                androidx.compose.material.Icon(Icons.Default.Delete, contentDescription = "Delete Icon")
            }
        }
    }
        }
@Composable
fun PlanItem(plan: Plan, onDelete: (Plan) -> Unit, onItemClick: (Plan) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = {
                onItemClick(plan)
            }),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Text(
                text = plan.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            androidx.compose.material.IconButton(
                onClick = {onItemClick(plan)},
                modifier = Modifier.padding(start = 8.dp)
            ) {
                androidx.compose.material.Icon(Icons.Default.Share, contentDescription = "Edit Icon")
            }
            androidx.compose.material.IconButton(
                onClick = {onDelete(plan)},
                modifier = Modifier.padding(start = 8.dp)
            ) {
                androidx.compose.material.Icon(Icons.Default.Delete, contentDescription = "Delete Icon")
            }
        }
    }
}
@Composable
fun WorkoutItem(workout: Workout, onItemClick: (Workout) -> Unit, onDelete: (Workout) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = {
                onItemClick(workout)
            }),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Text(
                text = workout.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            androidx.compose.material.IconButton(
                onClick = {onItemClick(workout)},
                modifier = Modifier.padding(start = 8.dp)
            ) {
                androidx.compose.material.Icon(Icons.Default.Share, contentDescription = "Edit Icon")
            }
            androidx.compose.material.IconButton(
                onClick = {onDelete(workout)},
                modifier = Modifier.padding(start = 8.dp)
            ) {
                androidx.compose.material.Icon(Icons.Default.Delete, contentDescription = "Delete Icon")
            }
        }
    }
}
@Composable
fun SelectWorkoutItem(workout: Workout, onItemClick: (Workout) -> Unit,onCloseDialog: () -> Unit) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = {
                onItemClick(workout)
                onCloseDialog()
            }),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Text(
                text = workout.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            androidx.compose.material.IconButton(
                onClick = {onItemClick(workout)
                    onCloseDialog()},
                modifier = Modifier.padding(start = 8.dp)
            ) {
                androidx.compose.material.Icon(Icons.Default.Share, contentDescription = "Edit Icon")
            }
        }
    }
}
@Composable
fun SelectExerciseItem(exercise: Exercise, onCloseDialog: () -> Unit, onItemClick: (Exercise) -> Unit ) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = {
                onItemClick(exercise)
                onCloseDialog()
            }),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Text(
                text = exercise.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            androidx.compose.material.IconButton(
                onClick = {onItemClick(exercise)
                    onCloseDialog()},
                modifier = Modifier.padding(start = 8.dp)
            ) {
                androidx.compose.material.Icon(Icons.Default.Share, contentDescription = "Edit Icon")
            }
        }
    }
}
@Composable
fun SelectPlanItem(plan: Plan,onCloseDialog: () -> Unit,onItemClick: (Plan) -> Unit ) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = {
                onItemClick(plan)
                onCloseDialog()
            }),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Text(
                text = plan.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            androidx.compose.material.IconButton(
                onClick = {onItemClick(plan)
                    onCloseDialog()},
                modifier = Modifier.padding(start = 8.dp)
            ) {
                androidx.compose.material.Icon(Icons.Default.Share, contentDescription = "Edit Icon")
            }
        }
    }
    }


//// CHECKED


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

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.Text(
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
            modifier = Modifier.padding(end = 8.dp)
        )
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
fun TopBar(navController: NavController, message: String) {
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
            SharingPreferencesButton(navController = navController)

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

            LogoUser(
                modifier = Modifier.size(60.dp),R.drawable.pexels5
            ) { navController.navigate(Route.SettingsScreen.route) }
        }
    }
}

@Composable
fun TopBar2(navController: NavController, message: String) {
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

        }
    }
}

@Composable
fun NewPlanPagePopup(showPopup : MutableState<Boolean>,  workouts: MutableList<Workout>,onItemClick: () -> Unit) {
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
fun SharingPreferencesButton(navController: NavController) {
    IconButton(
        onClick = { navController.navigate(Route.SettingScreen.route) },
        modifier = Modifier.size(50.dp),
        colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.onSurface)
    ) {
        Icon(
            imageVector = Icons.Outlined.Settings,
            contentDescription = "Share Icon",
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp),
            tint = MaterialTheme.colorScheme.surface
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
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
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
        modifier = Modifier.size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Share Icon",
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
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
                .height(150.dp).clip(RoundedCornerShape(16.dp)),
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