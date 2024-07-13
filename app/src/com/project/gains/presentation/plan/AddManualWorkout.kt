package com.project.gains.presentation.plan

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
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
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManageExercises
import com.project.gains.presentation.workout.events.ManageWorkoutEvent
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.toFormattedString
import java.util.Locale

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

    createWorkoutHandler: (ManageWorkoutEvent.CreateWorkout) -> Unit
) {
    var workoutTitle by remember { mutableStateOf(TextFieldValue("")) }
    val showDialog = remember { mutableStateOf(false) }

    val selectedExercises by manualWorkoutViewModel.selectedExercises.observeAsState(emptyList())
    val workoutTitleStored by manualWorkoutViewModel.workoutTitle.observeAsState(TextFieldValue(""))
    val removedExercises = remember { mutableStateOf(listOf<Exercise>()) }
    var selectedDay by rememberSaveable { mutableStateOf(Weekdays.MONDAY) }
    var expanded by remember { mutableStateOf(false) }

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
                    if (showDialog.value) {
                        FeedbackAlertDialog(
                            title = "Workout created!",
                            onDismissRequest = {
                                showDialog.value = false
                                navController.navigate(Route.HomeScreen.route)
                            },
                            onConfirm = {
                                showDialog.value = false
                                navController.navigate(Route.HomeScreen.route)
                            },
                            text =  "You will find it in the home",
                            icon = Icons.Default.Info
                        )
                    }
                }
                item {
                    Header()

                    OutlinedTextField(
                        value = workoutTitle,
                        onValueChange = { newValue ->
                            workoutTitle = newValue
                            addNameHandler(ManageExercises.SelectWorkoutStored(newValue))
                        },
                        label = {
                            Text(
                                if (workoutTitle.text.isEmpty()) "Enter workout name" else "",
                            )
                        },
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface), // Set the text color to white
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary, // Set the contour color when focused
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary // Set the contour color when not focused
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }) {

                        OutlinedTextField(
                            value = selectedDay.name.lowercase(Locale.ROOT)
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            label = {Text("Select workout day")},
                            onValueChange = {},
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )

                            },
                            readOnly = true,
                            modifier = Modifier
                                .menuAnchor().fillMaxWidth(),
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


                item {
                    FooterButton(
                        onClickSaveWorkout = {
                            showDialog.value = true
                            val exercisesList: MutableList<Exercise> = selectedExercises.toMutableList()
                            addNameHandler(ManageExercises.SelectWorkoutStored(TextFieldValue()))
                            createWorkoutHandler(
                                ManageWorkoutEvent.CreateWorkout(
                                    Workout(
                                        id = 0,
                                        name = workoutTitleStored.text.ifEmpty { "workout 4" },
                                        workoutDay = selectedDay,
                                        exercises = exercisesList
                                    )
                                )
                            )
                            // after the assignment, delete all exercises so it is ready for a new use
                            deleteAllExerciseHandler(ManageExercises.DeleteAllExercise)

                        },
                        onClickAddExercise = {
                            selectExerciseHandler(ExerciseEvent.SelectIsToAdd(true))
                            navController.navigate(Route.TypedExerciseScreen.route)
                        },
                        enabled = selectedExercises.isNotEmpty()
                    )
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
fun FooterButton(onClickSaveWorkout: () -> Unit, onClickAddExercise: () -> Unit, enabled: Boolean){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onClickSaveWorkout()},
            modifier = Modifier
                .weight(0.7f)
                .height(60.dp),

            enabled = enabled
        ) {
            Text(
                text = "SAVE WORKOUT",
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        AddExerciseButton (onClick = onClickAddExercise)

    }
}

@Composable
fun AddExerciseButton(onClick: () -> Unit) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Exercise",
            tint = MaterialTheme.colorScheme.surface
        )
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
        createWorkoutHandler = {},
        deleteAllExerciseHandler = {}
    )
}