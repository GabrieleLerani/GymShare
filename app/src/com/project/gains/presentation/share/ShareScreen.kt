package com.project.gains.presentation.share

import androidx.compose.foundation.background
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
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.PlanItem
import com.project.gains.presentation.components.SelectContentPopup
import com.project.gains.presentation.components.ShareButton
import com.project.gains.presentation.components.SocialMediaIcon
import com.project.gains.presentation.components.WorkoutItem
import com.project.gains.presentation.events.SelectEvent

import com.project.gains.presentation.events.ShareContentEvent
import com.project.gains.presentation.navgraph.Route
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
    var showDialog = remember { mutableStateOf(false) }
    var showDialogShared = remember { mutableStateOf(false) }


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
                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TopBar(navController = navController, message="Share")
                    }
                    // Horizontal separator around the post
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the separator
                            .background(Color.White) // Background color of the separator
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 200.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
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
                            item { Spacer(modifier = Modifier.height(40.dp)) }

                            if (plan != null) {

                                item {
                                    Row(modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,) {
                                        PlanItem(plan = plan ?: Plan(
                                            0, "", PeriodMetricType.YEAR,
                                            mutableListOf()
                                        ), onDelete = {}) {

                                        }
                                    }
                                }
                            }

                            if (workout != null) {

                                item {
                                    Row(modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,) {
                                        WorkoutItem(workout = workout ?: Workout(
                                            0, "",
                                            mutableListOf()
                                        ), onItemClick = {}) {

                                        }
                                    }

                                }
                            }
                            if (exercise != null) {

                                item {
                                    Row(modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,) {
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
                            }
                            item { Spacer(modifier = Modifier.height(40.dp)) }

                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    IconButton(
                                        onClick = { showDialog.value = true },
                                        modifier = Modifier.size(60.dp),
                                        colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Share Icon",
                                            modifier = Modifier
                                                .size(60.dp)
                                                .padding(10.dp),
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                    if (plan != null || exercise != null || workout != null) {
                                        ShareButton(
                                            onClick = {
                                                showDialogShared.value=true
                                                shareHandler(
                                                    ShareContentEvent.ShareExercise(
                                                        exercise ?: Exercise(
                                                            "", R.drawable.gi, "",
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
                                            },
                                            isEnabled = plan != null || exercise != null || workout != null
                                        )
                                    }
                                }
                            }
                            item {
                                if (showDialog.value) {
                                    SelectContentPopup({ plan ->
                                        selectHandler(SelectEvent.SelectPlan(plan))
                                    }, { workout ->
                                        selectHandler(SelectEvent.SelectWorkout(workout))
                                    }, { exercise ->
                                        selectHandler(SelectEvent.SelectExercise(exercise))
                                    },
                                        showDialog
                                    )
                                }
                            }
                            item {  if (showDialogShared.value) {
                                FeedbackAlertDialog(
                                    title = "",
                                    message = "You have successfully Shared your content!",
                                    onDismissRequest = { showDialog.value = false },
                                    onConfirm = { showDialog.value = false
                                        navController.navigate(Route.HomeScreen.route)
                                    },
                                    confirmButtonText = "Ok",
                                    dismissButtonText = ""
                                )
                            } }
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