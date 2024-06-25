import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R
import com.project.gains.data.ExerciseType
import com.project.gains.presentation.components.LogoUser
import com.project.gains.presentation.components.MuscleGroupItem
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.theme.GainsAppTheme



@Composable
fun ExerciseTypeScreen(navController:NavController,selectHandler: (SelectEvent)->Unit) {

    GainsAppTheme {

        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = "Workout" ,
                    button= {
                        androidx.compose.material.IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {
                                // Handle history button click
                                // TODO history popus page
                                //navController.navigate(Route.HistoryScreen.route)
                            }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = "History",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                )
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center

                ) {

                    item {
                            MuscleGroupItem(imageResId = R.drawable.chest2, title = "Chest"){
                                selectHandler(SelectEvent.SelectExerciseType(ExerciseType.CHEST))
                                navController.navigate(Route.TypedExerciseScreen.route)
                            }
                            MuscleGroupItem(imageResId = R.drawable.back2, title = "Back"){
                                selectHandler(SelectEvent.SelectExerciseType(ExerciseType.BACK))
                                navController.navigate(Route.TypedExerciseScreen.route)
                            }
                            MuscleGroupItem(imageResId = R.drawable.legs, title = "Legs"){
                                selectHandler(SelectEvent.SelectExerciseType(ExerciseType.LEGS))
                                navController.navigate(Route.TypedExerciseScreen.route)
                            }
                            MuscleGroupItem(imageResId = R.drawable.legs2, title = "Arms"){
                                selectHandler(SelectEvent.SelectExerciseType(ExerciseType.ARMS))
                                navController.navigate(Route.TypedExerciseScreen.route)
                            }
                            MuscleGroupItem(imageResId = R.drawable.legs3, title = "Shoulders"){
                                selectHandler(SelectEvent.SelectExerciseType(ExerciseType.SHOULDERS))
                                navController.navigate(Route.TypedExerciseScreen.route)
                            }
                        }
                    }
                }
            }
        }
        // Page popups
    }


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GainsAppTheme {
        ExerciseTypeScreen(navController = rememberNavController(),{})
    }
}
