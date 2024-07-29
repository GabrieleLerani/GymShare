package com.project.gains.presentation.exercises

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.project.gains.data.Exercise
import com.project.gains.data.tabTitles
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.FavoriteTopBar
import com.project.gains.presentation.components.MenuItem
import com.project.gains.presentation.components.MyDropdownMenu
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailsScreen(
    navController: NavController,
    exerciseViewModel: ExerciseViewModel,
    addFavouriteExerciseHandler: (ExerciseEvent.AddExercise) -> Unit,
    removeFavouriteExerciseHandler: (ExerciseEvent.DeleteExercise) -> Unit,
    completionMessage: MutableState<String>
) {
    val selectedExercise by exerciseViewModel.selectedExercise.observeAsState()
    val favoriteExercises by exerciseViewModel.favouriteExercises.observeAsState()
    val favorite = remember { mutableStateOf(false) }

    var favoritesText = "Add to favorites"
    val favoritesIcon: ImageVector

    if (favorite.value || favoriteExercises?.contains(selectedExercise) == true) {
        favoritesText = "Delete from favorites"
        favoritesIcon = Icons.Default.Delete
    } else {
        favoritesIcon = Icons.Default.FavoriteBorder
    }

    val exerciseDetailMenuItems = listOf(
        MenuItem(
            text = favoritesText,
            icon = favoritesIcon,
            onClick = {
                if (favorite.value) {
                    removeFavouriteExerciseHandler(ExerciseEvent.DeleteExercise)
                    favorite.value = false
                    completionMessage.value = "Exercise removed from favorites!"

                } else {
                    addFavouriteExerciseHandler(ExerciseEvent.AddExercise)
                    favorite.value = true
                    completionMessage.value = "Exercise added to favorites!"

                }
            }
        ),
        MenuItem(
            text = "Share",
            icon = Icons.Outlined.Share,
            onClick = { navController.navigate(Route.ShareScreen.route) }
        )
    )

    var selectedTabIndex by remember { mutableIntStateOf(0) }


    Column(modifier = Modifier.fillMaxSize()){
        selectedExercise?.let {
            FavoriteTopBar(
                message = it.name,
                navigationIcon = { BackButton { navController.popBackStack() } },
                dropDownMenu = { MyDropdownMenu(menuItems = exerciseDetailMenuItems) }
            )
        }
        PrimaryTabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    icon = { Icon(tab.second, contentDescription = tab.first) },
                    text = { Text(text = tab.first) }
                )
            }
        }

        Column(modifier = Modifier.padding(4.dp)) {
            when (selectedTabIndex) {
                0 -> ExerciseDetailsTab(selectedExercise)
                1 -> ExerciseInstructionsTab(selectedExercise)
                2 -> ExerciseWarningsTab(selectedExercise)
            }
        }
    }
}

@Composable
fun ExerciseDetailsTab(selectExercise: Exercise?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
                text = "The inclined dumbbell bench press is considered as the best basic exercise for developing the pectoral muscles and increasing general strength. This exercise allows a greater amplitude of movement than the classic bar press, and allows you to work out the muscles more efficiently.",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ExerciseInstructionsTab(selectExercise: Exercise?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        selectExercise?.description?.forEach { instruction ->
            item {
                InstructionCard(text = instruction)
            }
        }
    }
}

@Composable
fun ExerciseWarningsTab(selectExercise: Exercise?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        selectExercise?.warnings?.forEach { warning ->
            item {
                WarningCard(message = warning)
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun InstructionCard(text: String) {
    androidx.compose.material.Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.onTertiary,
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun WarningCard(message: String) {
    androidx.compose.material.Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                RoundedCornerShape(16.dp)
            ),
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.WarningAmber,
                contentDescription = "Warning",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun ExerciseDetailsScreenPreview() {
    val navController = rememberNavController()
    val exerciseViewModel: ExerciseViewModel = hiltViewModel()
    ExerciseDetailsScreen(
        navController = navController,
        exerciseViewModel = exerciseViewModel,
        addFavouriteExerciseHandler = {},
        removeFavouriteExerciseHandler = {},
        completionMessage = mutableStateOf("")
    )
}