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
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Search
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

import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.PlanPagePopup
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
    val showPopup1 by generalViewModel.showPopup.observeAsState()
    var showPopup2 = remember { mutableStateOf(false) }
    var notification = remember {
        mutableStateOf(false)
    }


    val workouts by generalViewModel.workouts.observeAsState()
    val showDialogShared by generalViewModel.showDialogShared.observeAsState()
    val showDialogWorkout by generalViewModel.showDialogWorkout.observeAsState()
    val showDialogPlan by generalViewModel.showDialogPlan.observeAsState()

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
                                selectHandler(SelectEvent.SelectPlanPopup(true))
                                selectHandler(SelectEvent.SelectPreviewsPage("Home"))

                                selectHandler(SelectEvent.SelectClicked(false))
                                selectHandler(SelectEvent.SelectShowPopup3(false))
                                selectHandler(SelectEvent.SelectShowPopup4(false))
                            }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "New",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        androidx.compose.material.IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {
                                navController.navigate(Route.WorkoutModeScreen.route)
                            }) {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = "Workout",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                )
                if (notification.value){
                    NotificationCard(message ="Notification", onClose = {notification.value=false})
                }
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
                            WorkoutHeader(generalViewModel)
                            Spacer(modifier = Modifier.height(16.dp))
                            WorkoutDaysList(workouts ?: mutableListOf()) {
                                navController.navigate(Route.WorkoutScreen.route)
                            }
                        }
                    }

                }
                if (showDialogWorkout == true) {
                    FeedbackAlertDialog(
                        title = "You have successfully created your workout!",
                        message = "",
                        onDismissRequest = { selectHandler(SelectEvent.SelectShowDialogWorkout(false)) },
                        onConfirm = {
                            selectHandler(SelectEvent.SelectShowDialogWorkout(false))
                        },
                        confirmButtonText = "Ok",
                        dismissButtonText = "",
                        color = MaterialTheme.colorScheme.onError,
                        show = showDialog

                    )
                }
                if (showDialogPlan == true) {
                    FeedbackAlertDialog(
                        title = "You have successfully created your plan!",
                        message = "",
                        onDismissRequest = { selectHandler(SelectEvent.SelectShowDialogPlan(false)) },
                        onConfirm = {
                            selectHandler(SelectEvent.SelectShowDialogPlan(false))
                        },
                        confirmButtonText = "Ok",
                        dismissButtonText = "",
                        color = MaterialTheme.colorScheme.onError,
                        show = showDialog

                    )
                }
                if (showDialogShared==true) {

                    FeedbackAlertDialog(
                        title = "You have successfully Shared your content!",
                        message = "",
                        onDismissRequest = {
                        },
                        onConfirm = {
                            selectHandler(SelectEvent.SelectShowDialogShared(false))
                        },
                        confirmButtonText = "Ok",
                        dismissButtonText = "",
                        color = MaterialTheme.colorScheme.onError,
                        show = showPopup2
                    )
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
                showDialogShared,
                { selectHandler(SelectEvent.SelectShowDialogShared(true))},
                navController,generalViewModel)
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