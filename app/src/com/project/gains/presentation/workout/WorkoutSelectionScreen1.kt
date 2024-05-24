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
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.data.generateSampleWorkouts
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.WorkoutList
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.DeleteEvent
import com.project.gains.presentation.events.SelectEvent

import com.project.gains.presentation.navgraph.Route
import com.project.gains.theme.GainsAppTheme

@Composable
fun WorkoutSelectionScreen(
    navController: NavController,
    generalViewModel: GeneralViewModel,
    selectHandler: (SelectEvent) -> Unit,
    deleteHandler: (DeleteEvent) -> Unit,
    createHandler: (CreateEvent) -> Unit,

) {
    // Sample list of workouts
    val workouts by generalViewModel.workouts.observeAsState()


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
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 26.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BackButton {
                                navController.popBackStack()
                            }
                            // Title
                            Text(
                                text = "Select a workout",
                                style = MaterialTheme.typography.headlineMedium,
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 0.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Button(
                                onClick = {
                                    createHandler(CreateEvent.CreateWorkout(generateSampleWorkouts()[0]))
                                    navController.navigate(Route.AddExerciseScreenScreen.route) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer, // Orange
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Text color
                                ),
                                modifier = Modifier.padding(bottom = 0.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add Icon") // Add icon here
                            }
                        }

                        // List of workouts
                        WorkoutList(workouts, { workout ->
                            selectHandler(SelectEvent.SelectWorkout(workout))
                            navController.navigate(Route.WorkoutScreen.route)
                        }) { workout ->
                            deleteHandler(DeleteEvent.DeleteWorkout(workout))
                            workouts?.remove(workout)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutSelectionScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel:GeneralViewModel = hiltViewModel()
    WorkoutSelectionScreen(
        navController = navController,
        createHandler = {  },
        deleteHandler = {},
        selectHandler = {},
        generalViewModel = generalViewModel

    )
}


