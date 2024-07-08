package com.project.gains.presentation.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.gains.data.ExerciseType
import com.project.gains.data.Option
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.generateOptions
import com.project.gains.presentation.Dimension
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.onboarding.components.OnBoardingButton
import com.project.gains.presentation.onboarding.components.PagerIndicator
import com.project.gains.presentation.plan.events.ManagePlanEvent
import kotlinx.coroutines.launch

@Composable
fun LastNewPlanScreen(navController: NavController, createPlanHandler: (ManagePlanEvent.CreatePlan) -> Unit) {
    val selectedExerciseTypes =
        remember { mutableStateListOf<ExerciseType>() } // List to store selected options
    val selectedMetrics =
        remember { mutableStateListOf<TrainingMetricType>() } // List to store selected options
    val selectedMusic = remember { mutableStateOf(false) } // List to store selected options
    val selectedBackup = remember { mutableStateOf(false) } // List to store selected options
    val allOptions = remember { generateOptions() } // List to store selected options
    val options = remember { mutableStateListOf<Option>() } // List to store selected options

    // Function to handle checkbox state change
    fun onOptionSelected(option: Option, isChecked: Boolean) {
        if (isChecked) {
            options.add(option)
        } else {
            options.remove(option)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(20.dp)
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 290.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        navController.navigate(Route.NewPlanScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon"
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Create New Plan",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            item {
                Text(
                    text = "Set the following options and press the generate plan button to create a personalized workout plan based on your needs.",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                Spacer(
                    modifier = Modifier.height(
                        10.dp
                    )
                )
            }
            item {
                Text(
                    text = "Choose if you want to have music while training",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        ) // Optional background for emphasis
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[0],
                    onOptionSelected = { isChecked ->
                        selectedMusic.value = true
                        onOptionSelected(
                            allOptions[0],
                            isChecked
                        )
                    }
                )
            }
            item {
                Text(
                    text = "Choose if you want to have backup on your workout",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[1],
                    onOptionSelected = { isChecked ->
                        selectedBackup.value = true
                        onOptionSelected(
                            allOptions[1],
                            isChecked
                        )
                    }
                )
            }
            item {
                Text(
                    text = "Choose the metrics to track in your progress",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[2],
                    onOptionSelected = { isChecked ->
                        selectedMetrics.add(TrainingMetricType.BPM)
                        onOptionSelected(
                            allOptions[2],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[3],
                    onOptionSelected = { isChecked ->
                        selectedMetrics.add(TrainingMetricType.KCAL)

                        onOptionSelected(
                            allOptions[3],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[4],
                    onOptionSelected = { isChecked ->
                        selectedMetrics.add(TrainingMetricType.DURATION)

                        onOptionSelected(
                            allOptions[4],
                            isChecked
                        )
                    }
                )
            }
            item {
                Text(
                    text = "Choose the muscle groups to include in your plan",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[5],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.CHEST)
                        onOptionSelected(
                            allOptions[5],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[6],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.BACK)
                        onOptionSelected(
                            allOptions[6],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[7],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.SHOULDERS)
                        onOptionSelected(
                            allOptions[7],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[8],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.ARMS)
                        onOptionSelected(
                            allOptions[8],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[9],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.LEGS)
                        onOptionSelected(
                            allOptions[9],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[10],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.CORE)
                        onOptionSelected(
                            allOptions[10],
                            isChecked
                        )
                    }
                )
            }


            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimension.MediumPadding2)
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Route.NewPlanScreen.route)
                        },
                    ) {
                        Text(
                            text = "Back",
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Button(
                        onClick = {
                            createPlanHandler(
                                ManagePlanEvent.CreatePlan(
                                    selectedMetrics,
                                    selectedExerciseTypes,
                                    selectedMusic.value,
                                    selectedBackup.value
                                )
                            )
                            navController.navigate(Route.PlanScreen.route)
                        },
                    ) {
                        Text(
                            text = "GENERATE",
                        )
                    }
                }

            }
        }
    }
}