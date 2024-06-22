package com.project.gains.presentation

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.project.gains.GeneralViewModel

import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.CustomBackHandler
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
    selectHandler: (SelectEvent) -> Unit,
) {
    val userProfile by viewModel.userProfile.observeAsState()
    val openPopup = remember { mutableStateOf(false) }
    val workouts by generalViewModel.workouts.observeAsState()
    val plans by generalViewModel.plans.observeAsState()
    val plots by generalViewModel.plots.observeAsState()

    CustomBackHandler(
        onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
            ?: return,
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
                modifier = Modifier.padding(paddingValues).fillMaxSize()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top=4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TopBar(userProfile = null, navController = navController)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Text(
                            text = "Your Suggestions",
                            style = MaterialTheme.typography.headlineMedium,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }

                        // Horizontal separator around the post
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
                                plots?.forEach { plot ->
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .clip(RoundedCornerShape(16.dp)) // Rounded corners for the separator
                                                .background(Color.White) // Background color of the separator
                                        ) {
                                            UserContentCard(
                                                title = "Your Progress",
                                                icon = Icons.Default.Analytics
                                            ) {
                                                // Navigate to the progress screen when clicked
                                                navController.navigate(Route.ProgressScreen.route)
                                            }
                                        }
                                    }
                                }
                                // Plans
                                plans?.forEach { plan ->
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .clip(RoundedCornerShape(16.dp)) // Rounded corners for the separator
                                                .background(Color.White) // Background color of the separator
                                        ) {
                                            UserContentCard(
                                                title = "Your Plans",
                                                icon = Icons.Default.Event
                                            ) {
                                                // Navigate to the plans screen when clicked
                                                selectHandler(SelectEvent.SelectPlan(plan))
                                                navController.navigate(Route.PlanScreen.route)
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
    }





