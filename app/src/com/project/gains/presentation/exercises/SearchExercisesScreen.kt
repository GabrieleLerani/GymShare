package com.project.gains.presentation.exercises

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.project.gains.data.ExerciseButtonMode
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.ExerciseItem
import com.project.gains.presentation.components.ExerciseSearchBar
import com.project.gains.presentation.components.SearchViewModel
import com.project.gains.presentation.components.events.ManageCategoriesEvent
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManageExercises
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.theme.GainsAppTheme
import java.util.Locale

//@OptIn(ExperimentalMaterial3Api::class)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchExercisesScreen(
    navController: NavController,
    addExerciseHandler: (ManageExercises.AddAllExercises) -> Unit,
    selectExerciseHandler:(ExerciseEvent.SelectExercise) -> Unit,
    assignCategoryHandler: (ManageCategoriesEvent.AssignExerciseCategoryEvent) -> Unit,
    workoutViewModel: WorkoutViewModel,
    exerciseViewModel: ExerciseViewModel,
    searchViewModel: SearchViewModel
) {
    val isToAdd by exerciseViewModel.isToAdd.observeAsState()
    val allExercises by workoutViewModel.exercises.observeAsState(mutableListOf())

    val categories = searchViewModel.exerciseCategories
    val selectedCategory by searchViewModel.selectedExerciseCategory.observeAsState()

    val searchQuery by searchViewModel.searchQuery.observeAsState("")
    val searchedExercises by searchViewModel.searchedExercises.observeAsState(listOf())

    // used to implement multi selection
    val selectedExercises = remember { mutableStateListOf<Exercise>() }
    val multiSelectionState = rememberMultiSelectionState()

    // Initialize searchedExercises with allExercises when the screen is first composed
    LaunchedEffect(allExercises) {
        if (searchQuery.isBlank() && searchedExercises.isEmpty()) {
            searchViewModel.setAllExercises(allExercises)
        }
    }

    val previousRoute = navController.previousBackStackEntry?.destination?.route
    val canSelectExercise = if (previousRoute == Route.AddManualWorkoutScreen.route){
        isToAdd ?: false
    } else {
        false
    }

    val groupedExercises = searchedExercises.groupBy { it.name[0] } // group by first char
    //val groupedExercises = exercises.groupBy { it.name[0] } // group by first char

    GainsAppTheme {
        Box(
            Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true }) {

            Column(modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)) {

                ExerciseTopBar(
                    message = "Exercises",
                    navigationIcon = {
                        if (multiSelectionState.isMultiSelectionModeEnabled){
                            IconButton(
                                onClick = {
                                    multiSelectionState.isMultiSelectionModeEnabled = false
                                }) {
                                Icon(Icons.Default.Close, contentDescription = "exit from exercises selection")
                            }
                        }
                        else {
                            BackButton {
                                navController.popBackStack()
                            }
                        }
                    },
                    actionIcon = {
                        if (multiSelectionState.isMultiSelectionModeEnabled && selectedExercises.size > 0){
                            IconButton(
                                onClick = {
                                    // save all exercises
                                    addExerciseHandler(ManageExercises.AddAllExercises(selectedExercises))

                                    // clear the list
                                    selectedExercises.clear()
                                    multiSelectionState.isMultiSelectionModeEnabled = false

                                    navController.popBackStack()


                                }) {
                                Icon(Icons.Default.Check, contentDescription = "select all exercises")
                            }
                        }

                    }
                )

                // TODO test it
                ExerciseSearchBar(
                    modifier = Modifier
                        .semantics { traversalIndex = 0f },
                    categories = categories,
                    selectedCategory = selectedCategory.toString(),
                    onSearchClicked = { query ->
                        searchViewModel.updateSearchQuery(query)
                        val exercises =
                            if (query.isNotBlank() && selectedCategory != null) {
                                    allExercises?.filter {
                                        it.name.contains(query, ignoreCase = true) && it.type == selectedCategory
                                    } ?: listOf()
                            } else if (query.isNotBlank()) {
                                allExercises?.filter {
                                    it.name.contains(query, ignoreCase = true)
                                } ?: listOf()
                            } else if (selectedCategory != null) {
                                allExercises?.filter {
                                    it.type == selectedCategory
                                } ?: listOf()
                            } else {
                                allExercises
                            }
                        searchViewModel.updateSearchedExercises(exercises)
                    },
                    assignCategoryHandler = assignCategoryHandler
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { traversalIndex = 1f },
                    contentPadding = PaddingValues(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    groupedExercises.forEach {(initial, exercise) ->

                        if (searchQuery.isBlank()) {
                            stickyHeader {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = MaterialTheme.colorScheme.tertiary)) {
                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text = initial.toString().uppercase(Locale.ROOT),
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.onTertiary)
                                }
                            }
                        }

                        items(exercise) { ex ->

                            // if user comes from Manual workout he can select exercise
                            if (canSelectExercise){
                                MultiSelectionContainer(
                                    onCheckedChange = {
                                        if (!selectedExercises.contains(ex)) {
                                            selectedExercises.add(ex)
                                        }
                                        else {
                                            selectedExercises.remove(ex)
                                        }
                                    },
                                    isEnabled = multiSelectionState.isMultiSelectionModeEnabled,
                                    multiSelectionModeStatus = {
                                        multiSelectionState.isMultiSelectionModeEnabled = it
                                    })
                                {

                                    ExerciseItem(
                                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                                        exercise = ex,

                                        onItemClick = { exerciseToAdd ->
                                            searchViewModel.updateSearchedExercises(
                                                searchedExercises.toMutableList().apply {
                                                    if (!this.contains(exerciseToAdd)){
                                                        add(exerciseToAdd)
                                                    }

                                                }
                                            )
                                            selectExerciseHandler(ExerciseEvent.SelectExercise(ex))
                                            navController.navigate(Route.ExerciseDetailsScreen.route)
                                        },
                                        onRemove = {},
                                        mode = if (multiSelectionState.isMultiSelectionModeEnabled) {
                                            ExerciseButtonMode.SELECT
                                        } else {
                                            ExerciseButtonMode.NEXT
                                        }
                                    )
                                }
                            }
                            // if user comes from simple exercise search
                            else {
                                ExerciseItem(
                                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                                    exercise = ex,
                                    onItemClick = { exerciseToAdd ->
                                        searchViewModel.updateSearchedExercises(
                                            searchedExercises.toMutableList().apply {
                                                if (!this.contains(exerciseToAdd)){
                                                    add(exerciseToAdd)
                                                }

                                            }
                                        )
                                        selectExerciseHandler(ExerciseEvent.SelectExercise(ex))
                                        navController.navigate(Route.ExerciseDetailsScreen.route)
                                    },
                                    onRemove = {},
                                    mode = ExerciseButtonMode.NEXT
                                )
                            }


                        }

                    }

                }
            }
        }
    }
}

