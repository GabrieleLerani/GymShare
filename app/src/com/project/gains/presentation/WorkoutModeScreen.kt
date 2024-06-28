package com.project.gains.presentation

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
fun WorkoutModeScreen(
    navController: NavController,
    musicHandler: (MusicEvent) -> Unit,
    generalViewModel: GeneralViewModel,
    selectHandler: (SelectEvent) -> Unit
) {
    val currentSong by generalViewModel.currentSong.observeAsState()
    var timerState by remember { mutableStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var setsDone by remember { mutableStateOf(0) }
    var restCountdown by remember { mutableStateOf(60) }
    val show = remember { mutableStateOf(true) }
    val rest = remember { mutableStateOf(false) }

    // Dummy state for current exercise index
    val workout by generalViewModel.selectedWorkout.observeAsState()

    var currentExerciseIndex by remember { mutableStateOf(0) }

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
                        timerState = 0
                    }
                } else {
                    timerState++
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
                    message = "Workout Mode",
                    button = { },
                    button1 = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close Icon",
                                tint = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                )
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
                        Box(
                            modifier = Modifier
                                .height(360.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            Image(
                                painter = painterResource(id = workout?.exercises?.get(currentExerciseIndex)?.gifResId ?: R.drawable.arms2),
                                contentDescription = workout?.exercises?.get(currentExerciseIndex)?.name ?: "arms",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .matchParentSize()
                            )
                            Text(
                                text =  workout?.exercises?.get(currentExerciseIndex)?.name ?: "arms",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .background(
                                        Color.Black.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Counters Section
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Sets Done Counter
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    setsDone.toString(),
                                    color = Color.White,
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("Sets Done", color = Color.Gray, fontSize = 20.sp)
                            }

                            // Timer
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    if (!rest.value) formattedTime else restTime,
                                    color = Color.White,
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    if (!rest.value) "Time Left" else "Rest Left",
                                    color = Color.Gray,
                                    fontSize = 20.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Action Row (Previous, Start/Stop, Next Buttons)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Previous Exercise Button
                            Button(
                                onClick = {
                                    if (currentExerciseIndex > 0) {
                                        currentExerciseIndex--
                                        // Reset the timer and other states if necessary
                                        isTimerRunning = false
                                        timerState = 0
                                        restCountdown = 60
                                        rest.value = false
                                    }
                                },
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Previous",
                                    modifier = Modifier.size(60.dp)
                                )
                            }

                            // Start/Stop Button
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

                            // Next Exercise Button
                            Button(
                                onClick = {
                                    currentExerciseIndex++
                                    // Reset the timer and other states if necessary
                                    isTimerRunning = false
                                    timerState = 0
                                    restCountdown = 60
                                    rest.value = false
                                },
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Next",
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun WorkoutModePreview() {
    val generalViewModel:GeneralViewModel= hiltViewModel()
    GainsAppTheme {
        WorkoutModeScreen(rememberNavController(), musicHandler = {},generalViewModel, selectHandler = {})
    }
}
