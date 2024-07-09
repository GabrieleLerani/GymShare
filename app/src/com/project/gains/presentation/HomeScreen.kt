package com.project.gains.presentation

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.project.gains.R
import com.project.gains.data.PeriodMetricType

import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.favourite.FavouriteViewModel

import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.PlanViewModel
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.presentation.workout.WorkoutViewModel

import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.currentWeekday

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    workoutViewModel: WorkoutViewModel,
    selectHandler:(ManagePlanEvent.SelectPlan)->Unit,
    paddingValues: PaddingValues,
    planViewModel: PlanViewModel,
    favouriteViewModel:FavouriteViewModel
) {
    val openPopup = remember { mutableStateOf(false) }
    var selectedPlan = remember {
        planViewModel.selectedPlan.value?.name
    }
    val plans by planViewModel.plans.observeAsState()
    var expanded by remember { mutableStateOf(false) }
    val favouriteExercises by favouriteViewModel.favouriteExercises.observeAsState()
    val favouriteWorkouts by favouriteViewModel.favouriteWorkouts.observeAsState()




    val workouts by workoutViewModel.workouts.observeAsState()

    CustomBackHandler(
        onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
            ?: return,
        enabled = true
    ) {
        openPopup.value = true
    }


   GainsAppTheme {
       Box(
           modifier = Modifier
               .padding(paddingValues)
       ) {
           LazyColumn(
               modifier = Modifier
                   .fillMaxSize(),
               verticalArrangement = Arrangement.Top

           ) {
               item {
                   // TODO CHANGE
                   Column (
                       horizontalAlignment = Alignment.CenterHorizontally,
                       verticalArrangement = Arrangement.Top
                   ) {
                       TextField(
                           readOnly = true,
                           value = selectedPlan.toString(),
                           onValueChange = { },
                           label = {
                                },
                           trailingIcon = {
                               androidx.compose.material.IconButton(onClick = { expanded = !expanded }) {
                                   Icon(
                                       imageVector = Icons.Default.ArrowDropDown,
                                       contentDescription = "Dropdown Icon"
                                   )
                               }
                           },
                           colors = ExposedDropdownMenuDefaults.textFieldColors(),
                           modifier = Modifier
                               .padding(start = 60.dp)
                               .background(
                                   shape = RoundedCornerShape(20.dp),
                                   color = MaterialTheme.colorScheme.background
                               )
                       )
                       DropdownMenu(
                           expanded = expanded,
                           onDismissRequest = {
                               expanded= false
                           }
                       ) {
                           plans?.forEach {plan ->
                               DropdownMenuItem(
                                   text = { androidx.compose.material.Text(plan.name) },
                                   onClick = {
                                       selectHandler(ManagePlanEvent.SelectPlan(plan))
                                       selectedPlan=plan.name
                                       expanded = false
                                   }
                               )
                           }
                       }
                   }
               }
               val weekday = currentWeekday()

               workouts?.forEach { workout ->
                   if (workout.workoutDay.ordinal == weekday) {
                       item {
                           GeneralCard(imageResId = R.drawable.pexels1, title = workout.name) {
                               navController.navigate(Route.WorkoutScreen.route)
                           }
                       }
                   }
               }

               favouriteExercises?.forEach { exercise ->
                   item {
                       GeneralCard(imageResId = R.drawable.pexels1, title = exercise.name) {
                           navController.navigate(Route.WorkoutScreen.route)
                       }
                   }

               }

               favouriteWorkouts?.forEach { workout ->
                   item {
                       GeneralCard(imageResId = R.drawable.pexels1, title = workout.name) {
                           navController.navigate(Route.WorkoutScreen.route)
                       }

                   }

               }


           }
       }
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

    LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.addCallback(backCallback)

    DisposableEffect(onBackPressedDispatcher) {
        backCallback.isEnabled = enabled
        onDispose {
            backCallback.remove()
        }
    }
}

@Composable
fun GeneralCard(imageResId: Int, title: String, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .height(150.dp)
            .background(Color.Gray, RoundedCornerShape(16.dp))
            .clickable {
                onItemClick()
            }
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)) // Clip to the rounded corners
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 300f
                    ),
                    RoundedCornerShape(16.dp)
                )
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}