class MultiSelectionState(initialIsMultiSelectionModeEnabled: Boolean = false) {
    var isMultiSelectionModeEnabled by mutableStateOf(initialIsMultiSelectionModeEnabled)
}

object MultiSelectionStateSaver : Saver<MultiSelectionState, Boolean> {
    override fun restore(value: Boolean): MultiSelectionState {
        return MultiSelectionState(value)
    }

    override fun SaverScope.save(value: MultiSelectionState): Boolean {
        return value.isMultiSelectionModeEnabled
    }
}
@Composable
fun rememberMultiSelectionState(): MultiSelectionState {
    return rememberSaveable(saver = MultiSelectionStateSaver) {
        MultiSelectionState()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseTopBar(message: String, navigationIcon: @Composable () -> Unit, actionIcon: @Composable () -> Unit) {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = message,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
            )
        },
        navigationIcon = { navigationIcon() },
        actions = { actionIcon() },
        colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer)
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MultiSelectionContainer(
    modifier: Modifier = Modifier,
    onCheckedChange : () -> Unit,
    isEnabled: Boolean,
    multiSelectionModeStatus: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    var selected by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    multiSelectionModeStatus(true)
                }
            ),
        contentAlignment = Alignment.CenterEnd
    ) {
        content()
        AnimatedVisibility(
            modifier = Modifier.padding(8.dp),//.background(checkboxBackground),
            visible = isEnabled,
            enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it / 2 }) + fadeOut()
        ) {
            Checkbox(
                enabled = true,
                checked = selected,
                onCheckedChange = {
                    selected = !selected
                    onCheckedChange()

                }

            )
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