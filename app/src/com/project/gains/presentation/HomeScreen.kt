package com.project.gains.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.Workout
import com.project.gains.presentation.exercises.ExerciseViewModel
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManageExercises
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
    selectWorkoutHandler: (ManageWorkoutEvent.SelectWorkout)->Unit,selectExerciseHandler: (ExerciseEvent.SelectExercise)->Unit
) {

    val openPopup = remember { mutableStateOf(false) }
    val favouriteExercises by exerciseViewModel.favouriteExercises.observeAsState()
    val favouriteWorkouts by workoutViewModel.favouriteWorkouts.observeAsState()
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
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top

            ) {

                val weekday = currentWeekday()


                item {
                    HorizontalScrollScreen(navController, "daily workouts", items2 = workouts!!.toList(), selectExerciseHandler = selectExerciseHandler, selectWorkoutHandler = selectWorkoutHandler)
                }

                item {
                    HorizontalScrollScreen(navController, "favourite exercises", items = favouriteExercises!!.toList(),selectExerciseHandler = selectExerciseHandler, selectWorkoutHandler = selectWorkoutHandler)
                }

                item {
                    HorizontalScrollScreen(navController, "favourite workouts", items2 = favouriteWorkouts!!.toList(),selectExerciseHandler = selectExerciseHandler, selectWorkoutHandler = selectWorkoutHandler)
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
fun HorizontalScrollScreen(navController: NavController, title: String, items: List<Exercise> = listOf(), items2:List<Workout> = listOf(),selectWorkoutHandler: (ManageWorkoutEvent.SelectWorkout)->Unit,selectExerciseHandler: (ExerciseEvent.SelectExercise)->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(180.dp)
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
                if (items.isEmpty() && items2.isEmpty()) {
                    Text(
                        text = "No $title",
                        style = MaterialTheme.typography.labelLarge, // Make it bigger and bold
                        color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp) // Add padding here for space between text and border
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                border = BorderStroke(
                                    width = 3.dp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ), shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp) // Additional padding inside the background and border
                    )
                } else {
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
                            .border(
                                border = BorderStroke(
                                    width = 3.dp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ), shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp) // Additional padding inside the background and border
                    )


                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = rememberLazyListState()
                    ) {
                        if (items.isEmpty() && items2.isNotEmpty()){
                            itemsIndexed(items2) {  _, item ->
                                    Card(imageResId = R.drawable.logo, title = item.name) {
                                        selectWorkoutHandler(ManageWorkoutEvent.SelectWorkout(item))
                                        navController.navigate(Route.WorkoutScreen.route)
                                    }

                            }
                        }else if (items2.isEmpty() && items.isNotEmpty()){
                            itemsIndexed(items) {  _, item ->
                                item.gifResId?.let {
                                    Card(imageResId = it, title = item.name) {
                                        selectExerciseHandler(ExerciseEvent.SelectExercise(item))
                                        navController.navigate(Route.ExerciseDetailsScreen.route)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Card(imageResId: Int, title: String, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .clickable {
                onItemClick()
            }
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 20.dp)
                .clip(RoundedCornerShape(16.dp)) // Clip to the rounded corners
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}