package com.project.gains.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue


import com.project.gains.presentation.components.DynamicBottomBar
import com.project.gains.presentation.components.DynamicTopBar
import com.project.gains.presentation.components.SearchViewModel
import com.project.gains.presentation.exercises.ExerciseViewModel

import com.project.gains.presentation.navgraph.NavGraph
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.theme.GainsAppTheme


@Composable
fun MainScreen(
    startDestination: String
) {
    val navController = rememberNavController()


    GainsAppTheme {
        Scaffold(
            topBar = { DynamicTopBar(navController = navController ) },
            bottomBar = {
                    DynamicBottomBar(navController = navController)
              },
        ) {
            paddingValues ->
            NavGraph(
                startDestination = startDestination,
                navController = navController,
                paddingValues = paddingValues)
        }
    }


}