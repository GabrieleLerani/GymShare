package com.project.gains.presentation.exercises

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.project.gains.R
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.FavoriteTopBar
import com.project.gains.presentation.components.InstructionCard
import com.project.gains.presentation.components.WarningCard
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route


@Composable
fun ExerciseDetailsScreen(
    navController: NavController,
    exerciseViewModel: ExerciseViewModel,
    addFavouriteExerciseHandler: (ExerciseEvent.AddExercise) -> Unit,
    removeFavouriteExerciseHandler: (ExerciseEvent.DeleteExercise) -> Unit,
    completionMessage: MutableState<String>

) {
    val selectExercise by exerciseViewModel.selectedExercise.observeAsState()
    val favoriteExercises by exerciseViewModel.favouriteExercises.observeAsState()
    val favorite = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        showDialog.value=false
    }
    Box(modifier = Modifier.fillMaxSize()) {
        // Second Box (main content)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(selectExercise?.gifResId ?: R.drawable.arms2),
                    contentDescription = "Exercise",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }

            item {
                Text(
                    text = selectExercise?.name ?: "Dumbbell",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                Text(
                    text = "The inclined dumbbell bench press is considered as the best basic exercise for developing the pectoral muscles and increasing general strength. This exercise allows a greater amplitude of movement than the classic bar press, and allows you to work out the muscles more efficiently.",
                    fontSize = 16.sp
                )
            }
            item { Spacer(modifier = Modifier.height(30.dp)) }

            item {
                Text(
                    text = "Instruction",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }

            selectExercise?.description?.forEach { instruction ->
                item {
                    InstructionCard(text = instruction)
                }
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }

            item {
                Text(
                    text = "Warning",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }

            selectExercise?.warnings?.forEach { warning ->
                item {
                    WarningCard(message = warning)
                }
                item { Spacer(Modifier.height(5.dp)) }
            }
        }

        // First Box (Top bar)
        FavoriteTopBar(
            message = "Exercise",
            button2 = {
                IconButton(
                    modifier = Modifier
                        .size(45.dp)
                        .fillMaxWidth(),
                    onClick = {
                        if (favorite.value){
                            removeFavouriteExerciseHandler(ExerciseEvent.DeleteExercise)
                            favorite.value=false
                            completionMessage.value="Exercise removed from favorites!"
                            showDialog.value=true
                        }
                        else{
                            addFavouriteExerciseHandler(ExerciseEvent.AddExercise)
                            favorite.value=true
                            completionMessage.value="Exercise added to favorites!"
                            showDialog.value=true
                        }
                    }) {
                    Icon(
                        imageVector = if (favorite.value){
                            Icons.Default.Favorite
                        }else if (favoriteExercises?.contains(selectExercise) == true) {
                            Icons.Default.Favorite
                        }
                        else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = "Favorite",
                    )
                }
            },
            button = {
                IconButton(
                    modifier = Modifier.size(45.dp),
                    onClick = {
                        navController.navigate(Route.ShareScreen.route)
                    }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Share",
                        modifier = Modifier.graphicsLayer {
                            rotationZ = -45f // Rotate 45 degrees counterclockwise
                        }
                    )
                }
            },
            button1 = {
                BackButton {
                    navController.popBackStack()
                }
            },
        )
    }
}






@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun ExerciseDetailsScreenPreview() {
    val navController= rememberNavController()
    val exerciseViewModel: ExerciseViewModel = hiltViewModel()
    ExerciseDetailsScreen(
        navController = navController,
        exerciseViewModel = exerciseViewModel,
        addFavouriteExerciseHandler = {},
        removeFavouriteExerciseHandler = {},
        completionMessage = mutableStateOf("")

    )
}

