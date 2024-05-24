package com.project.gains.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.MuscleGroup
import com.project.gains.data.TrainingType
import com.project.gains.presentation.components.BackButton

import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.ExerciseCard
import com.project.gains.theme.GainsAppTheme


@Composable
fun ExerciseDetailsScreen(
    navController: NavController,
    generalViewModel: GeneralViewModel

) {
    // Sample list of workouts
    val exercise by generalViewModel.selectedExercise.observeAsState()

    GainsAppTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 26.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BackButton {
                                navController.popBackStack()
                            }
                            // Title
                            Text(
                                text = exercise?.name ?: "",
                                style = MaterialTheme.typography.headlineMedium,
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 0.dp)
                            )
                        }

                        ExerciseCard(exercise ?: Exercise("",0,"",ExerciseType.BALANCE,TrainingType.STRENGTH,MuscleGroup.ARMS))
                    }
                }
            }
        }
    }
}







@Preview(showBackground = true)
@Composable
fun ExerciseDetailsScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel:GeneralViewModel = hiltViewModel()
    ExerciseDetailsScreen(
        navController = navController,
        generalViewModel =generalViewModel

    )
}
