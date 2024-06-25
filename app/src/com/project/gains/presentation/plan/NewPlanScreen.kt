package com.project.gains.presentation.plan
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.Option
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.TrainingType
import com.project.gains.data.generateOptions

import com.project.gains.presentation.components.BottomNavigationBar

import com.project.gains.presentation.components.OptionCheckbox
import com.project.gains.presentation.components.PeriodPopup
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.TrainingTypePopup
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.navgraph.Route


import com.project.gains.theme.GainsAppTheme



@Composable
fun NewPlanScreen(
    navController: NavController,
    createHandler: (CreateEvent) -> Unit
) {
    val allOptions = remember { generateOptions() } // List to store selected options
    val options = remember { mutableStateListOf<Option>() } // List to store selected options
    var popupVisible by remember { mutableStateOf(false) }
    var selectedPeriod by remember { mutableStateOf(PeriodMetricType.MONTH) }
    var selectedTraining by remember { mutableStateOf(TrainingType.STRENGTH) }
    val selectedExerciseTypes = remember { mutableStateListOf<TrainingType>() } // List to store selected options
    val selectedMetrics = remember { mutableStateListOf<TrainingMetricType>() } // List to store selected options
    val selectedMusic = remember { mutableStateOf(false) } // List to store selected options
    val selectedBackup = remember { mutableStateOf(false) } // List to store selected options

    // Function to handle checkbox state change
    fun onOptionSelected(option: Option, isChecked: Boolean) {
        if (isChecked) {
            options.add(option)
        } else {
            options.remove(option)
        }
    }

    GainsAppTheme {
        Scaffold(
            topBar = { TopBar(navController = navController, message ="New Plane")},
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp),

                    ) {
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    item {
                        Text(
                            text = "Choose if you want to have music while training",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    item {
                        OptionCheckbox(
                            option = allOptions[0],
                            onOptionSelected = { isChecked ->
                                selectedMusic.value = true
                                onOptionSelected(
                                    options[0],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        Text(
                            text = "Choose if you want to have backup on your workout",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier ,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    item {
                        OptionCheckbox(
                            option = allOptions[1],
                            onOptionSelected = { isChecked ->
                                selectedBackup.value = true
                                onOptionSelected(
                                    options[1],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        Text(
                            text = "Choose type of exercises to include",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    item {
                        OptionCheckbox(
                            option = allOptions[2],
                            onOptionSelected = { isChecked ->
                                selectedExerciseTypes.add(TrainingType.STRENGTH)
                                onOptionSelected(
                                    options[2],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        OptionCheckbox(
                            option = allOptions[3],
                            onOptionSelected = { isChecked ->
                                selectedExerciseTypes.add(TrainingType.CALISTHENICS)

                                onOptionSelected(
                                    options[3],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        OptionCheckbox(
                            option = allOptions[4],
                            onOptionSelected = { isChecked ->
                                selectedExerciseTypes.add(TrainingType.RUNNING)

                                onOptionSelected(
                                    options[4],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        OptionCheckbox(
                            option = allOptions[5],
                            onOptionSelected = { isChecked ->
                                selectedExerciseTypes.add(TrainingType.CROSSFIT)

                                onOptionSelected(
                                    options[5],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        Text(
                            text = "Choose the training type",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Text(
                                selectedTraining.name, style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            IconButton(onClick = { popupVisible = true }) {
                                androidx.compose.material3.Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Change Metric"
                                )
                            }
                        }
                    }
                    item {
                        Text(
                            text = "Choose the plan period",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Text(
                                selectedPeriod.name, style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            IconButton(onClick = { popupVisible = true }) {
                                androidx.compose.material3.Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Change Metric"
                                )
                            }
                        }
                    }
                    item {
                        Text(
                            text = "Choose the metrics to track",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    item {
                        OptionCheckbox(
                            option = allOptions[6],
                            onOptionSelected = { isChecked ->
                                selectedMetrics.add(TrainingMetricType.BPM)

                                onOptionSelected(
                                    options[6],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        OptionCheckbox(
                            option = allOptions[7],
                            onOptionSelected = { isChecked ->
                                selectedMetrics.add(TrainingMetricType.KCAL)

                                onOptionSelected(
                                    options[7],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        OptionCheckbox(
                            option = allOptions[8],
                            onOptionSelected = { isChecked ->
                                selectedMetrics.add(TrainingMetricType.INTENSITY)

                                onOptionSelected(
                                    options[8],
                                    isChecked
                                )
                            }
                        )
                        // TODO all
                    }
                    item {
                        OptionCheckbox(
                            option = allOptions[9],
                            onOptionSelected = { isChecked ->
                                selectedMetrics.add(TrainingMetricType.DURATION)

                                onOptionSelected(
                                    options[9],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        OptionCheckbox(
                            option = allOptions[10],
                            onOptionSelected = { isChecked ->
                                selectedMetrics.add(TrainingMetricType.DISTANCE)

                                onOptionSelected(
                                    options[10],
                                    isChecked
                                )
                            }
                        )
                    }
                    item {
                        PeriodPopup(
                            selectedPeriodMap = PeriodMetricType.entries.toMutableList(),
                            popupVisible = popupVisible,
                            onDismiss = { popupVisible = false },
                            onOptionSelected = { period ->
                                selectedPeriod = period
                                popupVisible = false
                            },
                            selectedMetric = selectedPeriod
                        )
                    }
                    item {
                        TrainingTypePopup(
                            popupVisible = popupVisible,
                            onDismiss = { popupVisible = false },
                            onOptionSelected = { training ->
                                selectedTraining = training
                                popupVisible = false
                            },
                            selectedMetric = selectedTraining
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    createHandler(CreateEvent.CreatePlan(options,selectedPeriod,selectedExerciseTypes,selectedMetrics,selectedTraining,selectedMusic.value,selectedBackup.value))
                                    navController.navigate(Route.PlanScreen.route) },
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Icon(Icons.Default.Check, contentDescription = "Add Icon")
                            }
                        }
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewPlanScreenPreview() {
    val navController = rememberNavController()
    NewPlanScreen(
        navController = navController,
        createHandler = {  },
    )
}


