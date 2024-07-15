package com.project.gains.presentation.plan

//noinspection UsingMaterialAndMaterial3Libraries

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.Frequency
import com.project.gains.data.Level
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout
import com.project.gains.presentation.components.getPreviousDestination
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.presentation.workout.events.ManageWorkoutEvent
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.toLowerCaseString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanScreen(
    navController: NavController,
    planViewModel: PlanViewModel,
    selectPlanHandler: (ManagePlanEvent.SelectPlan) -> Unit,
    completionMessage: MutableState<String>

) {
    // Sample list of workouts
    val selectedPlan by planViewModel.selectedPlan.observeAsState()
    val selectedFrequency by planViewModel.selectedFrequency.observeAsState()
    val selectedLevel by planViewModel.selectedLvl.observeAsState()
    val selectedTraining  by planViewModel.selectedTrainingType.observeAsState()

    val plans by planViewModel.plans.observeAsState()
    var expanded by remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(true) }




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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                        ) {

                            OutlinedTextField(
                                value = selectedPlan?.name.toString(),
                                textStyle = MaterialTheme.typography.titleLarge,
                                label = {Text("Your plans")},
                                singleLine = true,
                                onValueChange = {},
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )

                                },
                                readOnly = true,
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary, // Set the contour color when focused
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary // Set the contour color when not focused
                                )

                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false})
                            {
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

                        Spacer(modifier = Modifier.padding(10.dp))

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
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.5.dp,
                    color = Color.LightGray
                )
                AlignedTextItem(label = "Type", value = toLowerCaseString(selectedTraining.toString()))
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.5.dp,
                    color = Color.LightGray
                )
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
            text = "Workout days",
            style = MaterialTheme.typography.headlineSmall,
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

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
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
                            text = "${toLowerCaseString(workout.workoutDay.toString())} Workout",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
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
        planViewModel = planViewModel,
        selectPlanHandler = {},
        completionMessage = mutableStateOf("")
    )
}