package com.project.gains.presentation.components

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AlertDialog
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries,
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.GymPost
import com.project.gains.data.MuscleGroup
import com.project.gains.data.Option
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.Plan
import com.project.gains.data.Plot
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.Session
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.TrainingType
import com.project.gains.data.UserProfileBundle
import com.project.gains.data.Workout
import com.project.gains.data.bottomNavItems
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.MusicEvent
import com.project.gains.presentation.events.SaveSessionEvent
import com.project.gains.presentation.events.ShareContentEvent
import com.project.gains.presentation.navgraph.Route
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

//  Back Handler
@Composable
fun CustomBackHandler(
    onBackPressedDispatcher: OnBackPressedDispatcher,
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    androidx.activity.compose.LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.addCallback(backCallback)

    DisposableEffect(onBackPressedDispatcher) {
        backCallback.isEnabled = enabled
        onDispose {
            backCallback.remove()
        }
    }
}
// Buttons
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
fun SharingPreferencesButton(navController: NavController) {
    IconButton(
        onClick = { navController.navigate(Route.SettingScreen.route) },
        modifier = Modifier.size(50.dp),
        colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.tertiary)
    ) {
        Icon(
            imageVector = Icons.Outlined.Settings,
            contentDescription = "Share Icon",
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp),
            tint = MaterialTheme.colorScheme.onTertiary
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
        modifier=Modifier.size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Share Icon",
            modifier = Modifier.size(60.dp).padding(10.dp),
            tint = if (isEnabled) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onBackground
        )
    }
}
// popup

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
        Exercise("Exercise 1", R.drawable.gi, "Description 1", ExerciseType.CARDIO, TrainingType.RUNNING, MuscleGroup.BACK),
        Exercise("Exercise 2", R.drawable.gi, "Description 2", ExerciseType.BALANCE, TrainingType.STRENGTH, MuscleGroup.ARMS)
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
// dialogs
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
// bars
@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = currentRoute(navController)

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.height(64.dp) // Adjust the height of the BottomNavigation
    ) {
        bottomNavItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(6.dp)) // Add spacing between icon and text
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(32.dp) // Adjust the size of the icon
                        )
                        Spacer(modifier = Modifier.height(6.dp)) // Add spacing between icon and text
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp, // Set the desired text size here
                        maxLines = 1 // Limit to one line to prevent overflow
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                    }
                }
            )
        }
    }
}
@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
@Composable
fun TopBar(navController: NavController, userProfile: UserProfileBundle?, message: String) {
    LogoUserImage(name = userProfile?.displayName ?: "", modifier = Modifier.size(50.dp)
    ) { navController.navigate(Route.SettingsScreen.route) }
    Text(
        text = message,
        style = MaterialTheme.typography.displayLarge.copy( // Using a smaller typography style
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.Black,
                offset = Offset(4f, 4f),
                blurRadius = 8f
            )
        ),
        color = MaterialTheme.colorScheme.onPrimary,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 6.dp) // Adjust padding as needed
    )
    SharingPreferencesButton(navController = navController)
}
// cards
@Composable
fun UserContentCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer, // Orange
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Text color
        )    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
