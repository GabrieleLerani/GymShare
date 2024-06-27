package com.project.gains.presentation

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.MaterialTheme

//noinspection UsingMaterialAndMaterial3Libraries

//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import com.project.gains.GeneralViewModel
import com.project.gains.R

import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.GeneralCard
import com.project.gains.presentation.components.LogoUser
import com.project.gains.presentation.components.PlanPagePopup
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.SelectEvent

import com.project.gains.presentation.navgraph.Route

import com.project.gains.theme.GainsAppTheme


@Composable
fun GainsHomeScreen(
    navController: NavController,
    viewModel: MainViewModel,
    generalViewModel: GeneralViewModel,
    selectHandler: (SelectEvent) -> Unit,
    createHandler: (CreateEvent) -> Unit,


    ) {
    val openPopup = remember { mutableStateOf(false) }
    val workouts by generalViewModel.workouts.observeAsState()
    val plans by generalViewModel.plans.observeAsState()
    val plots by generalViewModel.plots.observeAsState()
    var showDialogWorkout = remember { mutableStateOf(false) }
    var showDialogPlan = remember { mutableStateOf(false) }
    val showPopup1 by generalViewModel.showPopup.observeAsState()
    var showDialogShared = remember { mutableStateOf(false) }

    var showDialog = remember { mutableStateOf(false) }


    CustomBackHandler(
        onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
            ?: return,
        enabled = true
    ) {
        openPopup.value = true
    }


   GainsAppTheme {

       Scaffold(
           topBar = {
               TopBar(
                   navController = navController,
                   message = "Gym Share!" ,
                   button= {
                       LogoUser(
                           modifier = Modifier.size(60.dp), R.drawable.pexels5
                       ) { navController.navigate(Route.AccountScreen.route) }
                   },
                   button1 = {
                       androidx.compose.material.IconButton(
                           modifier = Modifier.size(45.dp),
                           onClick = {
                               selectHandler(SelectEvent.SelectClicked(false))
                               selectHandler(SelectEvent.SelectShowPopup3(false))
                               selectHandler(SelectEvent.SelectShowPopup4(false))
                               selectHandler(SelectEvent.SelectPreviewsPage("Home"))
                               selectHandler(SelectEvent.SelectPlanPopup(true))
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
                               navController.navigate(Route.TypedExerciseScreen.route)
                           }) {
                           Icon(
                               imageVector = Icons.Default.FitnessCenter,
                               contentDescription = "Workout",
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
                   workouts?.forEach{ workout ->
                       item {
                           GeneralCard(imageResId = R.drawable.pexels1, title = workout.name){
                               navController.navigate(Route.WorkoutScreen.route)
                           }
                       }
                   }
                   plans?.forEach{ plan ->
                       item {
                           GeneralCard(imageResId = R.drawable.pexels1, title = plan.name){
                               selectHandler(SelectEvent.SelectPlan(plan))
                               navController.navigate(Route.PlanScreen.route)
                           }
                       }
                   }
                   plots?.forEach{ plot ->
                       item {
                           GeneralCard(imageResId = R.drawable.pexels1, title = "plot"){
                               selectHandler(SelectEvent.SelectPlotPreview(plot.preview))
                               navController.navigate(Route.ProgressDetailsScreen.route)
                           }
                       }
                   }
                   item {
                       if (showDialogWorkout.value) {
                           FeedbackAlertDialog(
                               title = "You have successfully created your workout!",
                               message = "",
                               onDismissRequest = { showDialogShared.value = false },
                               onConfirm = {
                                   showDialogShared.value = false
                               },
                               confirmButtonText = "Ok",
                               dismissButtonText = "",
                               color = MaterialTheme.colorScheme.onError
                           )
                       }
                   }
                   item {
                       if (showDialogPlan.value) {
                           FeedbackAlertDialog(
                               title = "You have successfully created your plan!",
                               message = "",
                               onDismissRequest = { showDialogShared.value = false },
                               onConfirm = {
                                   showDialogShared.value = false
                               },
                               confirmButtonText = "Ok",
                               dismissButtonText = "",
                               color = MaterialTheme.colorScheme.onError
                           )
                       }
                   }
               }
           }

       }
       workouts?.let {
           PlanPagePopup(showPopup1, it, selectHandler,createHandler,navController,generalViewModel,showDialogWorkout,showDialogPlan)}
   }
}

@Composable
fun CustomBackHandler(
    onBackPressedDispatcher: OnBackPressedDispatcher,
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    androidx.activity.compose.LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.addCallback(backCallback)

    DisposableEffect(onBackPressedDispatcher) {
        backCallback.isEnabled = enabled
        onDispose {
            backCallback.remove()
        }
    }
}





