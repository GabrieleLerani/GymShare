package com.project.gains.presentation.plan

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.Exercise
import com.project.gains.data.Weekdays
import com.project.gains.data.Workout
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.ErrorMessage
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManageExercises
import com.project.gains.presentation.workout.events.ManageWorkoutEvent
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.toFormattedString
import com.project.gains.util.toLowerCaseString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddManualWorkout(
    navController: NavController,
    paddingValues: PaddingValues,
    manualWorkoutViewModel: ManualWorkoutViewModel,
    addNameHandler: (ManageExercises.SelectWorkoutStored) -> Unit,
    selectExerciseHandler: (ExerciseEvent.SelectIsToAdd) -> Unit,
    deleteExerciseHandler: (ManageExercises.DeleteExercise) -> Unit,
    deleteAllExerciseHandler: (ManageExercises.DeleteAllExercise) -> Unit,

    createWorkoutHandler: (ManageWorkoutEvent.CreateWorkout) -> Unit,
    completionMessage: MutableState<String>
) {
    var workoutTitle by remember { mutableStateOf(TextFieldValue("")) }
    val showDialog = remember { mutableStateOf(false) }

    val selectedExercises by manualWorkoutViewModel.selectedExercises.observeAsState(emptyList())
    val workoutTitleStored by manualWorkoutViewModel.workoutTitle.observeAsState(TextFieldValue(""))
    val removedExercises = remember { mutableStateOf(listOf<Exercise>()) }
    var selectedDay by rememberSaveable { mutableStateOf(Weekdays.MONDAY) }
    var expanded by remember { mutableStateOf(false) }

    var inputInserted by remember { mutableStateOf(false) }
    var allFilled by remember { mutableStateOf(false) }

    if (showDialog.value ){

        FeedbackAlertDialog(
            onDismissRequest = {   },
            onConfirm = {   showDialog.value=false
                navController.navigate(Route.HomeScreen.route)


            },
            title ="Workout Created!",
            text ="You find it in the home!",
            icon = Icons.Default.Check,
            dismiss = false
        )
    }

    LaunchedEffect(workoutTitleStored) {
        workoutTitle = workoutTitleStored
    }

    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Header()

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = workoutTitle,
                            onValueChange = { newValue ->
                                workoutTitle = newValue
                                addNameHandler(ManageExercises.SelectWorkoutStored(newValue))
                            },
                            label = {
                                Text(
                                    "Workout name",
                                )
                            },
                            placeholder = { Text("Set name...") },
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface), // Set the text color to white
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "name")
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = if (workoutTitle.text.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = if (workoutTitle.text.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )
                        )

                        // check error
                        if (inputInserted && (workoutTitle.text.isEmpty())) {
                            Text(
                                text = "Empty name. Please insert one",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .align(Alignment.Start)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }) {

                        OutlinedTextField(
                            value = toLowerCaseString(selectedDay.name),
                            label = {Text("Select workout day")},
                            onValueChange = {},
                            leadingIcon = {
                              Icon(Icons.Default.Today, contentDescription = "day")
                            },
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
                            onDismissRequest = { expanded = false }
                        ) {
                            Weekdays.entries.forEach { day ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                             day.toFormattedString(),
                                        )
                                    },
                                    onClick = {
                                        expanded = false
                                        selectedDay = day
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()

                                )
                                Spacer(modifier = Modifier.height(2.dp))
                            }
                        }

                    }

                }

                item {

                    // TODO adjust element position and leave lazy column only for exercises
                    if (inputInserted && selectedExercises.isEmpty()) {
                        ErrorMessage(message = "Please insert at least one exercise.")
                    }

                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        selectedExercises.forEach { exercise: Exercise ->
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
                    }

                }
                item { Spacer(modifier = Modifier.height(10.dp)) }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AddExerciseButton {
                        selectExerciseHandler(ExerciseEvent.SelectIsToAdd(true))
                        navController.navigate(Route.TypedExerciseScreen.route)
                    }

                    SaveButton {
                        if (workoutTitle.text.isEmpty() || selectedExercises.isEmpty()){
                            inputInserted = true
                        } else {
                            val exercisesList: MutableList<Exercise> = selectedExercises.toMutableList()
                            addNameHandler(ManageExercises.SelectWorkoutStored(TextFieldValue()))
                            createWorkoutHandler(
                                ManageWorkoutEvent.CreateWorkout(
                                    Workout(
                                        id = 0,
                                        name = workoutTitle.text.ifEmpty { "workout 1" },
                                        workoutDay = selectedDay,
                                        exercises = exercisesList
                                    )
                                )
                            )
                            // after the assignment, delete all exercises so it is ready for a new use
                            deleteAllExerciseHandler(ManageExercises.DeleteAllExercise)

                            showDialog.value=true
                        }
                    }

                }


            }

        }
    }
}


@Composable
fun Header(){
    Text(
        text = "Create your workout!",
        style = MaterialTheme.typography.headlineMedium,

        )
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Manually add each exercise to your workout",
        style = MaterialTheme.typography.bodySmall,
    )
}

@Composable
fun AddExerciseButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick =  onClick ,
        modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Exercise",

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Exercise")
        }
    }
}

@Composable
fun SaveButton(onClick: () -> Unit){
    FilledTonalButton(
        onClick = { onClick() },
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text("Save workout")
    }
}

@Composable
fun DeleteExerciseButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(color = MaterialTheme.colorScheme.error, shape = CircleShape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "-",
            color = MaterialTheme.colorScheme.surface,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}



@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ScreenPrev(){
    val navController = rememberNavController()
    val manualWorkoutViewModel : ManualWorkoutViewModel = hiltViewModel()

    AddManualWorkout(
        navController = navController,
        paddingValues = PaddingValues(0.dp),
        manualWorkoutViewModel = manualWorkoutViewModel,
        addNameHandler = {},
        selectExerciseHandler = {},
        deleteExerciseHandler = {},
        deleteAllExerciseHandler = {},
        createWorkoutHandler = {},
        completionMessage = mutableStateOf("")
    )
}