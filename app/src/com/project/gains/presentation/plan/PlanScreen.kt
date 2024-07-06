package com.project.gains.presentation.plan

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout

import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.ShareContentPagePopup
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.events.ManageDataStoreEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.presentation.settings.ShareContentViewModel
import com.project.gains.presentation.workout.WorkoutViewModel

import com.project.gains.theme.GainsAppTheme

@Composable
fun PlanScreen(
    navController: NavController,
    planViewModel: PlanViewModel,
    shareContentViewModel: ShareContentViewModel,
    workoutViewModel: WorkoutViewModel
) {
    // Sample list of workouts
    val selectedPlan by planViewModel.selectedPlan.observeAsState()
    val selectedLevel by planViewModel.selectedLvl.observeAsState()
    val selectedPeriod  by planViewModel.selectedPeriod.observeAsState()
    val selectedTraining  by planViewModel.selectedTrainingType.observeAsState()

    var showPopup = remember { mutableStateOf(false) }
    var notification = remember { mutableStateOf(false) }

    val workouts by workoutViewModel.workouts.observeAsState()

    GainsAppTheme {

        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = selectedPlan?.name ?: "Your Plan",
                    button= {
                        IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {
                                showPopup.value = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.graphicsLayer {
                                    rotationZ = -45f // Rotate 45 degrees counterclockwise
                                }
                            )
                        }
                    },

                    button1 = {
                        IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {
                                // TODO go to progress screen
                            }) {
                            Icon(
                                imageVector = Icons.Default.BarChart,
                                contentDescription = "Stats",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                )
                if (notification.value){
                    NotificationCard(
                        message ="Notification",
                        onClose = {
                            notification.value = false
                        }
                    )
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    navController = navController
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center

                ) {

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            WorkoutHeader(selectedLevel, selectedPeriod, selectedTraining)
                            Spacer(modifier = Modifier.height(16.dp))
                            WorkoutDaysList(workouts ?: mutableListOf()) {
                                navController.navigate(Route.WorkoutScreen.route)
                            }
                        }
                    }
                }
            }
        }

        ShareContentPagePopup(
            showPopup = showPopup,
            onItemClick = {},
            navController = navController,
            shareContentViewModel = shareContentViewModel
        )
    }
}

@Composable
fun WorkoutHeader(selectedLevel: Level?, selectedPeriod: PeriodMetricType?, selectedTraining: TrainingType?) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.pexels1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "GENERAL • $selectedTraining • TRAINING",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "For gym • $selectedLevel",
            fontSize = 16.sp,

            )
        Text(
            text = "For period • $selectedPeriod",
            fontSize = 14.sp,
        )
        Text(
            text = "Workouts done: 0",
            fontSize = 14.sp,
        )
    }
}

@Composable
fun WorkoutDaysList(workouts: MutableList<Workout>, onItemClick: () -> Unit) {

    val workoutsDays = mutableListOf<String>()

    val workoutsIds = mutableListOf<Int>()

    workouts.forEach { workout ->
        workoutsDays.add(workout.name)
        workoutsIds.add(workout.id)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Workout days",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        workoutsDays.forEachIndexed { index, day ->
            androidx.compose.material.Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clickable { onItemClick() },
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (index == 0) Icons.Default.Circle else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (index == 0) Color.Red else Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "workout day ${workoutsIds[index]}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = day,
                            fontSize = 14.sp
                        )
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
    val planViewModel: PlanViewModel = hiltViewModel()
    val shareContentViewModel: ShareContentViewModel = hiltViewModel()
    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    PlanScreen(
        navController = navController,
        planViewModel = planViewModel,
        shareContentViewModel = shareContentViewModel,
        workoutViewModel = workoutViewModel
    )
}