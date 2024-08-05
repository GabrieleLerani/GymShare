package com.project.gains.presentation.workout

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.project.gains.R
import com.project.gains.data.Song
import com.project.gains.presentation.events.MusicEvent
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.settings.ShareContentViewModel
import com.project.gains.presentation.workout.events.VideoEvent
import com.project.gains.theme.GainsAppTheme
import kotlinx.coroutines.delay

@SuppressLint("RememberReturnType")
@Composable
fun WorkoutModeScreen(
    navController: NavController,
    musicHandler: (MusicEvent) -> Unit,
    workoutViewModel: WorkoutViewModel,
    videoDialogHandler: (VideoEvent.VisibilityVideoEvent) -> Unit,
    shareContentViewModel: ShareContentViewModel
) {
    val currentSong by workoutViewModel.currentSong.observeAsState()
    val workout by workoutViewModel.selectedWorkout.observeAsState()
    val showVideoDialog by workoutViewModel.showVideoDialog.observeAsState()
    val linkedApps by shareContentViewModel.linkedApps.observeAsState()

    var currentExerciseIndex by remember { mutableIntStateOf(0) }
    var timerState by remember { mutableIntStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var setsDone by remember { mutableIntStateOf(0) }
    var restCountdown by remember { mutableIntStateOf(60) }
    val rest = remember { mutableStateOf(false) }

    val currentExercise = workout?.exercises
    val currentExerciseTime = currentExercise?.get(currentExerciseIndex)?.totalTime ?: 90
    val totalSets = currentExercise?.get(currentExerciseIndex)?.sets ?: 4
    val isTimeExercise = currentExercise?.get(currentExerciseIndex)?.totalTime != -1

    val context = LocalContext.current
    val mediaPlayerSetDone = remember { MediaPlayer.create(context, R.raw.completion_sound) }
    val mediaPlayerCountDown = remember { MediaPlayer.create(context, R.raw.countdown) }
    val mediaPlayerWellDone = remember { MediaPlayer.create(context, R.raw.well_done) }
    val mediaPlayerStarted = remember { MediaPlayer.create(context, R.raw.started) }

    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            mediaPlayerStarted.start() // Play sound when the timer starts
            while (isTimerRunning) {
                delay(1000L)
                if (rest.value) {
                    if (restCountdown > 0) {
                        restCountdown--
                    } else {
                        rest.value = false
                        setsDone++
                        if (setsDone >= totalSets) {
                            currentExerciseIndex = (currentExerciseIndex + 1) % currentExercise!!.size
                            setsDone = 0
                        }
                        timerState = currentExerciseTime
                    }
                } else {
                    if (timerState > 0) {
                        timerState--
                        if (timerState == 10) {
                            mediaPlayerCountDown.start()
                        }
                    } else {
                        rest.value = true
                        restCountdown = 60
                    }
                }
            }
        }
    }

    Log.d("WorkoutModeScreen", "timerState: $timerState")

    val formattedTime = String.format("%02d:%02d", timerState / 60, timerState % 60)
    Log.d("WorkoutModeScreen", "formattedTime: $formattedTime")
    val restTime = String.format("%02d", restCountdown)
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBar(
                message = "Workout Mode",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close Icon")
                    }
                },
                actionIcon = {}
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    if (showVideoDialog == true) {
                        VideoAlertDialog(
                            res = currentExercise?.get(currentExerciseIndex)?.videoId ?: R.raw.chest,
                            onDismiss = { videoDialogHandler(VideoEvent.VisibilityVideoEvent(false)) }
                        )
                    } else {
                        Image(
                            painter = painterResource(id = currentExercise?.get(currentExerciseIndex)?.gifResId ?: R.drawable.arms2),
                            contentDescription = currentExercise?.get(currentExerciseIndex)?.name ?: "arms",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Text(
                            text = currentExercise?.get(currentExerciseIndex)?.name ?: "arms",
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
                        IconButton(
                            onClick = { videoDialogHandler(VideoEvent.VisibilityVideoEvent(true)) },
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color.Black, shape = RoundedCornerShape(16.dp))
                            ) {
                                Icon(
                                    Icons.Filled.PlayCircleOutline,
                                    contentDescription = "Play Icon",
                                    tint = Color.White,
                                    modifier = Modifier.scale(6f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${setsDone + 1}/$totalSets", fontSize = 60.sp, fontWeight = FontWeight.Bold)
                        Text("Sets Done", fontSize = 30.sp)
                    }

                    if (isTimeExercise) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(if (!rest.value) formattedTime else restTime, fontSize = 60.sp, fontWeight = FontWeight.Bold)
                            Text(if (!rest.value) "Time Left" else "Rest Left", fontSize = 30.sp)
                        }
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("x${currentExercise?.get(currentExerciseIndex)?.repetition}", fontSize = 60.sp, fontWeight = FontWeight.Bold)
                            Text("Repetition", fontSize = 30.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {


                    // Center Button Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isTimeExercise) {
                            Button(
                                onClick = {
                                    mediaPlayerSetDone.start()
                                    if (setsDone == (currentExercise?.get(currentExerciseIndex)?.sets?.minus(1) ?: 0)) {
                                        mediaPlayerWellDone.start()
                                        currentExerciseIndex = if (currentExerciseIndex > 0) currentExerciseIndex - 1 else currentExercise!!.size - 1
                                        isTimerRunning = false
                                        timerState = currentExerciseTime
                                        setsDone = 0
                                        restCountdown = 60
                                        rest.value = false
                                    } else {
                                        isTimerRunning = false
                                        timerState = currentExerciseTime
                                        setsDone += 1
                                        restCountdown = 60
                                        rest.value = true
                                    } },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .fillMaxWidth(0.8f)
                            ) {
                                Icon(Icons.Default.Check, contentDescription = "Done", modifier = Modifier
                                    .size(20.dp)
                                    .padding(end = 4.dp))
                                Text("DONE", fontSize = 20.sp)
                            }
                        } else {
                            Button(
                                onClick = { isTimerRunning = !isTimerRunning },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(0.8f)
                            ) {
                                if (isTimerRunning){
                                    Icon(Icons.Default.Pause, contentDescription = "Pause")
                                    Text("PAUSE", fontSize = 20.sp)
                                } else {
                                    Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                                    Text("START", fontSize = 20.sp)
                                }

                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Back and Forward Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilledTonalButton(
                            onClick = {
                                currentExerciseIndex = if (currentExerciseIndex > 0) currentExerciseIndex - 1 else currentExercise!!.size - 1
                                isTimerRunning = false
                                //timerState = currentExerciseTime
                                timerState = currentExercise?.get(currentExerciseIndex)?.totalTime ?: 90
                                setsDone = 0
                                restCountdown = 60
                                rest.value = false
                            },
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.skip_previous), contentDescription = "Previous", modifier = Modifier.size(30.dp))
                            Text(text = "Prev", modifier = Modifier.padding(start = 4.dp))
                        }

                        FilledTonalButton(
                            onClick = {
                                currentExerciseIndex = (currentExerciseIndex + 1) % currentExercise!!.size
                                isTimerRunning = false
                                //timerState = currentExerciseTime
                                timerState = currentExercise?.get(currentExerciseIndex)?.totalTime ?: 90
                                setsDone = 0
                                restCountdown = 60
                                rest.value = false
                            },
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.skip_next), contentDescription = "Next", modifier = Modifier.size(30.dp))
                            Text(text = "Next", modifier = Modifier.padding(start = 4.dp))
                        }
                    }

                }

            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = LocalConfiguration.current.screenHeightDp.dp - 120.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            MusicSnackbar(
                snackbarHostState = snackbarHostState,
                musicHandler = musicHandler,
                currentSong = currentSong ?: Song("Linkin Park", "", "In the end"),
                show = linkedApps?.contains(R.drawable.spotify_icon) == true
            )
        }
    }
}

