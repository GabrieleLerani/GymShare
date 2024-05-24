package com.project.gains.presentation.plan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.project.gains.presentation.components.BackButton

import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.WorkoutList
import com.project.gains.presentation.events.DeleteEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.navgraph.Route

import com.project.gains.theme.GainsAppTheme

@Composable
fun PlanScreen(
    navController: NavController,
    deleteHandler: (DeleteEvent) -> Unit,
    selectHandler: (SelectEvent)->Unit,
    generalViewModel: GeneralViewModel

) {
    // Sample list of workouts
    val selectedPlan by generalViewModel.selectedPlan.observeAsState()

    GainsAppTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            androidx.compose.material3.Surface(
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BackButton {
                                    navController.popBackStack()
                                }
                            }
                            // Title
                            androidx.compose.material3.Text(
                                text = "Your ${selectedPlan?.period} Plan",
                                style = MaterialTheme.typography.headlineMedium,
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Button(
                                onClick = { navController.navigate(Route.NewPlanScreen.route) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer, // Orange
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Text color
                                ),
                                modifier = Modifier.padding(bottom = 10.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add Icon")
                            }
                        }

                        // List of workouts
                        WorkoutList(selectedPlan?.workouts,{ workout ->
                            selectHandler(SelectEvent.SelectWorkout(workout))
                            navController.navigate(Route.WorkoutScreen.route)
                        }) { work ->
                            deleteHandler(DeleteEvent.DeleteWorkout(work))
                            selectedPlan?.workouts?.remove(work)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel:GeneralViewModel= hiltViewModel()
    PlanScreen(
        navController = navController,
        deleteHandler = {},
        selectHandler = {},
        generalViewModel

    )
}