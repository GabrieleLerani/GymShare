package com.project.gains.presentation

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.Workout
import com.project.gains.presentation.components.SearchAppBar
import com.project.gains.presentation.components.getPreviousDestination
import com.project.gains.presentation.exercises.ExerciseViewModel
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.presentation.workout.events.ManageWorkoutEvent
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.currentWeekday

@Composable
fun HomeScreen(
    navController: NavController,
    workoutViewModel: WorkoutViewModel,
    exerciseViewModel: ExerciseViewModel,
    selectWorkoutHandler: (ManageWorkoutEvent.SelectWorkout)->Unit,
    selectExerciseHandler: (ExerciseEvent.SelectExercise)->Unit
) {

    val openPopup = remember { mutableStateOf(false) }
    val favouriteExercises by exerciseViewModel.favouriteExercises.observeAsState()
    val workouts by workoutViewModel.workouts.observeAsState()

    CustomBackHandler(
        onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
            ?: return,
        enabled = true
    ) {
        openPopup.value = true
    }

    GainsAppTheme {
        Box(
            modifier = Modifier
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                // TODO exercise search bar must be moved into the search bar in explore feed
                /*
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    SearchAppBar(
                        value = "",
                        placeholder = "Search exercises here...",
                        onValueChange = {},
                        onCloseClicked = {},
                        onSearchClicked = {},
                        onClick = {
                            navController.navigate(Route.HomeSearchScreen.route)
                        },
                        enabled = false
                    )
                }
                */
                item {
                    HorizontalScrollScreenWorkout(navController, "Your workouts", items2 = workouts!!, selectWorkoutHandler = selectWorkoutHandler)
                }

                item {
                    HorizontalScrollScreenExercise(navController, "Your favourite exercises", items = favouriteExercises!!.toList(),selectExerciseHandler = selectExerciseHandler,)
                }

            }
        }
    }
}

@Composable
fun CustomBackHandler(
    onBackPressedDispatcher: OnBackPressedDispatcher,
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.addCallback(backCallback)

    DisposableEffect(onBackPressedDispatcher) {
        backCallback.isEnabled = enabled
        onDispose {
            backCallback.remove()
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HorizontalScrollScreenExercise(navController: NavController, title: String, items: List<Exercise> = listOf(),selectExerciseHandler: (ExerciseEvent.SelectExercise)->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(250.dp)
    ) {
        // BowWithConstraints will provide the maxWidth used below
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TextItem(title = title)

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    state = rememberLazyListState()
                ) {
                    // by default, there is a card that suggests you to add a new exercise

                    if (items.isEmpty()) {
                        item {
                            val onClick = { navController.navigate(Route.HomeSearchScreen.route) }

                            ElevatedCardItem(
                                onClick = onClick,
                                imageResId = R.drawable.logo,
                                title = "Like an exercise",
                                buttonEnabled = true,
                                buttonText = "Look for exercises",
                                description = "Look for exercises and press the like button to save them, so you can have them always at hand"
                            )
                        }
                    }

                    itemsIndexed(items) { _, item ->
                        item.gifResId?.let {

                            val onClick = {
                                selectExerciseHandler(ExerciseEvent.SelectExercise(item))
                                navController.navigate(Route.ExerciseDetailsScreen.route)
                            }
                            ElevatedCardItem(
                                onClick = onClick,
                                imageResId = it,
                                title = item.name,
                                buttonEnabled = true,
                                buttonText = "More details",
                                description = item.description.toString()
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HorizontalScrollScreenWorkout(navController: NavController, title: String, items2: List<Workout> = listOf(), selectWorkoutHandler: (ManageWorkoutEvent.SelectWorkout)->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(250.dp)
    ) {
        // BowWithConstraints will provide the maxWidth used below
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TextItem(title = title)

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = rememberLazyListState()
                ) {

                    if (items2.isEmpty()) {
                        item {
                            val onClick = { navController.navigate(Route.NewPlanScreen.route) }

                            ElevatedCardItem(
                                onClick = onClick,
                                imageResId = R.drawable.logo,
                                title = "Add new workout",
                                buttonEnabled = true,
                                buttonText = "Add workout",
                                description = "Add a new workout, either by generating a plan automatically or by adding it manually"
                            )
                        }
                    }

                    itemsIndexed(items2) { _, item ->
                        val onClick = {
                            selectWorkoutHandler(ManageWorkoutEvent.SelectWorkout(item))
                            navController.navigate(Route.WorkoutScreen.route)
                        }
                        var description = "Exercises:\n"
                        item.exercises.forEachIndexed { index, exercise ->
                            description += "${index + 1}. " + exercise.name + "\n"
                        }
                        ElevatedCardItem(
                            onClick = onClick,
                            imageResId = R.drawable.logo,
                            title = item.name,
                            buttonEnabled = true,
                            buttonText = "More details",
                            description = description
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextItem(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium, // Make it bigger and bold
        color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp, bottom = 15.dp)
    )
}

@Composable
fun ElevatedCardItem(onClick: () -> Unit, imageResId: Int, title: String, buttonEnabled: Boolean, buttonText: String, description: String) {
    val configuration = LocalConfiguration.current
    val screenWith = configuration.screenWidthDp.dp

    ElevatedCard(
        elevation = CardDefaults.cardElevation(16.dp),
        modifier = Modifier
            .padding(start = 16.dp)
            .width(width = screenWith - 64.dp)
            .fillMaxHeight()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .weight(0.4f)
                    .fillMaxHeight()
            )
            Column (
                modifier = Modifier
                    .weight(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(bottom = 8.dp, end = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    modifier = Modifier
                        .padding(bottom = 16.dp, end = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                if (buttonEnabled) {
                    Button(
                        onClick = { onClick() },
                        modifier = Modifier
                            .padding(bottom = 16.dp, end = 16.dp),
                    ) {
                        Text(
                            text = buttonText
                        )
                    }
                }
            }
        }
    }
}