@Composable
fun MusicSnackbar(
    snackbarHostState: SnackbarHostState,
    musicHandler: (MusicEvent) -> Unit,
    currentSong: Song,
    show: Boolean
) {
    if (show) {
        val play = remember { mutableStateOf(false) }
        var currentTime by remember { mutableStateOf(0f) }
        val songTotalTime = 165f

        LaunchedEffect(play.value) {
            if (play.value) {
                while (currentTime < songTotalTime && play.value) {
                    delay(100L)
                    currentTime += 0.1f
                    if (currentTime % 1.0f >= 0.9f) currentTime = (currentTime - (currentTime % 1.0f)) + 1f
                    if (currentTime >= songTotalTime) {
                        play.value = false
                        musicHandler(MusicEvent.Forward)
                    }
                }
            }
        }

        Snackbar(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(20.dp))
                .height(110.dp),
            action = {},
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Row {
                            Icon(painter = painterResource(id = R.drawable.spotify_icon), contentDescription = "Spotify Icon", modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Spotify", style = MaterialTheme.typography.bodySmall)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                                Text("Song: ${currentSong.title}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, fontSize = 20.sp, maxLines = 1)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Singer: ${currentSong.singer}", style = MaterialTheme.typography.bodySmall, fontSize = 20.sp, maxLines = 1)
                            }
                            Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { musicHandler(MusicEvent.Rewind); currentTime = 0f }, modifier = Modifier.size(50.dp)) {
                                    Icon(Icons.Default.FastRewind, contentDescription = "Rewind", modifier = Modifier.size(50.dp))
                                }
                                IconButton(onClick = { play.value = !play.value; musicHandler(MusicEvent.Music) }, modifier = Modifier.size(50.dp)) {
                                    Icon(if (play.value) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = if (play.value) "Stop" else "Play", modifier = Modifier.size(50.dp))
                                }
                                IconButton(onClick = { musicHandler(MusicEvent.Forward); currentTime = 0f }, modifier = Modifier.size(50.dp)) {
                                    Icon(Icons.Default.FastForward, contentDescription = "Forward", modifier = Modifier.size(50.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        androidx.compose.material.LinearProgressIndicator(
                            progress = currentTime / songTotalTime,
                            color = MaterialTheme.colorScheme.surface,
                            backgroundColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun VideoAlertDialog(res: Int, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val uri = remember { Uri.parse("android.resource://" + context.packageName + "/" + res) }

    Column(modifier = Modifier.fillMaxSize()) {
        VideoPlayer(uri = uri, modifier = Modifier
            .fillMaxHeight()
            .align(Alignment.CenterHorizontally))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .background(Color.White.copy(alpha = 0.7f), shape = MaterialTheme.shapes.small)
                .align(Alignment.TopStart)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Dismiss")
        }
    }
}

@Composable
fun VideoPlayer(uri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            VideoView(context).apply {
                setVideoURI(uri)
                setOnPreparedListener { it.start() }
            }
        },
        modifier = modifier
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun WorkoutModePreview() {
    var currentExerciseIndex by remember { mutableStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var timerState by remember { mutableStateOf(90) }
    var setsDone by remember { mutableStateOf(0) }
    var restCountdown by remember { mutableStateOf(60) }
    val rest = remember { mutableStateOf(false) }
    val currentExerciseSize = 5 // Dummy size for preview
    val currentExerciseTime = 90 // Dummy time for preview

    GainsAppTheme {

        Box(modifier = Modifier.fillMaxSize()) {

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            currentExerciseIndex = if (currentExerciseIndex > 0) currentExerciseIndex - 1 else currentExerciseSize - 1
                            isTimerRunning = false
                            timerState = currentExerciseTime
                            setsDone = 0
                            restCountdown = 60
                            rest.value = false
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous", modifier = Modifier.size(60.dp))
                    }

                    Button(
                        onClick = { isTimerRunning = !isTimerRunning },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = if (isTimerRunning) "Pause" else "Start", modifier = Modifier.size(60.dp))
                    }

                    Button(
                        onClick = {
                            currentExerciseIndex = (currentExerciseIndex + 1) % currentExerciseSize
                            isTimerRunning = false
                            timerState = currentExerciseTime
                            setsDone = 0
                            restCountdown = 60
                            rest.value = false
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next", modifier = Modifier.size(60.dp))
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("00:30", fontSize = 60.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Text("time left", fontSize = 30.sp, textAlign = TextAlign.Center)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("00:30", fontSize = 60.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Text("time left", fontSize = 30.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }
}
