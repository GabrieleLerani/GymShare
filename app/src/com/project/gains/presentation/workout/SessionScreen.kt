package com.project.gains.presentation.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.data.Song
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.MusicPopup
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.ShareContentPagePopup
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.WarningCard
import com.project.gains.presentation.events.MusicEvent
import com.project.gains.presentation.events.SelectEvent

import com.project.gains.theme.GainsAppTheme
import kotlinx.coroutines.delay


@Composable
fun SessionScreen(navController:NavController,musicHandler:(MusicEvent)->Unit,generalViewModel:GeneralViewModel,selectHandler:(SelectEvent)->Unit) {
    // State for the timer

    val linkedApps by generalViewModel.linkedApps.observeAsState()
    var showPopup2 = remember { mutableStateOf(false) }
    val showDialogShared by generalViewModel.showDialogShared.observeAsState()
    var showDialog = remember { mutableStateOf(false) }

    var notification = remember {
        mutableStateOf(false)
    }


    val currentSong by generalViewModel.currentSong.observeAsState()
    val workout by generalViewModel.selectedWorkout.observeAsState()
    val show = remember { mutableStateOf(true) }

    var currentExerciseIndex by remember { mutableStateOf(0) }
    var timerState by remember { mutableStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var setsDone by remember { mutableStateOf(0) }
    var restCountdown by remember { mutableStateOf(60) }
    val rest = remember { mutableStateOf(false) }

    // Get the current exercise total time (dummy value 90 seconds for this example)
    val currentExerciseTime = workout?.exercises?.get(currentExerciseIndex)?.totalTime ?: 90
    val totalSets = workout?.exercises?.get(currentExerciseIndex)?.sets ?: 4



    // Function to start the timer
    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            while (isTimerRunning) {
                delay(1000L) // Update timer every second
                if (rest.value) {
                    if (restCountdown > 0) {
                        restCountdown--
                    } else {
                        rest.value = false
                        setsDone++
                        if (setsDone >= totalSets) {
                            if (currentExerciseIndex < workout?.exercises?.size!! - 1) {
                                currentExerciseIndex++
                            } else {
                                currentExerciseIndex = 0
                            }
                            setsDone = 0
                        }
                        timerState = currentExerciseTime
                    }
                } else {
                    if (timerState > 0) {
                        timerState--
                    } else {
                        rest.value = true
                        restCountdown = 60
                    }
                }
            }
        }
    }

    // Formatting the timer as MM:SS
    val minutes = timerState / 60
    val seconds = timerState % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)
    val restTime = String.format("%02d", restCountdown)

    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = "${setsDone + 1}/$totalSets  ${workout?.exercises?.get(currentExerciseIndex)?.name}",
                    button = {
                        androidx.compose.material.IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {

                                showPopup2.value = true

                            }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.graphicsLayer {
                                    rotationZ = -45f // Rotate 45 degrees counterclockwise
                                }
                            )
                        }
                    },
                    button1 = {
                        BackButton {
                            navController.popBackStack()
                        }
                    }
                )
                if (notification.value){
                    NotificationCard(message ="Notification", onClose = {notification.value=false})
                }
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.onSurface)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center

                ) {
                    item {
                        MusicPopup(
                            popup = show.value,
                            musicHandler = musicHandler,
                            currentSong = currentSong ?: Song("", "", ""),
                            totalTime = "3.0"
                        )
                    }
                    item {

                        // Exercise Image (Animated GIF)
                        Image(
                            painter = painterResource(id = R.drawable.legs), // Ensure this is a GIF resource
                            contentDescription = workout?.exercises?.get(currentExerciseIndex)?.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Exercise Title
                        Text(
                            text = "${workout?.exercises?.get(currentExerciseIndex)?.name}",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // Exercise Details
                        Text(
                            text = "Recommended time: ${workout?.exercises?.get(currentExerciseIndex)?.totalTime} sec",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Counters Section
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Repetitions Counter
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "${setsDone + 1}/$totalSets",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("Sets Done", color = Color.Gray, fontSize = 12.sp)
                            }

                            // Dynamic Counter for Minutes
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    if (!rest.value) formattedTime else restTime,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(if (!rest.value) "Time Left" else "Rest Left", color = Color.Gray, fontSize = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Start/Stop Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    isTimerRunning = !isTimerRunning
                                },
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                if (isTimerRunning) {
                                    Icon(
                                        imageVector = Icons.Default.Stop,
                                        contentDescription = "Stop",
                                        modifier = Modifier.size(60.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Start",
                                        modifier = Modifier.size(60.dp)
                                    )
                                }
                            }
                        }
                        // Spacer between button and error cards
                        Spacer(modifier = Modifier.height(16.dp))

                        // Error Warning Section
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            WarningCard(message = "Keep your back straight.")
                            WarningCard(message = "Avoid locking your elbows.")
                            WarningCard(message = "Maintain a consistent speed.")
                        }
                    }
                }
                if (showDialogShared==true) {

                    FeedbackAlertDialog(
                        title = "You have successfully Shared your content!",
                        message = "",
                        onDismissRequest = {
                        },
                        onConfirm = {
                            selectHandler(SelectEvent.SelectShowDialogShared(false))
                        },
                        confirmButtonText = "Ok",
                        dismissButtonText = "",
                        color = MaterialTheme.colorScheme.onError,
                        show = showPopup2
                    )
                }
            }
        }
        linkedApps?.let {
            ShareContentPagePopup(
                showPopup2,
                it,
                showDialogShared,
                {  selectHandler(SelectEvent.SelectShowDialogShared(true))},
                navController,
                generalViewModel
            )
        }
    }
}







@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val generalViewModel:GeneralViewModel= hiltViewModel()
    GainsAppTheme {
        SessionScreen(rememberNavController(), musicHandler = {},generalViewModel, selectHandler = {})
    }
}
