package com.project.gains.presentation.workout

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
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
import com.project.gains.presentation.events.MusicEvent
import androidx.compose.material3.Snackbar
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import com.project.gains.presentation.components.VideoAlertDialog


import com.project.gains.theme.GainsAppTheme
import kotlinx.coroutines.delay
import androidx.compose.ui.text.buildAnnotatedString as buildAnnotatedString1

@Composable
fun WorkoutModeScreen(
    navController: NavController,
    musicHandler: (MusicEvent) -> Unit,
    workoutViewModel: WorkoutViewModel,
    completionMessage: MutableState<String>
) {
    val currentSong by workoutViewModel.currentSong.observeAsState()
    val workout by workoutViewModel.selectedWorkout.observeAsState()

    var currentExerciseIndex by remember { mutableStateOf(0) }
    var timerState by remember { mutableStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var setsDone by remember { mutableStateOf(0) }
    var restCountdown by remember { mutableStateOf(60) }
    val rest = remember { mutableStateOf(false) }
    val showVideoDialog = remember { mutableStateOf(false) }

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

    // Snackbar host state
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .height(315.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(id = workout?.exercises?.get(currentExerciseIndex)?.gifResId
                            ?: R.drawable.arms2),
                        contentDescription = workout?.exercises?.get(currentExerciseIndex)?.name ?: "arms",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = workout?.exercises?.get(currentExerciseIndex)?.name ?: "arms",
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

                Button(
                    onClick = {
                        showVideoDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(
                        text = "Execution Video of ${workout?.exercises?.get(currentExerciseIndex)?.name}",
                        fontWeight = FontWeight.Bold,
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
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Sets Done", fontSize = 20.sp)
                    }

                    // Timer
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            if (!rest.value) formattedTime else restTime,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            if (!rest.value) "Time Left" else "Rest Left",
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

            // Video Dialog
            if (showVideoDialog.value) {
                VideoAlertDialog(
                    res = workout?.exercises?.get(currentExerciseIndex)?.videoId ?: R.raw.chest
                ) {
                    showVideoDialog.value = false
                }
            }
        }
        Box(   modifier = Modifier.fillMaxSize()
            .padding(top=605.dp)
            .background(MaterialTheme.colorScheme.surface) ){
            // Music Snackbar
            MusicSnackbar(
                snackbarHostState = snackbarHostState,
                musicHandler = musicHandler,
                currentSong = currentSong ?: Song("", "", "")
            )
        }
    }
}



@Composable
fun MusicSnackbar(
    snackbarHostState: SnackbarHostState,
    musicHandler: (MusicEvent) -> Unit,
    currentSong: Song,
) {
    val play = remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf(0f) }
    val songTotalTime = 165f

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

    // Display music controls as a snackbar
    Snackbar(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            ),
        action = { },
        content = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Song: ${currentSong.title}",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        maxLines = 1,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Artist: ${currentSong.singer}",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        maxLines = 1,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Album: ${currentSong.album}",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        maxLines = 1,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.material.LinearProgressIndicator(
                        progress = currentTime / songTotalTime,
                        color = MaterialTheme.colorScheme.surface,
                        backgroundColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formatTime(currentTime),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 12.sp
                        )
                        Text(
                            text = formatTime(songTotalTime),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 12.sp
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        IconButton(
                            onClick = {
                                musicHandler(MusicEvent.Rewind)
                                currentTime = 0f
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FastRewind,
                                contentDescription = "Rewind",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                play.value = !play.value
                                musicHandler(MusicEvent.Music)
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = if (play.value) Icons.Default.Stop else Icons.Default.PlayArrow,
                                contentDescription = if (play.value) "Stop" else "Play",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                musicHandler(MusicEvent.Forward)
                                currentTime = 0f
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FastForward,
                                contentDescription = "Forward",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.spotify_icon),
                    contentDescription = "Spotify Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp)
                )
            }
        }
    )
}

private fun formatTime(time: Float): String {
    val totalSeconds = time.toInt()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun WorkoutModePreview() {
    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    GainsAppTheme {
        WorkoutModeScreen(
            rememberNavController(),
            musicHandler = {},
            workoutViewModel,
            mutableStateOf("")
        )
    }
}
