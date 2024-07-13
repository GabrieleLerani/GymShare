package com.project.gains.presentation.workout


import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment


import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.gains.presentation.settings.ShareContentViewModel

import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.FavoriteTopBar


import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.getPreviousDestination


import com.project.gains.presentation.exercises.events.ExerciseEvent

import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.settings.events.ManageDialogEvent
import com.project.gains.presentation.workout.events.ManageWorkoutEvent
import com.project.gains.theme.GainsAppTheme

@Composable
fun WorkoutScreen(
    navController: NavController,
    shareHandler: (ManageDialogEvent) -> Unit,
    exerciseHandler: (ExerciseEvent) -> Unit,
    workoutViewModel: WorkoutViewModel,
    shareContentViewModel: ShareContentViewModel,
    addFavouriteWorkoutHandler: (ManageWorkoutEvent.AddWorkoutFavourite) -> Unit,
    removeFavouriteWorkoutHandler: (ManageWorkoutEvent.DeleteWorkoutFavourite) -> Unit,
    completionMessage: MutableState<String>


) {
    val favoriteWorkouts by workoutViewModel.favouriteWorkouts.observeAsState()

    // Sample list of exercises
    val selectedWorkout by workoutViewModel.selectedWorkout.observeAsState()

    val favorite = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        showDialog.value=false
    }


    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                selectedWorkout?.exercises?.forEach { exercise ->
                    item {
                        AddExerciseItem(
                            exercise = exercise, {

                                exerciseHandler(ExerciseEvent.SelectExercise(exercise))
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

            // First Box (Top bar)
            FavoriteTopBar(
                message = "Workout",
                button2 = {
                    IconButton(
                        modifier = Modifier
                            .size(45.dp)
                            .fillMaxWidth(),
                        onClick = {
                            if (favorite.value){
                                removeFavouriteWorkoutHandler(ManageWorkoutEvent.DeleteWorkoutFavourite)
                                favorite.value=false
                                completionMessage.value="Workout removed from favorites!"
                                showDialog.value=true
                            }
                            else{
                                addFavouriteWorkoutHandler(ManageWorkoutEvent.AddWorkoutFavourite)
                                favorite.value=true
                                completionMessage.value="Workout added to favorites!"
                                showDialog.value=true
                            }
                        }) {
                        Icon(
                            imageVector = if (favorite.value){
                                Icons.Default.Favorite
                            }else if (favoriteWorkouts?.contains(selectedWorkout) == true) {
                                Icons.Default.Favorite
                            }
                            else {
                                Icons.Default.FavoriteBorder
                            },
                            contentDescription = "Favorite",
                        )
                    }
                },
                button = {
                    IconButton(
                        modifier = Modifier.size(45.dp),
                        onClick = {
                            navController.navigate(Route.ShareScreen.route)
                        }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Share",
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
                },
            )


        }



    }

}






