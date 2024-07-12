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
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CardDefaults
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
                verticalArrangement = Arrangement.Top

            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        //modifier = Modifier.padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(5.dp)
                                ),

                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = selectedPlan?.name.toString(),
                                    style = MaterialTheme.typography.headlineSmall,
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
                                    //shape = RoundedCornerShape(16.dp),
                                    color = MaterialTheme.colorScheme.surface
                                )
                                .padding(10.dp) // Padding to match the Text above
                        ) {
                            plans?.forEach { plan ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            plan.name,
                                        )
                                    },
                                    onClick = {
                                        selectPlanHandler(ManagePlanEvent.SelectPlan(plan))
                                        expanded = false
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()

                                )

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
                        WorkoutHeader(selectedLevel, selectedTraining, selectedFrequency)
                        Spacer(modifier = Modifier.height(30.dp))
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
fun WorkoutHeader(selectedLevel: Level?, selectedTraining: TrainingType?, selectedFrequency:Frequency?) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        //.padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Workout settings",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(),

            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {

                AlignedTextItem(label = "Level", value = selectedLevel.toString())
                Divider(color = Color.LightGray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))
                AlignedTextItem(label = "Type", value = selectedTraining.toString())
                Divider(color = Color.LightGray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))
                AlignedTextItem(label = "Frequency", value = "$selectedFrequency PER WEEK")
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
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
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
                        imageVector = if (index == 0) Icons.Default.FitnessCenter else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (index == 0) MaterialTheme.colorScheme.primary else Color.Gray,
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