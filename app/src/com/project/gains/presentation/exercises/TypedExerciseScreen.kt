package com.project.gains.presentation.exercises

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.Exercise
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.SearchAppBar
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManageExercises
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.theme.GainsAppTheme

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypedExerciseScreen(
    navController: NavController,
    addExerciseHandler: (ManageExercises.AddExercise) -> Unit,
    selectExerciseHandler:(ExerciseEvent.SelectExercise) -> Unit,
    workoutViewModel: WorkoutViewModel,
    exerciseViewModel: ExerciseViewModel
) {
    val isToAdd by exerciseViewModel.isToAdd.observeAsState()
    val allExercises by workoutViewModel.exercises.observeAsState()

    val searchQuery = remember { mutableStateOf("") }
    val searchedExercises = remember { mutableStateOf(listOf<Exercise>()) }
    val isSearchQueryEmpty = remember { mutableStateOf(searchQuery.value.isBlank()) }

    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                item {
                    SearchAppBar(
                        value = searchQuery.value,
                        placeholder = "Add exercises to your workout",
                        onValueChange = { query ->
                            searchQuery.value = query
                            isSearchQueryEmpty.value = query.isBlank()
                            searchedExercises.value = if (query.isNotBlank()) {
                                allExercises?.filter {
                                    it.name.contains(query, ignoreCase = true)
                                } ?: listOf()
                            } else {
                                listOf()
                            }

                        },
                        onCloseClicked = {     searchedExercises.value =  listOf<Exercise>()
                        },
                        onSearchClicked = {
                                query ->
                            searchQuery.value = query
                            isSearchQueryEmpty.value = query.isBlank()
                            searchedExercises.value = if (query.isNotBlank()) {
                                allExercises?.filter {
                                    it.name.contains(query, ignoreCase = true)
                                } ?: listOf()
                            } else {
                                listOf()
                            }

                        },
                        // this is empty because it is used for a different purpose
                        onClick = {},
                        enabled = true
                    )
                }

                items(searchedExercises.value) { exercise ->
                    AddExerciseItem(
                        exercise = exercise,
                        onItemClick = { exerciseToAdd ->
                            searchedExercises.value =
                                searchedExercises.value.toMutableList().apply {
                                    add(exerciseToAdd)
                                }
                            selectExerciseHandler(ExerciseEvent.SelectExercise(exercise))
                            navController.navigate(Route.ExerciseDetailsScreen.route)
                        },
                        onItemClick2 = {
                            addExerciseHandler(ManageExercises.AddExercise(exercise))
                            navController.popBackStack()
                        },
                        isSelected = true,
                        isToAdd = isToAdd ?: false,
                        modifier = Modifier
                    )

                    //Spacer(modifier = Modifier.padding(8.dp))

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    val exerciseViewModel: ExerciseViewModel = hiltViewModel()
    GainsAppTheme {
        TypedExerciseScreen(
            navController = rememberNavController(),
            selectExerciseHandler = {},
            workoutViewModel = workoutViewModel,
            exerciseViewModel = exerciseViewModel,
            addExerciseHandler = {},
        )
    }
}