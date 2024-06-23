package com.project.gains.presentation.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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

import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.PlanItem
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.events.DeleteEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.navgraph.Route

import com.project.gains.theme.GainsAppTheme

@Composable
fun PlansScreen(
    navController: NavController,
    deleteHandler: (DeleteEvent) -> Unit,
    selectHandler: (SelectEvent)-> Unit,
    generalViewModel: GeneralViewModel

) {
    // Sample list of exercises
    val plans by generalViewModel.plans.observeAsState()
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
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TopBar(navController = navController, userProfile = null,message ="Your Plans")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Text(
                            text = "Your Plans",
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

                            // Plans
                            plans?.forEach { plan ->
                                item {
                                    Box(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the separator
                                            .background(Color.White) // Background color of the separator
                                    ) {
                                        PlanItem(plan = plan,{ plan ->
                                            deleteHandler(DeleteEvent.DeletePlan(plan))
                                            plans?.remove(plan)
                                        }, onItemClick = {plan ->
                                            selectHandler(SelectEvent.SelectPlan(plan))
                                            navController.navigate(Route.PlanScreen.route)
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
fun PlansScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel:GeneralViewModel = hiltViewModel()
    PlansScreen(
        navController = navController,
        deleteHandler = {  },
        selectHandler = {},
        generalViewModel

    )
}