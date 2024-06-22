package com.project.gains.presentation.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.data.Workout
import com.project.gains.presentation.components.BackButton

import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.ExerciseItem
import com.project.gains.presentation.components.UserContentCard
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
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TopBar(userProfile = null, navController = navController)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BackButton {
                            navController.popBackStack()
                        }
                        Spacer(modifier = Modifier.padding(horizontal = 40.dp))
                        // Title
                        Text(
                            text = "Your Workout",
                            style = MaterialTheme.typography.headlineMedium,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                        )
                    }
                    // Horizontal separator around the post
                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the separator
                            .background(Color.White) // Background color of the separator
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2), // Adjust the number of columns as needed
                            contentPadding = PaddingValues(2.dp) // Add padding around the grid
                        ) {
                            exercises?.forEach { exercise ->
                                item {
                                    Box(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the separator
                                            .background(Color.White) // Background color of the separator
                                    ) {
                                        ExerciseItem(
                                            exercise = exercise,
                                            onDelete = { exerciseToDelete ->
                                                deleteHandler(
                                                    DeleteEvent.DeleteExercise(
                                                        exerciseToDelete
                                                    )
                                                )
                                                // Update exercises list safely
                                                exercises?.toMutableList()
                                                    ?.apply { remove(exerciseToDelete) }
                                            },
                                            onItemClick = { exerciseToSelect ->
                                                selectHandler(
                                                    SelectEvent.SelectExercise(
                                                        exerciseToSelect
                                                    )
                                                )
                                                navController.navigate(Route.ExerciseDetailsScreen.route)
                                            }
                                        )

                                    }
                                }
                            }
                        }
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Button(
                            onClick = {
                                selectHandler(
                                    SelectEvent.SelectWorkout(
                                        workout ?: Workout(1, "", mutableListOf())
                                    )
                                )
                                navController.navigate(Route.SessionScreen.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer, // Orange
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Text color
                            )) {
                            Text("Start Workout")
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


