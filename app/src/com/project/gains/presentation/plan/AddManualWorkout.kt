package com.project.gains.presentation.plan

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseButtonMode
import com.project.gains.data.ExerciseType
import com.project.gains.data.TrainingType
import com.project.gains.data.Weekdays
import com.project.gains.data.Workout
import com.project.gains.presentation.components.ErrorMessage
import com.project.gains.presentation.components.ExerciseItem
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.PlanSlidingComponent
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManageExercises
import com.project.gains.presentation.workout.events.ManageWorkoutEvent
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.toFormattedString
import com.project.gains.util.toLowerCaseString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddManualWorkout(
    navController: NavController,
    manualWorkoutViewModel: ManualWorkoutViewModel,
    addNameHandler: (ManageExercises.SelectWorkoutStored) -> Unit,
    selectExerciseHandler: (ExerciseEvent.SelectIsToAdd) -> Unit,
    deleteExerciseHandler: (ManageExercises.DeleteExercise) -> Unit,
    deleteAllExerciseHandler: (ManageExercises.DeleteAllExercise) -> Unit,
    createWorkoutHandler: (ManageWorkoutEvent.CreateWorkout) -> Unit,
) {
    var workoutTitle by remember { mutableStateOf(TextFieldValue("")) }
    val showDialog = remember { mutableStateOf(false) }

    val selectedExercises by manualWorkoutViewModel.selectedExercises.observeAsState(emptyList())
    val workoutTitleStored by manualWorkoutViewModel.workoutTitle.observeAsState(TextFieldValue(""))
    val removedExercises = remember { mutableStateOf(listOf<Exercise>()) }
    var selectedDay by rememberSaveable { mutableStateOf(Weekdays.MONDAY) }
    var expanded by remember { mutableStateOf(false) }

    var inputInserted by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val isOptionSelected = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(initialPage = 0) { 2 }

    if (showDialog.value) {
        FeedbackAlertDialog(
            onDismissRequest = { },
            onConfirm = {
                showDialog.value = false
                navController.navigate(Route.HomeScreen.route)
            },
            title = "Workout Created!",
            text = "You find it in the home!",
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
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PlanSlidingComponent(
                    inactiveColor = MaterialTheme.colorScheme.secondaryContainer,
                    activeColor = MaterialTheme.colorScheme.primary,
                    pagerState = pagerState,
                    modifier = Modifier.padding(20.dp),
                )

                Spacer(modifier = Modifier.padding(top = 8.dp))

                // header that contains only written information
                //Header()

                Spacer(modifier = Modifier.padding(top = 8.dp))

                // TODO check error message if no exercise
                if (inputInserted && selectedExercises.isEmpty()) {
                    ErrorMessage(message = "Please insert at least one exercise.")
                }

                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) { page ->
                    when (page) {
                        0 -> WorkoutInfoPage(
                            workoutTitle = workoutTitle,
                            onWorkoutTitleChange = { newValue ->
                                workoutTitle = newValue
                                addNameHandler(ManageExercises.SelectWorkoutStored(newValue))
                            },
                            selectedDay = selectedDay,
                            onSelectedDayChange = { selectedDay = it },
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            inputInserted = inputInserted,
                            isOptionSelected = isOptionSelected
                        )
                        1 -> ExerciseSelectionPage(
                            navController = navController,
                            selectedExercises = selectedExercises,
                            removedExercises = removedExercises,
                            scope = scope,
                            deleteExerciseHandler = deleteExerciseHandler,
                            selectExerciseHandler = selectExerciseHandler
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    if (pagerState.currentPage > 0) {
                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                                    isOptionSelected.value = false // Reset option selected state
                                }
                            },
                            modifier = Modifier
                        ) {
                            Text("Back")
                        }
                    }

                    // if it's last page show only save workout button
                    if (pagerState.currentPage + 1 == pagerState.pageCount){
                        SaveButton {
                            if (workoutTitle.text.isEmpty() || selectedExercises.isEmpty()) {
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
                                deleteAllExerciseHandler(ManageExercises.DeleteAllExercise)
                                showDialog.value = true
                            }
                        }

                    } else {
                        Button(
                            onClick = {
                                scope.launch {
                                    if (pagerState.currentPage < pagerState.pageCount - 1) {
                                        if (workoutTitle.text.isNotEmpty() && selectedDay.name.isNotEmpty()) {
                                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                                            isOptionSelected.value = false // Reset option selected state
                                        } else {
                                            inputInserted = true
                                        }
                                    } else {
                                        // do something
                                    }
                                }
                            },
                            enabled = isOptionSelected.value
                        ) {
                            Text(text = "Next")
                        }
                    }


                }
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutInfoPage(
    workoutTitle: TextFieldValue,
    onWorkoutTitleChange: (TextFieldValue) -> Unit,
    selectedDay: Weekdays,
    onSelectedDayChange: (Weekdays) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    inputInserted: Boolean,
    isOptionSelected: MutableState<Boolean>
) {
    var duration by remember { mutableStateOf(TextFieldValue("")) }
    var restIntervals by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = workoutTitle,
            onValueChange = onWorkoutTitleChange,
            label = { Text("Workout name") },
            placeholder = { Text("Set name...") },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "name") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (workoutTitle.text.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (workoutTitle.text.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        )

        if (inputInserted && workoutTitle.text.isEmpty()) {
            Text(
                text = "Empty name. Please insert one",
                color = MaterialTheme.colorScheme.error,
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration (minutes)") },
            placeholder = { Text("Enter duration...") },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "duration") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (duration.text.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (duration.text.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        )

        if (inputInserted && duration.text.isEmpty()) {
            Text(
                text = "Empty duration. Please insert one",
                color = MaterialTheme.colorScheme.error,
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = restIntervals,
            onValueChange = { restIntervals = it },
            label = { Text("Rest Intervals (seconds)") },
            placeholder = { Text("Enter rest intervals...") },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "rest intervals") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (restIntervals.text.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (restIntervals.text.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        )

        if (inputInserted && restIntervals.text.isEmpty()) {
            Text(
                text = "Empty rest intervals. Please insert one",
                color = MaterialTheme.colorScheme.error,
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Workout Description") },
            placeholder = { Text("Enter description...") },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "description") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (description.text.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (description.text.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        )

        if (inputInserted && description.text.isEmpty()) {
            Text(
                text = "Empty description. Please insert one",
                color = MaterialTheme.colorScheme.error,
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = toLowerCaseString(selectedDay.name),
                label = { Text("Select workout day") },
                onValueChange = {},
                leadingIcon = {
                    Icon(Icons.Default.Today, contentDescription = "day")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                readOnly = true,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                Weekdays.entries.forEach { day ->
                    DropdownMenuItem(
                        text = { Text(day.toFormattedString()) },
                        onClick = {
                            onExpandedChange(false)
                            onSelectedDayChange(day)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }

        LaunchedEffect(workoutTitle.text, duration.text, restIntervals.text, description.text, selectedDay) {
            isOptionSelected.value = workoutTitle.text.isNotEmpty() && duration.text.isNotEmpty() && restIntervals.text.isNotEmpty() && description.text.isNotEmpty() && selectedDay.name.isNotEmpty()
        }
    }
}


@Composable
fun ExerciseSelectionPage(
    navController: NavController,
    selectedExercises: List<Exercise>,
    removedExercises: MutableState<List<Exercise>>,
    scope: CoroutineScope,
    deleteExerciseHandler: (ManageExercises.DeleteExercise) -> Unit,
    selectExerciseHandler: (ExerciseEvent.SelectIsToAdd) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth().padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedExercises.isNotEmpty()) {
            selectedExercises.forEach { exercise: Exercise ->
                item {
                    AnimatedVisibility(
                        modifier = Modifier.fillMaxWidth(),
                        visible = !removedExercises.value.contains(exercise),
                        exit = shrinkVertically(animationSpec = tween(durationMillis = 200))
                    ) {
                        ExerciseItem(
                            modifier = Modifier
                                //.weight(1f)
                                .padding(top = 8.dp, bottom = 8.dp),
                            exercise = exercise,
                            onItemClick = {},
                            onRemove = {
                                scope.launch {
                                    removedExercises.value += exercise
                                    deleteExerciseHandler(ManageExercises.DeleteExercise(exercise = exercise))
                                }
                            },
                            mode = ExerciseButtonMode.REMOVE
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 42.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AddExerciseButton {
                    selectExerciseHandler(ExerciseEvent.SelectIsToAdd(true))
                    navController.navigate(Route.SearchExerciseScreen.route)
                }
            }

        }
    }
}

@Composable
fun Header() {
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
        onClick = onClick,
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
fun SaveButton(onClick: () -> Unit) {
    FilledTonalButton(
        onClick = { onClick() },
        modifier = Modifier.padding(start = 8.dp),
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
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Deletion"
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
        manualWorkoutViewModel = manualWorkoutViewModel,
        addNameHandler = {},
        selectExerciseHandler = {},
        deleteExerciseHandler = {},
        deleteAllExerciseHandler = {}
    ) {}
}

@Composable
@Preview(showBackground = true)
fun PreviewExerciseSelectionPage() {
    // Mock NavController
    val navController = rememberNavController()

    // Mock data and state
    val selectedExercises = listOf(
        Exercise(
            name = "Push-Up",
            description = listOf("Place hands shoulder-width apart", "Lower your body until your chest nearly touches the floor", "Push yourself back up"),
            gifResId = null,
            type = ExerciseType.ARMS,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 300,
            warnings = listOf("Avoid arching your back", "Keep your core tight"),
            videoId = 201
        ),
        Exercise(
            name = "Jumping Jacks",
            description = listOf("Stand upright with legs together", "Jump and spread your legs", "Simultaneously raise your arms above your head", "Return to starting position"),
            gifResId = null,
            type = ExerciseType.CORE,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 180,
            warnings = listOf("Land softly on your feet", "Keep your knees slightly bent"),
            videoId = 202
        ),
        Exercise(
            name = "Push-Up",
            description = listOf("Place hands shoulder-width apart", "Lower your body until your chest nearly touches the floor", "Push yourself back up"),
            gifResId = null,
            type = ExerciseType.ARMS,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 300,
            warnings = listOf("Avoid arching your back", "Keep your core tight"),
            videoId = 201
        ),
        Exercise(
            name = "Jumping Jacks",
            description = listOf("Stand upright with legs together", "Jump and spread your legs", "Simultaneously raise your arms above your head", "Return to starting position"),
            gifResId = null,
            type = ExerciseType.CORE,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 180,
            warnings = listOf("Land softly on your feet", "Keep your knees slightly bent"),
            videoId = 202
        ),
        Exercise(
            name = "Push-Up",
            description = listOf("Place hands shoulder-width apart", "Lower your body until your chest nearly touches the floor", "Push yourself back up"),
            gifResId = null,
            type = ExerciseType.ARMS,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 300,
            warnings = listOf("Avoid arching your back", "Keep your core tight"),
            videoId = 201
        ),
        Exercise(
            name = "Jumping Jacks",
            description = listOf("Stand upright with legs together", "Jump and spread your legs", "Simultaneously raise your arms above your head", "Return to starting position"),
            gifResId = null,
            type = ExerciseType.CORE,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 180,
            warnings = listOf("Land softly on your feet", "Keep your knees slightly bent"),
            videoId = 202
        ),
        Exercise(
            name = "Push-Up",
            description = listOf("Place hands shoulder-width apart", "Lower your body until your chest nearly touches the floor", "Push yourself back up"),
            gifResId = null,
            type = ExerciseType.ARMS,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 300,
            warnings = listOf("Avoid arching your back", "Keep your core tight"),
            videoId = 201
        ),
        Exercise(
            name = "Jumping Jacks",
            description = listOf("Stand upright with legs together", "Jump and spread your legs", "Simultaneously raise your arms above your head", "Return to starting position"),
            gifResId = null,
            type = ExerciseType.CORE,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 180,
            warnings = listOf("Land softly on your feet", "Keep your knees slightly bent"),
            videoId = 202
        ),
        Exercise(
            name = "Push-Up",
            description = listOf("Place hands shoulder-width apart", "Lower your body until your chest nearly touches the floor", "Push yourself back up"),
            gifResId = null,
            type = ExerciseType.ARMS,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 300,
            warnings = listOf("Avoid arching your back", "Keep your core tight"),
            videoId = 201
        ),
        Exercise(
            name = "Jumping Jacks",
            description = listOf("Stand upright with legs together", "Jump and spread your legs", "Simultaneously raise your arms above your head", "Return to starting position"),
            gifResId = null,
            type = ExerciseType.CORE,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 180,
            warnings = listOf("Land softly on your feet", "Keep your knees slightly bent"),
            videoId = 202
        ),
        Exercise(
            name = "Push-Up",
            description = listOf("Place hands shoulder-width apart", "Lower your body until your chest nearly touches the floor", "Push yourself back up"),
            gifResId = null,
            type = ExerciseType.ARMS,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 300,
            warnings = listOf("Avoid arching your back", "Keep your core tight"),
            videoId = 201
        ),
        Exercise(
            name = "Jumping Jacks",
            description = listOf("Stand upright with legs together", "Jump and spread your legs", "Simultaneously raise your arms above your head", "Return to starting position"),
            gifResId = null,
            type = ExerciseType.CORE,
            training = TrainingType.STRENGTH,
            sets = 3,
            totalTime = 180,
            warnings = listOf("Land softly on your feet", "Keep your knees slightly bent"),
            videoId = 202
        ),
    )
    val removedExercises = remember { mutableStateOf(listOf<Exercise>()) }
    val scope = rememberCoroutineScope()

    // Mock handlers
    val deleteExerciseHandler: (ManageExercises.DeleteExercise) -> Unit = {}
    val selectExerciseHandler: (ExerciseEvent.SelectIsToAdd) -> Unit = {}


    // Mock selected day
    val selectedDay = Weekdays.MONDAY

    // Call the composable function with mock data
    ExerciseSelectionPage(
        navController = navController,
        selectedExercises = selectedExercises,
        removedExercises = removedExercises,
        scope = scope,
        deleteExerciseHandler = deleteExerciseHandler,
        selectExerciseHandler = selectExerciseHandler
    )
}
