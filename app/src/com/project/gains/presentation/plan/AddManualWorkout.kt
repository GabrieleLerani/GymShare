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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.gains.data.Exercise
import com.project.gains.data.Weekdays
import com.project.gains.data.Workout
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManageExercises
import com.project.gains.presentation.workout.events.ManageWorkoutEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddManualWorkout(
    navController: NavController,
    manualWorkoutViewModel: ManualWorkoutViewModel,
    addExerciseHandler: (ManageExercises.AddExercise) -> Unit,
    deleteExerciseHandler: (ManageExercises.DeleteExercise) -> Unit,
    createWorkoutHandler: (ManageWorkoutEvent.CreateWorkout) -> Unit
) {
    var workoutTitle by remember { mutableStateOf("") }
    var workoutDay by remember { mutableStateOf(Weekdays.MONDAY) }
    var expanded by remember { mutableStateOf(false) }
    val selectedExercises by manualWorkoutViewModel.selectedExercises.observeAsState()

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
                    text = "Create Your Workout!",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            item {
                Text(
                    text = "Manually add each exercise to your workout day, press the button + to search for an exercise and then click save button to submit",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                OutlinedTextField(
                    value = workoutTitle,
                    onValueChange = { newValue ->
                        workoutTitle = newValue
                    },
                    label = {
                        Text(
                            "Enter workout name...",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    shape = RoundedCornerShape(size = 20.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface), // Set the text color to white

                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary, // Set the contour color when focused
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary // Set the contour color when not focused
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // TODO test it
                ExposedDropdownMenuBox(
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
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    selectedExercises?.forEach { exercise: Exercise ->
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
                                deleteExerciseHandler(ManageExercises.DeleteExercise(exercise = exercise))
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { /* Add exercise action */ }) {
                            Text(text = "Add new exercise", color = Color.Cyan)
                        }
                        Spacer(modifier = Modifier.width(80.dp))
                        Spacer(modifier = Modifier.width(20.dp))
                        AddExerciseButton {
                            // TODO insert a function to specify the behaviour
                            // this could be an example
                            //addExerciseHandler(ManageExercises.AddExercise(exercise = exercise))
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                Button(
                    onClick = {
                        val exercisesList: MutableList<Exercise> = selectedExercises?.toMutableList() ?: mutableListOf()
                        // after the assignment, delete all exercises so it is ready for a new use
                        selectedExercises?.forEach {
                            deleteExerciseHandler(ManageExercises.DeleteExercise(it))
                        }
                        createWorkoutHandler(ManageWorkoutEvent.CreateWorkout(
                            Workout(id = 0, name = workoutTitle, workoutDay = workoutDay, exercises = exercisesList)
                        ))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
                    enabled = selectedExercises != null
                ) {
                    Text(
                        text = "SAVE WORKOUT",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
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