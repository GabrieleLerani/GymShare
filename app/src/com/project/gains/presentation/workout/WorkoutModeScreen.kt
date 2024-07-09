package com.project.gains.presentation.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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


            Box(
                modifier = Modifier
                    .fillMaxSize()
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
                                painter = painterResource(id = workout?.exercises?.get(currentExerciseIndex)?.gifResId
                                    ?: R.drawable.arms2),
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

@Composable
fun MusicPopup(popup: Boolean, musicHandler: (MusicEvent) -> Unit, currentSong: Song) {
    if (popup) {
        val play = remember { mutableStateOf(false) }
        var currentTime by remember { mutableFloatStateOf(0f) }
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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(240.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(Color.Black)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.spotify2),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(2.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Song: ${currentSong.title}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.surface
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Artist: ${currentSong.singer}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.surface
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Album: ${currentSong.album}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                androidx.compose.material.LinearProgressIndicator(
                    progress = currentTime / songTotalTime,
                    color = MaterialTheme.colorScheme.surface,
                    backgroundColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(16.dp)
                        .height(8.dp)
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
                        color = MaterialTheme.colorScheme.surface
                    )
                    Text(
                        text = formatTime(songTotalTime),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            musicHandler(MusicEvent.Rewind)
                            currentTime = 0f
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastRewind,
                            contentDescription = "Rewind",
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            play.value = !play.value
                            musicHandler(MusicEvent.Music)
                        }
                    ) {
                        Icon(
                            imageVector = if (play.value) Icons.Default.Stop else Icons.Default.PlayArrow,
                            contentDescription = if (play.value) "Stop" else "Play",
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            musicHandler(MusicEvent.Forward)
                            currentTime = 0f
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastForward,
                            contentDescription = "Forward",
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun formatTime(time: Float): String {
    val totalSeconds = time.toInt()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}

@Preview(showBackground = true)
@Composable
fun WorkoutModePreview() {
    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    GainsAppTheme {
        WorkoutModeScreen(rememberNavController(), musicHandler = {}, workoutViewModel)
    }
}
