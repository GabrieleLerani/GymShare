package com.project.gains.presentation.share

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.MuscleGroup
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.Plan
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.ExerciseItem
import com.project.gains.presentation.components.PlanItem
import com.project.gains.presentation.components.SelectContentPopup
import com.project.gains.presentation.components.ShareButton
import com.project.gains.presentation.components.SocialMediaIcon
import com.project.gains.presentation.components.WorkoutItem
import com.project.gains.presentation.events.SelectEvent

import com.project.gains.presentation.events.ShareContentEvent
import com.project.gains.theme.GainsAppTheme

@Composable
fun ShareScreen(
    navController: NavController,
    selectHandler: (SelectEvent) -> Unit,
    shareHandler: (ShareContentEvent) -> Unit,
    generalViewModel: GeneralViewModel
) {
    val exercise by generalViewModel.selectedExercise.observeAsState()
    val workout by generalViewModel.selectedWorkout.observeAsState()
    val plan by generalViewModel.selectedPlan.observeAsState()
    var clickedApp by remember { mutableIntStateOf(1) }


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
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TopBar(navController, null)
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 250.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        item {
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    SocialMediaIcon(icon = R.drawable.instagram_icon, onClick = {
                                        selectHandler(SelectEvent.SelectLinkedApp(R.drawable.instagram_icon))
                                        clickedApp = R.drawable.instagram_icon
                                    }, clickedApp == R.drawable.instagram_icon)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    SocialMediaIcon(icon = R.drawable.x_logo_icon, onClick = {
                                        selectHandler(SelectEvent.SelectLinkedApp(R.drawable.x_logo_icon))
                                        clickedApp = R.drawable.x_logo_icon
                                    }, clickedApp == R.drawable.x_logo_icon)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    SocialMediaIcon(icon = R.drawable.tiktok_logo_icon, onClick = {
                                        selectHandler(SelectEvent.SelectLinkedApp(R.drawable.tiktok_logo_icon))
                                        clickedApp = R.drawable.tiktok_logo_icon
                                    }, clickedApp == R.drawable.tiktok_logo_icon)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    SocialMediaIcon(icon = R.drawable.drive_google_icon, onClick = {
                                        selectHandler(SelectEvent.SelectLinkedApp(R.drawable.drive_google_icon))
                                        clickedApp = R.drawable.drive_google_icon
                                    }, clickedApp == R.drawable.drive_google_icon)
                                }
                            }
                        }
                        item { Spacer(modifier = Modifier.height(40.dp)) }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SelectContentPopup({ plan ->
                                    selectHandler(SelectEvent.SelectPlan(plan))
                                }, { workout ->
                                    selectHandler(SelectEvent.SelectWorkout(workout))
                                }, { exercise ->
                                    selectHandler(SelectEvent.SelectExercise(exercise))
                                })
                            }
                        }

                        item { Spacer(modifier = Modifier.height(40.dp)) }
                        if (plan != null) {

                            item {
                                PlanItem(plan = plan ?: Plan(
                                    0, "", PeriodMetricType.YEAR,
                                    mutableListOf()
                                ), onDelete = {}) {

                                }
                            }
                        }

                        if (workout != null) {

                            item {
                                WorkoutItem(workout = workout ?: Workout(
                                    0, "",
                                    mutableListOf()
                                ), onItemClick = {}) {

                                }

                            }
                        }
                        if (exercise != null) {

                            item {
                                ExerciseItem(
                                    exercise = exercise ?: Exercise(
                                        "",
                                        R.drawable.gi,
                                        "",
                                        ExerciseType.BALANCE,
                                        TrainingType.STRENGTH,
                                        MuscleGroup.ARMS
                                    ), onDelete = {}) {

                                }

                            }
                        }

                        item { Spacer(modifier = Modifier.height(40.dp)) }

                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ShareButton(onClick = {
                                    shareHandler(
                                        ShareContentEvent.ShareExercise(
                                            exercise ?: Exercise(
                                                "", 0, "",
                                                ExerciseType.BALANCE,
                                                TrainingType.STRENGTH,
                                                MuscleGroup.ARMS
                                            )
                                        )
                                    )
                                    shareHandler(
                                        ShareContentEvent.ShareWorkout(
                                            workout ?: Workout(1, "", mutableListOf())
                                        )
                                    )
                                    shareHandler(
                                        ShareContentEvent.SharePlan(
                                            plan ?: Plan(
                                                1, "", PeriodMetricType.YEAR,
                                                mutableListOf()
                                            )
                                        )
                                    )
                                }, isEnabled = plan != null || exercise != null || workout != null)
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
fun ShareScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel:GeneralViewModel = hiltViewModel()
    ShareScreen(
        navController = navController,
        shareHandler = {  },
        selectHandler= {  },
        generalViewModel = generalViewModel
    )
}