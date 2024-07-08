package com.project.gains.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold


import com.project.gains.presentation.components.DynamicBottomBar
import com.project.gains.presentation.components.DynamicTopBar

import com.project.gains.presentation.navgraph.NavGraph


@Composable
fun MainScreen(startDestination: String) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { DynamicTopBar(navController = navController) },
        bottomBar = { DynamicBottomBar(navController = navController) }
    ) {
        paddingValues ->
        NavGraph(
            startDestination = startDestination,
            navController = navController,
            paddingValues = paddingValues)
    }

}
