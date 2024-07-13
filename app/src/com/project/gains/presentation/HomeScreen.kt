package com.project.gains.presentation

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
    paddingValues: PaddingValues,
    exerciseViewModel: ExerciseViewModel,
    selectWorkoutHandler: (ManageWorkoutEvent.SelectWorkout)->Unit,selectExerciseHandler: (ExerciseEvent.SelectExercise)->Unit,completionMessage:MutableState<String>
) {

    val openPopup = remember { mutableStateOf(false) }
    val favouriteExercises by exerciseViewModel.favouriteExercises.observeAsState()
    val favouriteWorkouts by workoutViewModel.favouriteWorkouts.observeAsState()
    val workouts by workoutViewModel.workouts.observeAsState()
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value && getPreviousDestination(navController = navController) == Route.AddManualWorkoutScreen.route){
        completionMessage.value = "You have successfully created your workout!"
        showDialog.value = false
    }

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

                item {
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

                item {
                    HorizontalScrollScreenWorkout(navController, "daily workouts", items2 = workouts!!.toList(),selectWorkoutHandler = selectWorkoutHandler)
                }

                item {
                    HorizontalScrollScreenExercise(navController, "favourite exercises", items = favouriteExercises!!.toList(),selectExerciseHandler = selectExerciseHandler,)
                }

                item {
                    HorizontalScrollScreenWorkoutFavourites(navController, "favourite workouts", items2 = favouriteWorkouts!!.toList(), selectWorkoutHandler = selectWorkoutHandler)
                }

                item {
                    // Notification card
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
            .height(350.dp)
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

                Text(
                    text = "Your $title",
                    style = MaterialTheme.typography.labelLarge, // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp) // Add padding here for space between text and border
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    state = rememberLazyListState()
                ) {
                    // by default, there is a card that suggests you to add a new exercise
                    if (items.isEmpty()) {

                        val onClick = { navController.navigate(Route.HomeSearchScreen.route) }
                        item {
                            ElevatedCardItem(
                                onClick = onClick,
                                imageResId = R.drawable.logo,
                                title = "Like an exercise",
                                buttonEnabled = true,
                                buttonText = "Add exercise to favourites"
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
                                buttonText = "More details"
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HorizontalScrollScreenWorkout(navController: NavController, title: String, items2:List<Workout> = listOf(),selectWorkoutHandler: (ManageWorkoutEvent.SelectWorkout)->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(350.dp)
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
                Text(
                    text = "Your $title",
                    style = MaterialTheme.typography.labelLarge, // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp) // Add padding here for space between text and border
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = rememberLazyListState()
                ) {

                    if (items2.isEmpty()) {
                        val onClick = {
                            navController.navigate(Route.NewPlanScreen.route)
                        }
                        item {
                            ElevatedCardItem(
                                onClick = onClick,
                                imageResId = R.drawable.logo,
                                title = "Add a new workout",
                                buttonEnabled = true,
                                buttonText = "Add workout"
                            )
                        }
                    }

                    if (items2.isNotEmpty()) {
                        itemsIndexed(items2) { _, item ->
                            if (currentWeekday() == item.workoutDay.ordinal + 1) {

                                val onClick = {
                                    selectWorkoutHandler(ManageWorkoutEvent.SelectWorkout(item))
                                    navController.navigate(Route.PlanScreen.route)
                                }
                                ElevatedCardItem(
                                    onClick = onClick,
                                    imageResId = R.drawable.logo,
                                    title = item.name,
                                    buttonEnabled = true,
                                    buttonText = "More details"
                                )
                            } else {

                                ElevatedCardItem(
                                    onClick = {},
                                    imageResId = R.drawable.logo,
                                    title = "There is no workout for you today",
                                    buttonEnabled = false,
                                    buttonText = ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HorizontalScrollScreenWorkoutFavourites(navController: NavController, title: String, items2:List<Workout> = listOf(),selectWorkoutHandler: (ManageWorkoutEvent.SelectWorkout)->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(350.dp)
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
                Text(
                    text = "Your $title",
                    style = MaterialTheme.typography.labelLarge, // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp) // Add padding here for space between text and border
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) // Additional padding inside the background and border

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = rememberLazyListState()
                ) {

                    if (items2.isEmpty()) {
                        val onClick = {
                            navController.navigate(Route.PlanScreen.route)
                        }
                        item {
                            ElevatedCardItem(
                                onClick = onClick,
                                imageResId = R.drawable.logo,
                                title = "Like a workout",
                                buttonEnabled = true,
                                buttonText = "Add workout to favourites"
                            )
                        }
                    }

                    if (items2.isNotEmpty()) {
                        itemsIndexed(items2) { _, item ->

                            val onClick = {
                                selectWorkoutHandler(ManageWorkoutEvent.SelectWorkout(item))
                                navController.navigate(Route.WorkoutScreen.route)
                            }
                            ElevatedCardItem(
                                onClick = { onClick() },
                                imageResId = R.drawable.logo,
                                title = item.name,
                                buttonEnabled = true,
                                buttonText = "More details"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ElevatedCardItem(onClick: () -> Unit, imageResId: Int, title: String, buttonEnabled: Boolean, buttonText: String) {
    val configuration = LocalConfiguration.current
    val screenWith = configuration.screenWidthDp.dp

    ElevatedCard(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .width(width = screenWith - 64.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(8.dp)
            )
            if (buttonEnabled) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onClick() },
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                ) {
                    Text(buttonText)
                }
            }
        }
    }
}