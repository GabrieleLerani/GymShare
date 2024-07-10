package com.project.gains.presentation

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.gains.R
import com.project.gains.presentation.components.GeneralCard
import com.project.gains.presentation.exercises.ExerciseViewModel
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.PlanViewModel
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.currentWeekday


@Composable
fun HomeScreen(
    navController: NavController,
    workoutViewModel: WorkoutViewModel,
    selectHandler:(ManagePlanEvent.SelectPlan)->Unit,
    paddingValues: PaddingValues,
    planViewModel: PlanViewModel,
    exerciseViewModel: ExerciseViewModel

) {
    val openPopup = remember { mutableStateOf(false) }
    var selectedPlan = remember {
        planViewModel.selectedPlan.value?.name
    }
    val plans by planViewModel.plans.observeAsState()
    var expanded by remember { mutableStateOf(false) }
    val favouriteExercises by exerciseViewModel.favouriteExercises.observeAsState()
    val favouriteWorkouts by workoutViewModel.favouriteWorkouts.observeAsState()

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
                   Column(
                       horizontalAlignment = Alignment.CenterHorizontally,
                       verticalArrangement = Arrangement.Top,
                       modifier = Modifier.padding(16.dp)
                   ) {
                       Box(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(bottom = 16.dp)
                               .background(
                                   MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                   RoundedCornerShape(16.dp)
                               )
                               .border(
                                   border = BorderStroke(
                                       width = 3.dp,
                                       color = MaterialTheme.colorScheme.onSurface
                                   ), shape = RoundedCornerShape(16.dp)
                               )

                               .padding(16.dp)
                       ) {
                           Row(
                               verticalAlignment = Alignment.CenterVertically,
                               modifier = Modifier.fillMaxWidth()
                           ) {
                               Text(
                                   text = "Your current plan: ${selectedPlan.toString()}",
                                   style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                   color = MaterialTheme.colorScheme.onSurface,
                                   modifier = Modifier.weight(1f)
                                   // Take up the remaining space
                               )
                               IconButton(
                                   onClick = {  expanded =!expanded }
                               ) {
                                   Icon(
                                       imageVector = Icons.Default.ArrowDropDown,
                                       contentDescription = "Dropdown Icon"
                                   )
                               }
                           }
                       }

                       DropdownMenu(
                           expanded = expanded,
                           onDismissRequest = { expanded=false },
                           modifier = Modifier
                               .fillMaxWidth(0.7f)
                               .background(
                                   shape = RoundedCornerShape(16.dp),
                                   color = MaterialTheme.colorScheme.surface
                               )
                               .padding(10.dp) // Padding to match the Text above
                       ) {
                           plans?.forEach { plan ->
                               DropdownMenuItem(
                                   text = {
                                       Text(
                                           plan.name,
                                           style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                           color = MaterialTheme.colorScheme.onSurface
                                       )
                                   },
                                   onClick = {
                                       selectedPlan=plan.name
                                       selectHandler(ManagePlanEvent.SelectPlan(plan))
                                       expanded=false
                                   },
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .background(
                                           color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                           shape = RoundedCornerShape(16.dp)
                                       )
                                       .border(
                                           border = BorderStroke(
                                               width = 3.dp,
                                               color = MaterialTheme.colorScheme.onSurface
                                           ), shape = RoundedCornerShape(16.dp)
                                       )
                                       .padding(16.dp) // Inner padding for the item
                               )
                               Spacer(modifier = Modifier.height(2.dp))
                           }
                       }
                   }
               }



               item {
                   Text(
                       text = "Your daily workout",
                       style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                       color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(16.dp) // Add padding for better spacing
                           .background(
                               MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                               RoundedCornerShape(16.dp)
                           )
                           .border(
                               border = BorderStroke(
                                   width = 3.dp,
                                   color = MaterialTheme.colorScheme.onSurface
                               ), shape = RoundedCornerShape(16.dp)
                           )

                           .padding(16.dp) // Inner padding for the text itself
                   )
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

               item {
                   if (favouriteExercises?.isEmpty()==true){
                       Text(
                           text = "No favorites exercises yet ",
                           style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                           color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(16.dp) // Add padding for better spacing
                               .background(
                                   MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                   RoundedCornerShape(16.dp)
                               )
                               .border(
                                   border = BorderStroke(
                                       width = 3.dp,
                                       color = MaterialTheme.colorScheme.onSurface
                                   ), shape = RoundedCornerShape(16.dp)
                               )

                               .padding(16.dp) // Inner padding for the text itself
                       )
                   }else{
                       Text(
                           text = "Your favorites exercises",
                           style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                           color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(16.dp) // Add padding for better spacing
                               .background(
                                   MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                   RoundedCornerShape(16.dp)
                               )
                               .border(
                                   border = BorderStroke(
                                       width = 3.dp,
                                       color = MaterialTheme.colorScheme.onSurface
                                   ), shape = RoundedCornerShape(16.dp)
                               )

                               .padding(16.dp) // Inner padding for the text itself
                       )
                   }

               }

               favouriteExercises?.forEach { exercise ->
                   item {
                       GeneralCard(imageResId = R.drawable.pexels1, title = exercise.name) {
                           navController.navigate(Route.ExerciseDetailsScreen.route)
                       }
                   }

               }

               item {
                   if (favouriteWorkouts?.isEmpty()==true) {

                       Text(
                           text = "No favorites workouts yet ",
                           style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                           color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(16.dp) // Add padding for better spacing
                               .background(
                                   MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                   RoundedCornerShape(16.dp)
                               )
                               .border(
                                   border = BorderStroke(
                                       width = 3.dp,
                                       color = MaterialTheme.colorScheme.onSurface
                                   ), shape = RoundedCornerShape(16.dp)
                               )

                               .padding(16.dp) // Inner padding for the text itself
                       )
                   }else{
                       Text(
                           text = "Your favorites workouts",
                           style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                           color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(16.dp) // Add padding for better spacing
                               .background(
                                   MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                   RoundedCornerShape(16.dp)
                               )
                               .border(
                                   border = BorderStroke(
                                       width = 3.dp,
                                       color = MaterialTheme.colorScheme.onSurface
                                   ), shape = RoundedCornerShape(16.dp)
                               )

                               .padding(16.dp) // Inner padding for the text itself
                       )
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
