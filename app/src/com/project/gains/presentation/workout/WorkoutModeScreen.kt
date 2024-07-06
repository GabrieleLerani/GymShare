package com.project.gains.presentation.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R
import com.project.gains.data.Song
import com.project.gains.presentation.components.MusicPopup
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.events.MusicEvent

import com.project.gains.theme.GainsAppTheme
import kotlinx.coroutines.delay


@Composable
fun WorkoutModeScreen(
    navController: NavController,
    musicHandler: (MusicEvent) -> Unit,
    workoutViewModel: WorkoutViewModel
) {
    val currentSong by workoutViewModel.currentSong.observeAsState()
    val workout by workoutViewModel.selectedWorkout.observeAsState()
    val show = remember { mutableStateOf(true) }

    var currentExerciseIndex by remember { mutableIntStateOf(0) }
    var timerState by remember { mutableIntStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var setsDone by remember { mutableIntStateOf(0) }
    var restCountdown by remember { mutableIntStateOf(60) }
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
                    message = "Workout Mode",
                    button = { }
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(50.dp)
                        )
                    }
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
                            currentSong = currentSong ?: Song("", "", "")
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
                                    "${setsDone + 1}/$totalSets",
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
                                    } else {
                                        currentExerciseIndex = workout?.exercises?.size!! - 1
                                    }
                                    isTimerRunning = false
                                    timerState = currentExerciseTime
                                    setsDone = 0
                                    restCountdown = 60
                                    rest.value = false
                                },
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                                    if (currentExerciseIndex < workout?.exercises?.size!! - 1) {
                                        currentExerciseIndex++
                                    } else {
                                        currentExerciseIndex = 0
                                    }
                                    isTimerRunning = false
                                    timerState = currentExerciseTime
                                    setsDone = 0
                                    restCountdown = 60
                                    rest.value = false
                                },
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
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
    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    GainsAppTheme {
        WorkoutModeScreen(rememberNavController(), musicHandler = {}, workoutViewModel)
    }
}
