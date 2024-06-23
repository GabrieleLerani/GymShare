package com.project.gains.presentation.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.WorkoutItem
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
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(paddingValues).fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top=5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TopBar(navController = navController, userProfile = null,message=selectedPlan?.name ?: "Your Plan")
                    }
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
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the separator
                            .background(Color.White) // Background color of the separator
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2), // Adjust the number of columns as needed
                            contentPadding = PaddingValues(2.dp) // Add padding around the grid
                        ) {
                            selectedPlan?.workouts?.forEach { workout ->
                                item {
                                    Box(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the separator
                                            .background(Color.White) // Background color of the separator
                                    ) {
                                        WorkoutItem(
                                            workout = workout,
                                            onItemClick = {
                                                selectHandler(
                                                    SelectEvent.SelectWorkout(workout)
                                                )
                                                navController.navigate(Route.WorkoutScreen.route)
                                            },
                                            onDelete = {
                                                deleteHandler(
                                                    DeleteEvent.DeleteWorkout(workout)
                                                )
                                            })

                                    }
                                }
                            }
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