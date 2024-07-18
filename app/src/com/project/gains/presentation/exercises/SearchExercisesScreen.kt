package com.project.gains.presentation.exercises

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.Exercise
import com.project.gains.presentation.components.ExerciseItem
import com.project.gains.presentation.components.ExerciseSearchBar
import com.project.gains.presentation.components.SearchAppBar
import com.project.gains.presentation.components.SearchViewModel
import com.project.gains.presentation.components.events.ManageCategoriesEvent
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManageExercises
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.theme.GainsAppTheme

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchExercisesScreen(
    navController: NavController,
    addExerciseHandler: (ManageExercises.AddExercise) -> Unit,
    selectExerciseHandler:(ExerciseEvent.SelectExercise) -> Unit,
    assignCategoryHandler: (ManageCategoriesEvent.AssignExerciseCategoryEvent) -> Unit,
    workoutViewModel: WorkoutViewModel,
    exerciseViewModel: ExerciseViewModel,
    searchViewModel: SearchViewModel
) {
    val isToAdd by exerciseViewModel.isToAdd.observeAsState()
    val allExercises by workoutViewModel.exercises.observeAsState()

    val categories = searchViewModel.exerciseCategories
    val selectedCategory by searchViewModel.selectedExerciseCategory.observeAsState()

    val searchQuery = remember { mutableStateOf("") }
    val searchedExercises = remember { mutableStateOf(listOf<Exercise>()) }
    val isSearchQueryEmpty = remember { mutableStateOf(searchQuery.value.isBlank()) }

    GainsAppTheme {
        Box(
            Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true }) {

            ExerciseSearchBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .semantics { traversalIndex = 0f },
                categories = categories,
                selectedCategory = selectedCategory.toString(),
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
                assignCategoryHandler = assignCategoryHandler
            )

            LazyColumn(
                modifier = Modifier.semantics { traversalIndex = 1f },
                contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                items(searchedExercises.value) { exercise ->
                    ExerciseItem(
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
                        onRemove = {},
                        isSelected = true,
                        isToAdd = isToAdd ?: false,
                        isToRemove = false,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
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
    val searchViewModel: SearchViewModel = hiltViewModel()
    GainsAppTheme {
        SearchExercisesScreen(
            navController = rememberNavController(),
            selectExerciseHandler = {},
            addExerciseHandler = {},
            assignCategoryHandler = {},
            workoutViewModel = workoutViewModel,
            exerciseViewModel = exerciseViewModel,
            searchViewModel = searchViewModel
        )
    }
}