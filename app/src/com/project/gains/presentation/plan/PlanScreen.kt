package com.project.gains.presentation.plan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R
import com.project.gains.data.Frequency
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout

import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.presentation.workout.events.ManageWorkoutEvent

import com.project.gains.theme.GainsAppTheme

@Composable
fun PlanScreen(
    navController: NavController,
    planViewModel: PlanViewModel,
    selectPlanHandler: (ManagePlanEvent.SelectPlan) -> Unit
) {
    // Sample list of workouts
    val selectedPlan by planViewModel.selectedPlan.observeAsState()
    val selectedFrequency by planViewModel.selectedFrequency.observeAsState()
    val selectedLevel by planViewModel.selectedLvl.observeAsState()
    val selectedPeriod  by planViewModel.selectedPeriod.observeAsState()
    val selectedTraining  by planViewModel.selectedTrainingType.observeAsState()

    val plans by planViewModel.plans.observeAsState()
    var expanded by remember { mutableStateOf(false) }

    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center

            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(16.dp)
                                )
                                .border(
                                    border = BorderStroke(
                                        width = 3.dp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    ), shape = RoundedCornerShape(16.dp)
                                )

                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Your current plan: ${selectedPlan?.name.toString()}",
                                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                    // Take up the remaining space
                                )
                                IconButton(
                                    onClick = { expanded = !expanded }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Dropdown Icon"
                                    )
                                }
                            }
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .background(
                                    shape = RoundedCornerShape(16.dp),
                                    color = MaterialTheme.colorScheme.surface
                                )
                                .padding(10.dp) // Padding to match the Text above
                        ) {
                            plans?.forEach { plan ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            plan.name,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    },
                                    onClick = {
                                        selectPlanHandler(ManagePlanEvent.SelectPlan(plan))
                                        expanded = false
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
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
                                        .padding(16.dp) // Inner padding for the item
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                            }
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        WorkoutHeader(selectedLevel, selectedPeriod, selectedTraining,selectedFrequency)
                        Spacer(modifier = Modifier.height(16.dp))
                        WorkoutDaysList(selectedPlan?.workouts ?: mutableListOf()) {
                            navController.navigate(Route.WorkoutScreen.route)
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun WorkoutHeader(selectedLevel: Level?, selectedPeriod: PeriodMetricType?, selectedTraining: TrainingType?,selectedFrequency:Frequency?) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.pexels1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "GENERAL • $selectedTraining • TRAINING",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "For gym • $selectedLevel",
            fontSize = 16.sp,

            )
        Text(
            text = "For period • $selectedPeriod",
            fontSize = 14.sp,
        )
        Text(
            text = "Workouts done: 0",
            fontSize = 14.sp,
        )
        Text(
            text = "Workouts week frequency • $selectedFrequency",
            fontSize = 14.sp,
        )
    }
}

@Composable
fun WorkoutDaysList(workouts: MutableList<Workout>, selectHandler: (ManageWorkoutEvent.SelectWorkout)->Unit,) {


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Workout days",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        workouts.forEachIndexed { index,workout ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clickable {
                        selectHandler(ManageWorkoutEvent.SelectWorkout(workout))
                         },
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (index == 0) Icons.Default.Circle else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (index == 0) Color.Red else Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "workout day $index",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = workout.workoutDay.toString(),
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanScreenPreview() {
    val navController = rememberNavController()
    val planViewModel: PlanViewModel = hiltViewModel()
    PlanScreen(
        navController = navController,
        planViewModel = planViewModel,
        selectPlanHandler = {}
    )
}