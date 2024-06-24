package com.project.gains.presentation.workout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    val currWorkout by generalViewModel.selectedWorkout.observeAsState()

    val terminatedSession = remember { mutableStateOf(false) }
    val selectedPlan by generalViewModel.selectedPlan.observeAsState()
    val selectedWorkout by generalViewModel.selectedWorkout.observeAsState()
    var showMusicPopup = generalViewModel._selectedMusicsMap.get(selectedPlan?.id)
    var showBackupPopup = generalViewModel._selectedBackupsMap.get(selectedPlan?.id)
    val selectedApps by generalViewModel.linkedApps.observeAsState()
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
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TopBar(navController = navController, message="Session")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (showMusicPopup == true) {
                                MusicPopup(popup = true, musicHandler = musicHandler, currentSong ?: "")
                            }
                            if (terminatedSession.value && showBackupPopup == true) {
                                BackupPopup(popup = terminatedSession, shareHandler = shareHandler)
                            }
                            if (terminatedSession.value && selectedApps?.isNotEmpty() == true) {
                                selectedApps?.random()?.let {
                                    SharePopup(popup = terminatedSession, icon = it, shareHandler = shareHandler)
                                }
                            }
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                ExerciseGif(exercise = currExercise
                                    ?: Exercise("", R.drawable.gi, "", ExerciseType.BALANCE, TrainingType.STRENGTH, MuscleGroup.ARMS)) { exercise ->
                                    selectHandler(SelectEvent.SelectExercise(exercise))
                                    navController.navigate(Route.ExerciseDetailsScreen.route)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp)) // Rounded corners for the separator
                                .background(Color.White) // Background color of the separator
                                .padding(16.dp)
                        ) {
                            selectedWorkout?.id?.let {
                                selectedPlan?.id?.let { it1 ->
                                    AnimatedSessionDetails(
                                        it1,
                                        it,
                                        terminatedSession.value,
                                        saveSessionHandler
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Button(
                                    onClick = { selectHandler(SelectEvent.SelectExercise(
                                        selectedWorkout?.exercises!![0])) },
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                                ) {
                                    Icon(
                                        Icons.Default.ArrowBackIosNew,
                                        contentDescription = "Save session",
                                        modifier = Modifier.size(21.dp),
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))

                                Button(
                                    onClick = { selectHandler(SelectEvent.SelectExercise(
                                        selectedWorkout?.exercises!![0])) },
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                                ) {
                                    Icon(
                                        Icons.Default.ArrowForwardIos,
                                        contentDescription = "Save session",
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                            Button(
                                onClick = { terminatedSession.value = true },
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = "Save session",
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
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



