package com.project.gains.presentation.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.BackButton

import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.ShareContentPagePopup
import com.project.gains.presentation.events.DeleteEvent
import com.project.gains.presentation.events.SelectEvent

import com.project.gains.presentation.navgraph.Route
import com.project.gains.theme.GainsAppTheme

@Composable
fun WorkoutScreen(
    navController: NavController,
    deleteHandler: (DeleteEvent.DeleteExercise) -> Unit,
    selectHandler: (SelectEvent) -> Unit,
    generalViewModel: GeneralViewModel

) {
    val linkedApps by generalViewModel.linkedApps.observeAsState()
    var showPopup2 = remember { mutableStateOf(false) }
    val showDialogShared by generalViewModel.showDialogShared.observeAsState()

    var notification = remember {
        mutableStateOf(false)
    }

    // Sample list of exercises
    val exercises by generalViewModel.exercises.observeAsState()
    val workout by generalViewModel.selectedWorkout.observeAsState()
    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = workout?.name ?: "Workout",
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
            bottomBar = {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(64.dp)
                        .background(MaterialTheme.colorScheme.onSurface),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(Route.SessionScreen.route)
                        },
                        modifier = Modifier.size(50.dp),
                        colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Start Icon",
                            modifier = Modifier
                                .size(50.dp)
                                .padding(10.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center

                ) {
                    exercises?.forEach { exercise ->
                        item {
                            AddExerciseItem(
                                exercise = exercise, { exerciseToAdd ->
                                    selectHandler(SelectEvent.SelectExercise(exercise))
                                    navController.navigate(Route.ExerciseDetailsScreen.route)
                                },
                                onItemClick2 = {},
                                isSelected = true,
                                isToAdd = false,
                                modifier = Modifier
                            )
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
                    { selectHandler(SelectEvent.SelectShowDialogShared(true)) },
                    navController
                )
            }
        }

    }





    @Preview(showBackground = true)
    @Composable
    fun WorkoutScreenPreview() {
        val navController = rememberNavController()
        val generalViewModel: GeneralViewModel = hiltViewModel()
        WorkoutScreen(
            navController = navController,
            deleteHandler = { },
            selectHandler = {},
            generalViewModel = generalViewModel

        )
    }



