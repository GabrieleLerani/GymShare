package com.project.gains.presentation.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.MuscleGroup
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.DeleteEvent
import com.project.gains.theme.GainsAppTheme

@Composable
fun AddExerciseScreen(
    navController: NavController,
    createHandler: (CreateEvent) -> Unit,
    deleteHandler: (DeleteEvent) -> Unit,
    generalViewModel: GeneralViewModel
) {
    // Sample list of exercises
    val searchQuery = remember { mutableStateOf("") }
    val show = remember {
        mutableStateOf(false)
    }
    val allExercises by generalViewModel.exercises.observeAsState()
    val searchedExercises = remember { allExercises }
    val oldExercise by generalViewModel.oldExercise.observeAsState()
    val selectedExercises = remember {
       mutableStateListOf(Exercise("",0,"",ExerciseType.BALANCE,TrainingType.STRENGTH,MuscleGroup.ARMS))
    }
    GainsAppTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TopBar(navController,message="Edit Workout")
                    }

                    // Search Bar
                    TextField(
                        value = searchQuery.value,
                        onValueChange = { query ->
                            searchQuery.value = query
                            searchedExercises?.clear()
                            allExercises?.filter {
                                it.name.contains(query, ignoreCase = true)
                            }?.let { searchedExercises?.addAll(it) }
                        },
                        label = { Text("Search Exercise") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, top = 16.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search Icon")
                        }
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {

                        items(items = searchedExercises ?: emptyList()) { exercise ->
                            AddExerciseItem(exercise = exercise, { exerciseToAdd ->
                                show.value=true
                                selectedExercises.add(exerciseToAdd)
                                deleteHandler(DeleteEvent.DeleteExercise(oldExercise ?:Exercise("",0,"",ExerciseType.BALANCE,TrainingType.STRENGTH,MuscleGroup.ARMS)))
                                createHandler(CreateEvent.CreateExercise(exercise))},
                                isSelected = exercise in selectedExercises
                            )
                        }
                        item {
                            Button(onClick = { createHandler(CreateEvent.CreateWorkout(Workout(1,"workout",selectedExercises))) }, colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.primaryContainer)) {
                            Text("Save workout", color = MaterialTheme.colorScheme.onPrimaryContainer)
                        } }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddExerciseScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel :GeneralViewModel= hiltViewModel()
    AddExerciseScreen(
        navController = navController,
        createHandler = {  },
        deleteHandler = {},
        generalViewModel
    )
}
