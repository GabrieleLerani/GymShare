package com.project.gains.presentation

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn

//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.FitnessCenter

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import com.project.gains.GeneralViewModel

import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.CustomBackHandler
import com.project.gains.presentation.components.ExitPopup
import com.project.gains.presentation.components.UserContentCard
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.events.SelectEvent

import com.project.gains.presentation.navgraph.Route

import com.project.gains.theme.GainsAppTheme

@Composable
fun GainsHomeScreen(
    navController: NavController,
    viewModel: MainViewModel,
    generalViewModel: GeneralViewModel,
    selectHandler:(SelectEvent)->Unit,
) {
    val userProfile by viewModel.userProfile.observeAsState()
    val openPopup = remember { mutableStateOf(false) }
    val workouts by generalViewModel.workouts.observeAsState()
    val plans by generalViewModel.plans.observeAsState()
    val plots by  generalViewModel.plots.observeAsState()


    CustomBackHandler(
        onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher ?: return,
        enabled = true
    ) {
        openPopup.value = true
    }

    GainsAppTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {

                    // Content
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        item { Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TopBar(navController, userProfile)

                        } }
                        item {  workouts?.forEach {workout ->
                            UserContentCard(
                                title = "Your Workouts",
                                icon = Icons.Default.FitnessCenter
                            ) {
                                // Navigate to the workouts screen when clicked
                                selectHandler(SelectEvent.SelectWorkout(workout))
                                navController.navigate(Route.WorkoutScreen.route)
                            } } }

                         item {plots?.forEach {plot ->
                             UserContentCard(title = "Your Progress", icon = Icons.Default.Analytics) {
                                 // Navigate to the progress screen when clicked

                                 navController.navigate(Route.ProgressScreen.route)
                             }
                         }  }
                        item {   plans?.forEach {plan ->
                            UserContentCard(title = "Your Plans", icon = Icons.Default.Event) {
                                // Navigate to the progress screen when clicked
                                selectHandler(SelectEvent.SelectPlan(plan))

                                navController.navigate(Route.PlanScreen.route)
                            }
                        } }

                    }
                    ExitPopup(openPopup)
                }
            }
        }
    }
}



