package com.project.gains.presentation.share

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.project.gains.presentation.explore.events.SearchEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.PlanViewModel
import kotlinx.coroutines.launch
import java.lang.StringBuilder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PostScreen(
    navController: NavController,
    planViewModel: PlanViewModel,
    onPost: () -> Unit,
    onExit: () -> Unit,
    generalPostHandler: (SearchEvent.GeneralPostEvent) -> Unit,
    workoutPostHandler: (SearchEvent.WorkoutPostEvent) -> Unit,
){

    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val selectedWorkouts by planViewModel.selectedWorkouts.observeAsState(mutableListOf())


    BottomSheetScaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onExit) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                },
                actions = {

                    AttachButton(onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    })

                    FilledTonalButton(
                        onClick = {
                            if (selectedWorkouts.isNotEmpty()) {

                                val content = StringBuilder()
                                content.append(textState.text + "\n\n")
                                selectedWorkouts.forEach { workout ->
                                    content.append(workout.name).append(":\n")
                                    workout.exercises.forEach { exercise ->
                                        content.append("\t").append(exercise.name).append("\n")

                                    }
                                    content.append("\n\n")
                                }

                                val image = if (imageUri != null) {
                                    imageUri
                                } else {null}

                                workoutPostHandler(
                                    SearchEvent.WorkoutPostEvent(
                                        social = 1,
                                        username = "gabriele",
                                        imageUri =  image,
                                        content = content.toString()
                                    )
                                )

                                selectedWorkouts.clear()
                            }
                            else {
                                generalPostHandler(
                                    SearchEvent.GeneralPostEvent(
                                        content = textState.text,
                                        social = 1, // TODO image is not passed
                                        username = "user 2",
                                    )
                                )
                            }
                            onPost()
                                  },
                        modifier = Modifier.padding(end = 8.dp),
                        enabled = textState.text.isNotEmpty() || imageUri != null
                    ) {
                        Text("Post")
                    }

                }
            )
        },
        scaffoldState = scaffoldState,
        sheetContent = {
            SheetContent(onShareWorkout = {
                navController.navigate(Route.WorkoutListScreen.route)
            }) { selectedUri ->
                imageUri = selectedUri
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            LazyColumn(modifier = Modifier.fillMaxSize()) {

                item{
                    imageUri?.let {

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)) {

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                IconButton(
                                    onClick = { imageUri = null } ,
                                    modifier = Modifier
                                        .padding(end = 4.dp),

                                    colors = IconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        contentColor = MaterialTheme.colorScheme.primary,
                                        disabledContentColor = MaterialTheme.colorScheme.primary,
                                        disabledContainerColor = MaterialTheme.colorScheme.primary
                                    ),
                                    enabled = imageUri != null
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Remove Image")
                                }

                                AsyncImage(
                                    model = imageUri,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                )
                            }


                        }

                    }
                }

                item{
                    if (selectedWorkouts.isNotEmpty()){
                        selectedWorkouts.forEach {workout ->
                            Card(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(
                                        text = workout.name,
                                        modifier = Modifier.padding(8.dp),
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                    IconButton(
                                        onClick = { planViewModel.deleteSelectedWorkout(workout) } ,
                                        modifier = Modifier
                                            .padding(end = 8.dp),

                                        colors = IconButtonColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                            contentColor = MaterialTheme.colorScheme.primary,
                                            disabledContentColor = MaterialTheme.colorScheme.primary,
                                            disabledContainerColor = MaterialTheme.colorScheme.primary
                                        ),

                                        ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Remove workout")
                                    }
                                }
                                Column(modifier = Modifier.padding(16.dp)) {

                                    workout.exercises.forEach { exercise ->
                                        Text(
                                            text = exercise.name,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }


                item {
                    OutlinedTextField(
                        value = textState,
                        onValueChange = { textState = it },
                        modifier = Modifier.fillMaxSize(),
                        placeholder = { Text(text = "Share your opinions...") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent
                        )
                    )
                }

            }

        }
    }

}


@Composable
fun AttachButton(onClick: () -> Unit){
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(end = 4.dp),
        colors = IconButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(Icons.Default.Add, contentDescription = "Attach content")
    }
}



@Composable
fun SheetContent(onShareWorkout : () -> Unit, onImageSelected: (Uri) -> Unit) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                onImageSelected(it)
            }
        }

    GridContent(
        onItemClick = {
            launcher.launch(
                "image/*"
            )},
        onShareWorkout = onShareWorkout

    )

}



@Composable
fun GridContent(onShareWorkout: () -> Unit,onItemClick: () -> Unit) {

    val items = listOf(
        Triple("Workout", Icons.Default.FitnessCenter, onShareWorkout),
        Triple("Image", Icons.Default.Image, onItemClick),
        Triple("Video", Icons.Default.VideoFile, {})

    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // set at most 3 column
        modifier = Modifier.padding(16.dp)
    ) {
        items(items.size) { index ->
            val (label, icon, onClick) = items[index]
            GridItem(
                icon = icon,
                label = label,
                onClick = onClick)
        }
    }
}

@Composable
fun GridItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
            onClick = onClick,
            colors = IconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(icon, contentDescription = label)
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun prev(){
    val planViewModel : PlanViewModel = hiltViewModel()
    PostScreen(
        navController = rememberNavController(),
        planViewModel = planViewModel, onPost = {  }, onExit = {  }, workoutPostHandler = {}, generalPostHandler =  {})
}