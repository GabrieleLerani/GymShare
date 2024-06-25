package com.project.gains.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout
import com.project.gains.data.generateSampleExercises
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.MuscleGroupItem
import com.project.gains.presentation.components.PlanPagePopup
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.WorkoutDaysList
import com.project.gains.presentation.components.WorkoutHeader
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.DeleteEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.theme.GainsAppTheme
import dagger.hilt.android.lifecycle.HiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypedExerciseScreen(
    createHandler: (CreateEvent) -> Unit,
    deleteHandler: (DeleteEvent) -> Unit,
    navController:NavController, selectHandler: (SelectEvent)->Unit, generalViewModel: GeneralViewModel) {
    // Sample list of exercises
    val searchQuery = remember { mutableStateOf("") }
    val show = remember {
        mutableStateOf(false)
    }
    val allExercises by generalViewModel.exercises.observeAsState()
    val searchedExercises = remember { allExercises }
    val selectedExercises = remember {
        mutableStateListOf(Exercise("", R.drawable.legs, "", ExerciseType.ARMS, TrainingType.STRENGTH))
    }
    val selectExerciseType by generalViewModel.selectedExerciseType.observeAsState()
    var res:Int
    if (selectExerciseType == ExerciseType.ARMS){
        res= R.drawable.arms
    }
    else if (selectExerciseType == ExerciseType.SHOULDERS){
        res= R.drawable.shoulders

    }
    else if (selectExerciseType == ExerciseType.CHEST){
        res= R.drawable.chest2

    }
    else if (selectExerciseType == ExerciseType.BACK){
        res= R.drawable.back

    }
    else if (selectExerciseType == ExerciseType.LEGS){
        res= R.drawable.legs

    }
    else{
        res= R.drawable.arms2

    }
    val exercises = remember {
        mutableListOf(generateSampleExercises(selectExerciseType ?: ExerciseType.ARMS,res))
    }


    GainsAppTheme {

        Scaffold(
            topBar = {
                TopBar(navController = navController ,  message = "Exercises Type")
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Top

                ) {

                    item {
                        com.project.gains.presentation.components.SearchBar(
                            searchQuery = searchQuery,
                            allExercises = allExercises,
                            searchedExercises = searchedExercises
                        )

                    }
                    allExercises?.forEach{exercise ->
                        item {
                            AddExerciseItem(exercise = exercise, { exerciseToAdd ->
                                show.value=true
                                selectedExercises.add(exerciseToAdd)
                                selectHandler(SelectEvent.SelectExercise(exercise))
                                navController.navigate(Route.ExerciseDetailsScreen.route)},
                                isSelected = exercise in selectedExercises
                            )                        }
                    }
                }
            }
        }
    }
    // Page popups
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val generalViewModel:GeneralViewModel= hiltViewModel()
    GainsAppTheme {
        TypedExerciseScreen(navController = rememberNavController(), selectHandler = {}, deleteHandler = {},
            createHandler = {}, generalViewModel = generalViewModel)
    }
}