@Composable
fun ExerciseCard(exercise: Exercise){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            // Display GIF of the exercise
            item {
                Image(
                painter = painterResource(id = exercise.gifResId ?: R.drawable.gi),
                contentDescription = "Exercise image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {   // Display exercise name
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) }
            item {
                // Display description of the exercise execution
                Text(
                    text = exercise.description,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) }
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
// items
@Composable
fun GymPostItem(post: GymPost, shareHandler: (ShareContentEvent) -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp) // Add padding around the post
            .aspectRatio(1f) // Set aspect ratio to maintain square shape
    ) {
        // Horizontal separator around the post
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)) // Rounded corners for the separator
                .background(Color.White) // Background color of the separator
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { shareHandler(ShareContentEvent.ShareLink(post)) },
                shape = RectangleShape,
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary)
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                ) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = post.randomSocialId),
                            contentDescription = "Exercise GIF",
                            modifier = Modifier
                                .size(15.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Image(
                        painter = painterResource(id = post.imageResourceId),
                        contentDescription = "Exercise GIF",
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = post.username,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Just finished an intense workout session! 💪",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
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
fun AddExerciseItem(exercise: Exercise, onAdd: (Exercise) -> Unit,isSelected: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = { onAdd(exercise) },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primaryContainer, // Orange
            contentColor = if(isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer // Text color
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            androidx.compose.material.Text(
                text = exercise.name,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(1f)
            )
            androidx.compose.material.IconButton(
                onClick = { onAdd(exercise) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                androidx.compose.material.Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Icon"
                )
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
fun AnimatedSessionDetails(plan:Int,workout:Int,
    submit: Boolean,
    saveSessionHandler: (SaveSessionEvent.SaveSession) -> Unit
) {
    val animatedCalories = remember { Animatable(0f) }
    val animatedBpm = remember { Animatable(0f) }
    val animatedRestTime = remember { Animatable(0f) }
    val animatedDuration = remember { Animatable(0f) }
    val animatedIntensity = remember { Animatable(0f) }
    val animatedDistance = remember { Animatable(0f) }
    val progress = remember{ Animatable(0F) }

    val duration = 20000 // 20 seconds for the sake of example
    val targetCalories = 100f
    val targetBpm = 100f
    val targetRestTime = 100f
    val targetTimeOfTrying = 100f
    val targetTime = 100f

    LaunchedEffect(Unit) {
        launch { animatedCalories.animateTo(targetValue = targetCalories, animationSpec = tween(durationMillis = duration)) }
        launch { animatedBpm.animateTo(targetValue = targetBpm, animationSpec = tween(durationMillis = duration)) }
        launch { animatedRestTime.animateTo(targetValue = targetRestTime, animationSpec = tween(durationMillis = duration)) }
        launch { animatedDuration.animateTo(targetValue = targetTimeOfTrying, animationSpec = tween(durationMillis = duration)) }
        launch { animatedIntensity.animateTo(targetValue = targetTimeOfTrying, animationSpec = tween(durationMillis = duration)) }
        launch { animatedDistance.animateTo(targetValue = targetTimeOfTrying, animationSpec = tween(durationMillis = duration)) }
        launch { progress.animateTo(targetValue = targetTime, animationSpec = tween(durationMillis = duration)) }

    }

    SessionDetails(
        calories = "${animatedCalories.value.toInt()} kcal",
        bpm = "${animatedBpm.value.toInt()} bpm",
        restTime = "${animatedRestTime.value.toInt()} min",
        intensity = "${animatedIntensity.value.toInt()} min",
        distance = "${animatedDistance.value.toInt()} min",
        duration = "${animatedDuration.value.toInt()} min",
        time="${progress.value.toInt()} min",
    )
    if (submit) {
        saveSessionHandler(
            SaveSessionEvent.SaveSession(plan,workout,
                Session(
                    animatedCalories.value.toInt(),
                    animatedBpm.value.toInt(),
                    animatedRestTime.value.toInt(),
                    animatedDuration.value.toInt(),
                    animatedDistance.value.toInt(),
                    animatedIntensity.value.toInt()
                )
            )
        )

    }

}
@Composable
fun SessionDetails(
    calories: String,
    bpm: String,
    restTime: String,
    duration: String,
    intensity: String,
    distance: String,
    time: String
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Adjust the number of columns as needed
        contentPadding = PaddingValues(10.dp) // Add padding around the grid
    ) {
        item{
            MetricRow(icon = Icons.Default.LocalFireDepartment, label = "Calories", value = calories)

        }
        item{
            MetricRow(icon = Icons.Default.Favorite, label = "BPM", value = bpm)

        }
        item{
            MetricRow(icon = Icons.Default.Timer, label = "Rest Time", value = restTime)

        }
        item{
            MetricRow(icon = Icons.Default.Timelapse, label = "Until Rest Time", value = restTime)

        }
        item{
            MetricRow(icon = Icons.Default.AccessTime, label = "Training time", value = duration)

        }
        item{
            MetricRow(icon = Icons.Default.Speed, label = "Intensity", value = intensity)

        }
        item{
            MetricRow(icon = Icons.AutoMirrored.Filled.DirectionsRun, label = "Distance m", value = distance)

        }
        item{
            MetricRow(icon = Icons.Default.Timelapse, label = "Time", value = time)

        }
    }
}
@Composable
fun MetricRow(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .size(50.dp)
    ) {
        Icon(icon, contentDescription = label, modifier = Modifier.size(24.dp))
        Text(
            text = "$label: $value",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
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
// list
@Composable
fun GymPostsList(posts: MutableList<GymPost>?, shareHandler: (ShareContentEvent) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Adjust the number of columns as needed
        contentPadding = PaddingValues(8.dp) // Add padding around the grid
    ) {
        items(posts ?: emptyList()) { post ->

            GymPostItem(post = post,shareHandler)
        }
    }
}
@Composable
fun ProgressChartList(
    plots: List<Plot>,
    onItemClick: (ProgressChartPreview) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(plots) { plot ->
            ProgressChartCard(plot.preview, onItemClick)
            Spacer(modifier = Modifier.height(16.dp)) // Add spacing between cards
        }
    }
}
@Composable
fun TrainingOverviewChart(
    trainingData: List<TrainingData>, selectedMetric: TrainingMetricType, selectedPeriod: PeriodMetricType,
    shareHandler:(ShareContentEvent.SharePlot)->Unit, selectedPlotType: ProgressChartPreview) {
    val filteredData = trainingData.filter { it.type == selectedMetric }

    val rowColors = listOf(Color.Red, Color.Blue, Color.Green) // Example colors, you can customize as needed
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        filteredData.forEachIndexed { index, data ->
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
            Button(
                onClick = {
                    clickedApps.value = clickedApps.value.toMutableList().apply { add(icon) }
                    linkHandler(LinkAppEvent.LinkApp(icon)) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                androidx.compose.material.Icon(Icons.Default.Add, contentDescription = "Add Icon")
                androidx.compose.material.Text("Link")
            }
        }
    }
}








