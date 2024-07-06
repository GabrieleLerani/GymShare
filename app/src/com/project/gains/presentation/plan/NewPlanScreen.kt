package com.project.gains.presentation.plan

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.TrainingType
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.navgraph.Route

import com.project.gains.theme.GainsAppTheme

@Composable
fun NewPlanScreen(
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(20.dp)
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 290.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        // TODO test it
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon"
                        )
                    }
                }
            }
            item {
                Text(
                    text = "Add Pre-Made Workout",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            item {
                Text(
                    text = "Choose a pre-made workout from our library or use the workout builder to create your own.",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Button(
                    onClick = {
                        navController.navigate(Route.AddGeneratedPlan.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                ) {
                    Text(text = "USE PLAN BUILDER")
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Button(
                    onClick = {
                        navController.navigate(Route.AddManualWorkout.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                ) {
                    Text(text = "ADD MANUAL WORKOUT")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewPlanPagePreview() {
    val navController = rememberNavController()
    GainsAppTheme {
        NewPlanScreen(
            navController = navController
        )
    }
}