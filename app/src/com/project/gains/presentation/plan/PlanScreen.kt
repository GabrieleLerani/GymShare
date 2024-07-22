package com.project.gains.presentation.plan

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.Frequency
import com.project.gains.data.Level
import com.project.gains.data.Plan
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout
import com.project.gains.presentation.components.PlanTopBar
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.presentation.workout.events.ManageWorkoutEvent
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.toLowerCaseString

@Composable
fun PlanScreen(
    navController: NavController,
    planViewModel: PlanViewModel,
    planId: Int,
    selectPlanHandler: (ManagePlanEvent.SelectPlan) -> Unit
) {
    val plans by planViewModel.plans.observeAsState()
    val selectedPlan by planViewModel.selectedPlan.observeAsState()
    var plan = Plan(1, "Default Plan", mutableListOf(), Frequency.TWO, Level.BEGINNER, TrainingType.STRENGTH)

    plans?.forEach {
        if (it.id == planId) {
            plan = it
        }
    }

    GainsAppTheme {
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            PlanTopBar(message = plan.name) {
                IconButton(onClick = {
                    navController.navigate(Route.PlansProgressesScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Icon"
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            WorkoutHeader(plan.level, plan.training, plan.frequency)

                            Spacer(modifier = Modifier.height(30.dp))

                            WorkoutDaysList(plan.workouts) {
                                navController.navigate(Route.WorkoutScreen.route)
                            }
                        }
                    }
                }
                val onClick = {
                    selectPlanHandler(ManagePlanEvent.SelectPlan(plan = plan))
                }
                SelectButton(
                    onClick = { onClick() },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    isSelected = selectedPlan?.id == plan.id
                )
            }
        }
    }
}

@Composable
fun SelectButton(onClick: () -> Unit, modifier: Modifier, isSelected: Boolean){
    FilledTonalButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Select Plan",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Current Plan")
            } else {
                Icon(
                    imageVector = Icons.Default.CheckCircleOutline,
                    contentDescription = "Select Plan",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Set it to current plan")
            }
        }

    }
}

@Composable
fun WorkoutHeader(selectedLevel: Level?, selectedTraining: TrainingType?, selectedFrequency: Frequency?) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Plan details",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {

                val frequency = toLowerCaseString(selectedFrequency.toString())

                AlignedTextItem(label = "Level", value = toLowerCaseString(selectedLevel.toString()))
                AlignedTextItem(label = "Type", value = toLowerCaseString(selectedTraining.toString()))
                AlignedTextItem(label = "Frequency", value = "$frequency per week")
            }
        }
    }
}

@Composable
fun AlignedTextItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun WorkoutDaysList(workouts: MutableList<Workout>, selectHandler: (ManageWorkoutEvent.SelectWorkout)->Unit,) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Workouts",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        workouts.forEachIndexed { _, workout ->
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
                        horizontalArrangement = Arrangement.Center
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
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .padding(start = 16.dp, bottom = 8.dp)
                        ) {
                            Text(
                                text = "Modify workout",
                            )
                        }
                        Button(
                            onClick = { selectHandler(ManageWorkoutEvent.SelectWorkout(workout)) },
                            modifier = Modifier
                                .padding(end = 16.dp, bottom = 8.dp)
                        ) {
                            Text(
                                text = "Workout details"
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PlanScreenPreview() {
    val navController = rememberNavController()
    val planViewModel: PlanViewModel = hiltViewModel()
    PlanScreen(
        navController = navController,
        planId = 1,
        planViewModel = planViewModel,
        selectPlanHandler = {}
    )
}