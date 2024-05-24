package com.project.gains.presentation.workout
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.presentation.components.AnimatedSessionDetails
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.TopBar
import com.project.gains.theme.GainsAppTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.MuscleGroup
import com.project.gains.data.TrainingType
import com.project.gains.presentation.components.BackupPopup
import com.project.gains.presentation.components.ExerciseGif
import com.project.gains.presentation.components.MusicPopup
import com.project.gains.presentation.components.SharePopup
import com.project.gains.presentation.events.MusicEvent

import com.project.gains.presentation.events.SaveSessionEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.events.ShareContentEvent
import com.project.gains.presentation.navgraph.Route
import kotlinx.coroutines.delay


@Composable
fun SessionScreen(
    navController: NavController,
    saveSessionHandler: (SaveSessionEvent.SaveSession) -> Unit,
    shareHandler: (ShareContentEvent) -> Unit,
    selectHandler: (SelectEvent) -> Unit,
    musicHandler: (MusicEvent) -> Unit,
    generalViewModel: GeneralViewModel
) {
    val currExercise by generalViewModel.selectedExercise.observeAsState()
    val startTime = remember { System.currentTimeMillis() }
    val elapsedTime = remember { mutableLongStateOf(0L) }
    val progress = remember{ Animatable(0F) }
    val terminatedSession = remember { mutableStateOf(false) }
    val showMusicPopup by generalViewModel.showMusic.observeAsState()
    val currentSong by generalViewModel.currentSong.observeAsState()

    GainsAppTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TopBar(navController, null)
                        }

                        LaunchedEffect(Unit) {
                            while (true) {
                                elapsedTime.longValue = (System.currentTimeMillis() - startTime) / 1000
                                delay(1000L)
                            }
                        }

                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            // Only MusicPopup is shown by default
                            item { if (showMusicPopup == true) {
                                MusicPopup(popup = true, musicHandler = musicHandler,currentSong ?: "")
                            } }
                            item { if (terminatedSession.value) {
                                BackupPopup(popup = terminatedSession, shareHandler = shareHandler)
                            } }
                            item { if (terminatedSession.value) {
                                SharePopup(popup = terminatedSession, icon = R.drawable.tiktok_logo_icon, shareHandler = shareHandler)
                            }}
                            item { ExerciseGif(exercise = currExercise ?: Exercise("", 0, "", ExerciseType.BALANCE, TrainingType.STRENGTH, MuscleGroup.ARMS)) { exercise ->
                                selectHandler(SelectEvent.SelectExercise(exercise))
                                navController.navigate(Route.ExerciseDetailsScreen.route)
                            } }
                            item { AnimatedSessionDetails(
                                terminatedSession.value,
                                saveSessionHandler
                            ) }
                            item {  Spacer(modifier = Modifier.height(20.dp)) }
                            // Timer displaying elapsed time
                            item { Text(
                                text = "Elapsed Time: ${elapsedTime.longValue} seconds",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 20.sp
                            )  }

                            item {  CircularProgressIndicator(
                                progress = { progress.value },
                                color = MaterialTheme.colorScheme.onPrimary,
                            )  }

                            item {  // Buttons to show popups
                                Button(onClick = { terminatedSession.value = true },
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)) {
                                    Icon(Icons.Default.Check, contentDescription = "Save session", modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                } }

                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SessionPreview() {
    val navController = rememberNavController()
    val generalViewModel:GeneralViewModel = hiltViewModel()
    SessionScreen(
        navController = navController,
        saveSessionHandler = {},
        shareHandler = {  },
        selectHandler = {},
        musicHandler = {},
        generalViewModel = generalViewModel

    )
}



