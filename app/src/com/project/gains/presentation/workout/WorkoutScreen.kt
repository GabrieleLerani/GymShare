package com.project.gains.presentation.workout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.BackButton

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
            topBar = {
                TopBar(navController = navController, message = "Workout")
            },
            bottomBar = { BottomNavigationBar(navController = navController) }
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
                            AddExerciseItem(exercise = exercise, { exerciseToAdd ->
                                selectHandler(SelectEvent.SelectExercise(exercise))
                                navController.navigate(Route.ExerciseDetailsScreen.route)},
                                isSelected = true
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BackButton {
                                navController.popBackStack()
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            IconButton(
                                onClick = {
                                    navController.navigate(Route.NewPlanScreen.route)
                                },
                                modifier = Modifier.size(60.dp),
                                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Share Icon",
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(10.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            IconButton(
                                onClick = {
                                    navController.navigate(Route.SessionScreen.route)
                                },
                                modifier = Modifier.size(60.dp),
                                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Start Icon",
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(10.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        } }
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


