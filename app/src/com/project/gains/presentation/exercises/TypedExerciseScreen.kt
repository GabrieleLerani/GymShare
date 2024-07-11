package com.project.gains.presentation.exercises

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.project.gains.data.Exercise
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.SearchAppBar
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.explore.events.SearchEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManageExercises
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.theme.GainsAppTheme

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypedExerciseScreen(
    navController: NavController,
    addExerciseHandler: (ManageExercises.AddExercise) -> Unit,
    removeExerciseHandler: (ManageExercises.DeleteExercise) -> Unit,
    selectExerciseHandler:(ExerciseEvent.SelectExercise)->Unit,
    workoutViewModel: WorkoutViewModel,
    exerciseViewModel: ExerciseViewModel
    ) {
    val isToAdd by exerciseViewModel.isToAdd.observeAsState()
    val allExercises by workoutViewModel.exercises.observeAsState()


    val searchQuery = remember { mutableStateOf("") }
    val searchedExercises = remember { mutableStateOf(listOf<Exercise>()) }
    val isSearchQueryEmpty = remember { mutableStateOf(searchQuery.value.isBlank()) }
    val localKeyboardController = LocalSoftwareKeyboardController.current
    val notification = remember {
        mutableStateOf(false)
    }

    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    SearchAppBar(
                        value = searchQuery.value,
                        placeholder = "Add exercises to your workout",
                        onValueChange = {
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
                        onCloseClicked = {},
                        onSearchClicked = {

                        },
                        // this is empty because it is used for a different purpose
                        onClick = {},
                        enabled = true
                    )
                }
             /*   item {
                    // Remember if the search query is empty
                    TextField(
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        value = searchQuery.value,
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
                        label = {
                            if (isSearchQueryEmpty.value) {
                                Text("Search Exercise")
                            }
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .alpha(ContentAlpha.medium),
                                text = "Search exercises",
                            )
                        },
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    searchedExercises.value = allExercises?.filter {
                                        it.name.contains(searchQuery.value, ignoreCase = true)
                                    } ?: listOf()
                                    localKeyboardController?.hide()
                                },
                                content = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search Icon",
                                    )
                                }
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    if (isSearchQueryEmpty.value) {
                                        searchQuery.value=""
                                    } else {
                                        onCloseClicked()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close Icon",
                                )
                            }
                        },
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                searchedExercises.value = allExercises?.filter {
                                    it.name.contains(searchQuery.value, ignoreCase = true)
                                } ?: listOf()
                                localKeyboardController?.hide()
                            }
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )
                }*/

                items(searchedExercises.value) { exercise ->
                    AddExerciseItem(
                        exercise = exercise,
                        onItemClick = { exerciseToAdd ->
                            searchedExercises.value = searchedExercises.value.toMutableList().apply {
                                add(exerciseToAdd)
                            }
                            selectExerciseHandler(ExerciseEvent.SelectExercise(exercise))
                            navController.navigate(Route.ExerciseDetailsScreen.route)
                        },
                        onItemClick2 = {
                            addExerciseHandler(ManageExercises.AddExercise(exercise))
                            navController.navigate(Route.AddManualWorkoutScreen.route)
                        },
                        isSelected = true,
                        isToAdd = isToAdd ?: false,
                        modifier = Modifier
                    )
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
            removeExerciseHandler = {}
        )
    }
}