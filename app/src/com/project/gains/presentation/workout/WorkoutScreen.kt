package com.project.gains.presentation.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.data.Workout

import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.ExerciseItem
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
    // Sample list of exercises
    val exercises by generalViewModel.exercises.observeAsState()
    val workout by generalViewModel.selectedWorkout.observeAsState()
    GainsAppTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column( modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TopBar(navController,null)
                    }
                        LazyColumn(
                            modifier = Modifier.fillMaxSize() ,
                        ) {
                            item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp, top = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        navController.navigate(Route.WorkoutSelectionScreen.route) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer, // Orange
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Text color
                                    ),
                                    modifier = Modifier.weight(1f) // This will make the buttons occupy equal space
                                ) {
                                    Text("Select Workout")
                                }
                                Spacer(modifier = Modifier.width(16.dp)) // Spacer to add space between buttons
                                Button(
                                    onClick = { selectHandler(SelectEvent.SelectWorkout(workout ?: Workout(1,"", mutableListOf())))
                                        navController.navigate(Route.SessionScreen.route) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer, // Orange
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Text color
                                    ),
                                    modifier = Modifier.weight(1f) // This will make the buttons occupy equal space
                                ) {
                                    Text("Start Workout")
                                }
                            }
                        }
                            // Ensure exercises is a non-null, empty list if it is null

                            items(exercises ?: emptyList()) { exercise ->
                                ExerciseItem(
                                    exercise = exercise,
                                    onDelete = { exerciseToDelete ->
                                        deleteHandler(DeleteEvent.DeleteExercise(exerciseToDelete))
                                        // Update exercises list safely
                                        exercises?.toMutableList()?.apply { remove(exerciseToDelete) }
                                    },
                                    onItemClick = { exerciseToSelect ->
                                        selectHandler(SelectEvent.SelectExercise(exerciseToSelect))
                                        navController.navigate(Route.ExerciseDetailsScreen.route)
                                    }
                                )
                            }

                        }
                }
            }
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
        deleteHandler = {  },
        selectHandler = {},
        generalViewModel = generalViewModel

    )
}


