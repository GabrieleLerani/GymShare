package com.project.gains.presentation.plan

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
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.data.Exercise

import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.LogoUser
import com.project.gains.presentation.components.NewPlanPagePopup
import com.project.gains.presentation.components.PlanPagePopup
import com.project.gains.presentation.components.SetWorkoutPagePopup
import com.project.gains.presentation.components.ShareContentPagePopup
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.WorkoutDaysList
import com.project.gains.presentation.components.WorkoutHeader
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.DeleteEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.navgraph.Route


import com.project.gains.theme.GainsAppTheme

@Composable
fun PlanScreen(
    navController: NavController,
    deleteHandler: (DeleteEvent) -> Unit,
    selectHandler: (SelectEvent)->Unit,
    createHandler:(CreateEvent)->Unit,
    generalViewModel: GeneralViewModel

) {
    // Sample list of workouts
    val selectedPlan by generalViewModel.selectedPlan.observeAsState()

    val linkedApps by generalViewModel.linkedApps.observeAsState()
    var showPopup1 = remember { mutableStateOf(false) }
    var showPopup2 = remember { mutableStateOf(false) }



    val workouts by generalViewModel.workouts.observeAsState()
    var showDialogShared = remember { mutableStateOf(false) }
    var showDialogWorkout = remember { mutableStateOf(false) }
    var showDialogPlan = remember { mutableStateOf(false) }

    var showDialog = remember { mutableStateOf(false) }




    GainsAppTheme {

        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = selectedPlan?.name ?: "Plan",
                    button= {
                        androidx.compose.material.IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {

                                showPopup2.value=true

                            }) {
                            androidx.compose.material.Icon(
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
                        androidx.compose.material.IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {
                                showPopup1.value=true
                            }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "New",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                )
            },
            bottomBar = { BottomNavigationBar(navController = navController) }
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
                            WorkoutHeader()
                            Spacer(modifier = Modifier.height(16.dp))
                            WorkoutDaysList {
                                navController.navigate(Route.WorkoutScreen.route)
                            }
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    showDialog.value = true
                                    showDialogShared.value = true

                                },
                                modifier = Modifier.size(60.dp),
                                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "New Plan icon",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .padding(10.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            IconButton(
                                onClick = {
                                    navController.navigate(Route.NewPlanScreen.route)
                                },
                                modifier = Modifier.size(60.dp),
                                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "New Plan icon",
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(10.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                    item {
                        if (showDialog.value) {
                            FeedbackAlertDialog(
                                title = "Select a social",
                                message = "",
                                onDismissRequest = { showDialog.value = false },
                                onConfirm = {
                                    showDialog.value = false
                                    showDialogShared.value = true
                                },
                                confirmButtonText = "Ok",
                                dismissButtonText = ""
                            )
                        }
                    }
                    item {
                        if (showDialogShared.value) {
                            FeedbackAlertDialog(
                                title = "",
                                message = "You have successfully Shared your content!",
                                onDismissRequest = { showDialogShared.value = false },
                                onConfirm = {
                                    showDialogShared.value = false
                                    navController.navigate(Route.HomeScreen.route)
                                },
                                confirmButtonText = "Ok",
                                dismissButtonText = ""
                            )
                        }
                    }
                    item {
                        if (showDialogWorkout.value) {
                            FeedbackAlertDialog(
                                title = "",
                                message = "You have successfully created your workout!",
                                onDismissRequest = { showDialogShared.value = false },
                                onConfirm = {
                                    showDialogShared.value = false
                                    navController.navigate(Route.HomeScreen.route)
                                },
                                confirmButtonText = "Ok",
                                dismissButtonText = ""
                            )
                        }
                    }
                    item {
                        if (showDialogPlan.value) {
                            FeedbackAlertDialog(
                                title = "",
                                message = "You have successfully created your plan!",
                                onDismissRequest = { showDialogShared.value = false },
                                onConfirm = {
                                    showDialogShared.value = false
                                    navController.navigate(Route.HomeScreen.route)
                                },
                                confirmButtonText = "Ok",
                                dismissButtonText = ""
                            )
                        }
                    }

                }
            }
        }
        // Page popups

        workouts?.let {
            PlanPagePopup(showPopup1, it, selectHandler,createHandler,navController,generalViewModel,showDialogWorkout,showDialogPlan)}
        linkedApps?.let {
            ShareContentPagePopup(
                showPopup2,
                it,
                showDialog,
                { showDialogShared.value = true },
                navController)
        } }
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
        createHandler = {},
        generalViewModel=generalViewModel
    )
}