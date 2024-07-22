package com.project.gains.presentation.plan

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.ExerciseType
import com.project.gains.data.Option
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.TrainingType
import com.project.gains.data.generateOptions
import com.project.gains.data.generateRandomPlan
import com.project.gains.presentation.Dimension
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.presentation.workout.events.ManageWorkoutEvent

@Composable
fun LastNewPlanScreen(
    navController: NavController,
    createPlanHandler: (ManagePlanEvent.CreatePlan) -> Unit,
    addWorkoutHandler: (ManageWorkoutEvent.CreateWorkout) -> Unit,
    planViewModel: PlanViewModel,
    completionMessage: MutableState<String>
) {
    val selectedExerciseTypes = remember { mutableStateListOf<ExerciseType>() }
    val selectedMetrics = remember { mutableStateListOf<TrainingMetricType>() }
    val selectedMusic = remember { mutableStateOf(false) }
    val selectedBackup = remember { mutableStateOf(false) }
    val allOptions = remember { generateOptions() }
    val options = remember { mutableStateListOf<Option>().apply { addAll(allOptions) } }
    val showDialog = remember { mutableStateOf(false) }

    val selectedFrequency = planViewModel.selectedFrequency.observeAsState()
    val selectedTrainingType = planViewModel.selectedTrainingType.observeAsState()

    // Function to handle checkbox state change
    fun onOptionSelected(option: Option, isChecked: Boolean) {

        if (isChecked) {
            options.add(option)
        } else {
            options.remove(option)
        }
    }

    if (showDialog.value ){

        FeedbackAlertDialog(
            onDismissRequest = {   },
            onConfirm = {   showDialog.value=false
                navController.navigate(Route.PlansProgressesScreen.route)
            },
            title ="Plan Created!",
            text ="You have successfully created your plan!",
            icon = Icons.Default.Check,
            dismiss = false
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, start = 30.dp, end = 30.dp)
        ) {
            item {
                Text(
                    text = "Additional preferences",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                Text(
                    text = "Choose if you want to have music while training",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            border = BorderStroke(3.dp, MaterialTheme.colorScheme.onSurface),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                )
            }

            item {
                OptionCheckbox(
                    option = allOptions[0],
                    isChecked = selectedMusic.value,
                    onCheckedChange = {
                        selectedMusic.value = it
                        onOptionSelected(allOptions[0], it)
                    }
                )
            }

            item {
                Text(
                    text = "Choose if you want to have backup on your workout",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            border = BorderStroke(3.dp, MaterialTheme.colorScheme.onSurface),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                )
            }

            item {
                OptionCheckbox(
                    option = allOptions[1],
                    isChecked = selectedBackup.value,
                    onCheckedChange = {
                        selectedBackup.value = it
                        onOptionSelected(allOptions[1], it)
                    }
                )
            }

            item {
                Text(
                    text = "Choose the metrics to track in your progress",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            border = BorderStroke(3.dp, MaterialTheme.colorScheme.onSurface),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                )
            }

            item {
                OptionCheckbox(
                    option = allOptions[2],
                    isChecked = selectedMetrics.contains(TrainingMetricType.BPM),
                    onCheckedChange = { isChecked ->
                        if (isChecked) selectedMetrics.add(TrainingMetricType.BPM)
                        else selectedMetrics.remove(TrainingMetricType.BPM)
                        onOptionSelected(allOptions[2], isChecked)
                    }
                )
            }

            item {
                OptionCheckbox(
                    option = allOptions[3],
                    isChecked = selectedMetrics.contains(TrainingMetricType.KCAL),
                    onCheckedChange = { isChecked ->
                        if (isChecked) selectedMetrics.add(TrainingMetricType.KCAL)
                        else selectedMetrics.remove(TrainingMetricType.KCAL)
                        onOptionSelected(allOptions[3], isChecked)
                    }
                )
            }

            item {
                OptionCheckbox(
                    option = allOptions[4],
                    isChecked = selectedMetrics.contains(TrainingMetricType.FREQUENCY),
                    onCheckedChange = { isChecked ->
                        if (isChecked) selectedMetrics.add(TrainingMetricType.FREQUENCY)
                        else selectedMetrics.remove(TrainingMetricType.FREQUENCY)
                        onOptionSelected(allOptions[4], isChecked)
                    }
                )
            }

            item {
                Text(
                    text = "Choose the muscle groups to include in your plan",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            border = BorderStroke(3.dp, MaterialTheme.colorScheme.onSurface),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                )
            }

            item {
                OptionCheckbox(
                    option = allOptions[5],
                    isChecked = selectedExerciseTypes.contains(ExerciseType.CHEST),
                    onCheckedChange = { isChecked ->
                        if (isChecked) selectedExerciseTypes.add(ExerciseType.CHEST)
                        else selectedExerciseTypes.remove(ExerciseType.CHEST)
                        onOptionSelected(allOptions[5], isChecked)
                    }
                )
            }

            item {
                OptionCheckbox(
                    option = allOptions[6],
                    isChecked = selectedExerciseTypes.contains(ExerciseType.BACK),
                    onCheckedChange = { isChecked ->
                        if (isChecked) selectedExerciseTypes.add(ExerciseType.BACK)
                        else selectedExerciseTypes.remove(ExerciseType.BACK)
                        onOptionSelected(allOptions[6], isChecked)
                    }
                )
            }

            item {
                OptionCheckbox(
                    option = allOptions[7],
                    isChecked = selectedExerciseTypes.contains(ExerciseType.SHOULDERS),
                    onCheckedChange = { isChecked ->
                        if (isChecked) selectedExerciseTypes.add(ExerciseType.SHOULDERS)
                        else selectedExerciseTypes.remove(ExerciseType.SHOULDERS)
                        onOptionSelected(allOptions[7], isChecked)
                    }
                )
            }

            item {
                OptionCheckbox(
                    option = allOptions[8],
                    isChecked = selectedExerciseTypes.contains(ExerciseType.ARMS),
                    onCheckedChange = { isChecked ->
                        if (isChecked) selectedExerciseTypes.add(ExerciseType.ARMS)
                        else selectedExerciseTypes.remove(ExerciseType.ARMS)
                        onOptionSelected(allOptions[8], isChecked)
                    }
                )
            }

            item {
                OptionCheckbox(
                    option = allOptions[9],
                    isChecked = selectedExerciseTypes.contains(ExerciseType.LEGS),
                    onCheckedChange = { isChecked ->
                        if (isChecked) selectedExerciseTypes.add(ExerciseType.LEGS)
                        else selectedExerciseTypes.remove(ExerciseType.LEGS)
                        onOptionSelected(allOptions[9], isChecked)
                    }
                )
            }

            item {
                OptionCheckbox(
                    option = allOptions[10],
                    isChecked = selectedExerciseTypes.contains(ExerciseType.CORE),
                    onCheckedChange = { isChecked ->
                        if (isChecked) selectedExerciseTypes.add(ExerciseType.CORE)
                        else selectedExerciseTypes.remove(ExerciseType.CORE)
                        onOptionSelected(allOptions[10], isChecked)
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
                ) {}
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }

        }


        Box(
            modifier = Modifier
                .fillMaxHeight(0.125f)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.Center)
                    .height(60.dp),
                onClick = {
                    val workouts = generateRandomPlan(
                        trainingType = selectedTrainingType.value ?: TrainingType.STRENGTH,
                        muscleGroups = selectedExerciseTypes,
                        numberOfWorkouts = (selectedFrequency.value?.ordinal ?: 1) + 2
                    )

                    createPlanHandler(
                        ManagePlanEvent.CreatePlan(
                            workouts = workouts,
                            selectedMusic = selectedMusic.value,
                            selectedBackup = selectedBackup.value
                        )
                    )

                    workouts.forEach { workout ->
                        addWorkoutHandler(
                            ManageWorkoutEvent.CreateWorkout(workout)
                        )
                    }

                    showDialog.value = true

                },
            ) {
                Text(text = "Generate")
            }
        }
    }
}


@Composable
fun OptionCheckbox(
    option: Option,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isChecked) {
            MaterialTheme.colorScheme.onTertiary
        } else {
            MaterialTheme.colorScheme.primaryContainer
        },
        border = BorderStroke(
            width = 0.5.dp,
            color = if (isChecked) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }),
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(onClick = { onCheckedChange(!isChecked) })
            .padding(bottom = 10.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = option.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            Box(Modifier.padding(8.dp)) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { onCheckedChange(it) },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                        uncheckedColor = MaterialTheme.colorScheme.primary,
                        checkedColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}



@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun LastNewPlanScreenPreview() {
    val navController = rememberNavController()
    val planViewModel: PlanViewModel = hiltViewModel()
    LastNewPlanScreen(
        navController = navController,
        createPlanHandler = {},
        addWorkoutHandler = {},
        planViewModel = planViewModel,
        completionMessage = mutableStateOf("")
    )
}



