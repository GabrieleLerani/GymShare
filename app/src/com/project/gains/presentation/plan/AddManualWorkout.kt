package com.project.gains.presentation.plan

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.gains.data.Exercise
import com.project.gains.data.Weekdays
import com.project.gains.data.Workout
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManageExercises
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.presentation.workout.events.ManageWorkoutEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddManualWorkout(
    navController: NavController,
    manualWorkoutViewModel: ManualWorkoutViewModel,
    addNameHandler: (ManageExercises.SelectWorkoutStored) -> Unit,
    selectExerciseHandler: (ExerciseEvent.SelectIsToAdd) -> Unit,
    deleteExerciseHandler: (ManageExercises.DeleteExercise) -> Unit,
    createWorkoutHandler: (ManageWorkoutEvent.CreateWorkout) -> Unit
) {
    var workoutTitle by remember { mutableStateOf(TextFieldValue("")) }
    var workoutDay by remember { mutableStateOf(Weekdays.MONDAY) }
    val showDialog = remember { mutableStateOf(false) }

    val selectedExercises by manualWorkoutViewModel.selectedExercises.observeAsState()
    val workoutTitleStored by manualWorkoutViewModel.workoutTitle.observeAsState()
    val removedExercises = remember {
        mutableStateOf(listOf<Exercise>())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface,
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {

            item {
                Text(
                    text = "Create your workout!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "Manually add each exercise to your workout day",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                (if(workoutTitle.text=="") workoutTitleStored else workoutTitle)?.let {
                    OutlinedTextField(
                        value = it,
                        onValueChange = { newValue ->
                            workoutTitle = newValue
                            addNameHandler(ManageExercises.SelectWorkoutStored(newValue))
                        },
                        label = {
                            Text(
                                 if (workoutTitle.text=="") "Enter workout name..." else "",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        },
                        shape = RoundedCornerShape(size = 20.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface), // Set the text color to white

                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.onTertiary,
                            focusedContainerColor = MaterialTheme.colorScheme.onTertiary,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary

                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // TODO test it
         /*       ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    DropdownMenuItem(
                        text = { Text("Monday") },
                        onClick = {
                            workoutDay = Weekdays.MONDAY
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Tuesday") },
                        onClick = {
                            workoutDay = Weekdays.TUESDAY
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Wednesday") },
                        onClick = {
                            workoutDay = Weekdays.WEDNESDAY
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Thursday") },
                        onClick = {
                            workoutDay = Weekdays.THURSDAY
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Friday") },
                        onClick = {
                            workoutDay = Weekdays.FRIDAY
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Saturday") },
                        onClick = {
                            workoutDay = Weekdays.SATURDAY
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Sunday") },
                        onClick = {
                            workoutDay = Weekdays.SUNDAY
                            expanded = false
                        }
                    )
                }*/

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    selectedExercises?.forEach { exercise: Exercise ->
                        if (!removedExercises.value.contains(exercise)) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AddExerciseItem(
                                    modifier = Modifier.weight(1f),
                                    exercise = exercise,
                                    onItemClick = {},
                                    onItemClick2 = {},
                                    isSelected = true,
                                    isToAdd = false
                                )
                                Spacer(modifier = Modifier.width(5.dp))

                                DeleteExerciseButton {
                                    removedExercises.value = listOf(exercise)
                                    deleteExerciseHandler(ManageExercises.DeleteExercise(exercise = exercise))
                                }
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextButton(onClick = {
                            selectExerciseHandler(ExerciseEvent.SelectIsToAdd(true))
                            navController.navigate(Route.TypedExerciseScreen.route) }) {
                            Text(text = "Add new exercise")
                        }
                        Spacer(modifier = Modifier.width(80.dp))
                        Spacer(modifier = Modifier.width(20.dp))
                        AddExerciseButton {
                            selectExerciseHandler(ExerciseEvent.SelectIsToAdd(true))
                            navController.navigate(Route.TypedExerciseScreen.route)
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }

            /*
            item {
                Button(
                    onClick = {
                        showDialog.value=true
                        val exercisesList: MutableList<Exercise> = selectedExercises?.toMutableList() ?: mutableListOf()
                        // after the assignment, delete all exercises so it is ready for a new use
                        selectedExercises?.forEach {
                            deleteExerciseHandler(ManageExercises.DeleteExercise(it))
                        }
                        addNameHandler(ManageExercises.SelectWorkoutStored(TextFieldValue()))
                        createWorkoutHandler(ManageWorkoutEvent.CreateWorkout(
                            Workout(id = 0, name = workoutTitle.text, workoutDay = workoutDay, exercises = exercisesList)
                        ))

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    enabled = selectedExercises != null
                ) {
                    Text(
                        text = "SAVE WORKOUT",
                    )
                }
            }*/

            item {
                if (showDialog.value) {
                    FeedbackAlertDialog(
                        title =  "You have successfully added your workout!",
                        onDismissRequest = { showDialog.value = false
                            navController.navigate(Route.HomeScreen.route)                        },
                        onConfirm = {
                            showDialog.value = false
                            navController.navigate(Route.HomeScreen.route)                        },
                        show = showDialog

                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxHeight(0.125f)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            Button(
                onClick = {
                    showDialog.value=true
                    val exercisesList: MutableList<Exercise> = selectedExercises?.toMutableList() ?: mutableListOf()
                    // after the assignment, delete all exercises so it is ready for a new use
                    selectedExercises?.forEach {
                        deleteExerciseHandler(ManageExercises.DeleteExercise(it))
                    }
                    addNameHandler(ManageExercises.SelectWorkoutStored(TextFieldValue()))
                    createWorkoutHandler(ManageWorkoutEvent.CreateWorkout(
                        Workout(id = 0, name = workoutTitle.text, workoutDay = workoutDay, exercises = exercisesList)
                    ))

                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.Center)
                    .height(60.dp),
                enabled = selectedExercises != null
            ) {
                Text(
                    text = "SAVE WORKOUT",
                )
            }
        }
    }
}

@Composable
fun AddExerciseButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(color = Color.Cyan, shape = CircleShape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "+",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DeleteExerciseButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(color = Color.Red, shape = CircleShape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "-",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}