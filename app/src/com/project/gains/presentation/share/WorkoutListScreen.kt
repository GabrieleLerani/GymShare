package com.project.gains.presentation.share


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.gains.data.Workout
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.exercises.MultiSelectionContainer
import com.project.gains.presentation.exercises.MultiSelectionState
import com.project.gains.presentation.exercises.rememberMultiSelectionState
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.PlanViewModel
import com.project.gains.presentation.workout.events.ManageWorkoutEvent
import com.project.gains.util.toLowerCaseString


@Composable
fun WorkoutListScreen(
    navController : NavController,
    planViewModel: PlanViewModel,
) {
    val plans by planViewModel.plans.observeAsState()
    val multiSelectionState = rememberMultiSelectionState()
    val selectedWorkouts = remember { mutableStateListOf<Workout>() }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {

            // top app bar
            TopBar(
                message = "Exercises",
                navigationIcon = {
                    if (multiSelectionState.isMultiSelectionModeEnabled){
                        IconButton(
                            onClick = {
                                multiSelectionState.isMultiSelectionModeEnabled = false
                            }) {
                            Icon(Icons.Default.Close, contentDescription = "exit from workouts selection")
                        }
                    }
                    else {
                        BackButton {
                            navController.popBackStack()
                        }
                    }
                },
                actionIcon = {
                    if (multiSelectionState.isMultiSelectionModeEnabled && selectedWorkouts.size > 0){
                        IconButton(
                            onClick = {
                                // TODO save all workouts
                                planViewModel.updateSelectedWorkouts(selectedWorkouts)

                                // clear the list
                                selectedWorkouts.clear()
                                multiSelectionState.isMultiSelectionModeEnabled = false

                                navController.popBackStack()


                            }) {
                            Icon(Icons.Default.Check, contentDescription = "select all exercises")
                        }
                    }

                }
            )


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                plans?.forEach {
                    item {
                        WorkoutSelectionList(
                            title = it.name,
                            workouts = it.workouts,
                            multiSelectionState = multiSelectionState,
                            selectHandler = {navController.navigate(Route.WorkoutListScreen.route)},
                            selectedWorkouts = selectedWorkouts
                        )
                    }
                }
            }

        }



    }

}

@Composable
fun WorkoutSelectionList(
    title : String,
    workouts: MutableList<Workout>,
    selectHandler: (ManageWorkoutEvent.SelectWorkout)->Unit,
    multiSelectionState : MultiSelectionState,
    selectedWorkouts : MutableList<Workout>
    ) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        workouts.forEachIndexed { _, workout ->

            // allow item multi selection
            MultiSelectionContainer(
                onCheckedChange = {
                    if (!selectedWorkouts.contains(workout)) {
                        selectedWorkouts.add(workout)
                    }
                    else {
                        selectedWorkouts.remove(workout)
                    }
                },
                isEnabled = multiSelectionState.isMultiSelectionModeEnabled,
                multiSelectionModeStatus = {
                    multiSelectionState.isMultiSelectionModeEnabled = it
                }

            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(16.dp)
                        ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Day: ${toLowerCaseString(workout.workoutDay.toString())}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Name: ${workout.name}",
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }

            }


        }
    }
}