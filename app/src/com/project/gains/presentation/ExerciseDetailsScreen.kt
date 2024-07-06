package com.project.gains.presentation

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.FeedbackAlertDialog

import com.project.gains.presentation.components.InstructionCard
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.ShareContentPagePopup
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.WarningCard
import com.project.gains.presentation.events.SelectEvent

import com.project.gains.theme.GainsAppTheme


@Composable
fun ExerciseDetailsScreen(
    navController: NavController,
    generalViewModel: GeneralViewModel,
    shareContentViewModel: ShareContentViewModel,
    selectHandler:(SelectEvent)->Unit

) {
    // Sample list of workouts
    val linkedApps by generalViewModel.linkedApps.observeAsState()
    val showPopup2 = remember { mutableStateOf(false) }
    val showDialogShared by generalViewModel.showDialogShared.observeAsState()
    val showDialog = remember { mutableStateOf(false) }
    val exercise by generalViewModel.selectedExercise.observeAsState()
    val notification = remember {
        mutableStateOf(false)
    }

    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = "Chest",
                    button = {
                        androidx.compose.material.IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {

                                showPopup2.value = true

                            }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.surface,
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
                    }
                )
                if (notification.value){
                    NotificationCard(message ="Notification", onClose = {notification.value=false})
                }
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
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item {
                        Image(
                            painter = painterResource(R.drawable.legs),
                            contentDescription = "Exercise",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .padding(bottom = 16.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    item {
                        androidx.compose.material.Text(
                            text = "30-degree incline dumbbell bench press",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    item {
                        androidx.compose.material.Text(
                            text = "The inclined dumbbell bench press is considered as the best basic exercise for developing the pectoral muscles and increasing general strength. This exercise allows a greater amplitude of movement than the classic bar press, and allows you to work out the muscles more efficiently.",
                            fontSize = 16.sp
                        )
                    }
                    item { Spacer(modifier = Modifier.height(30.dp)) }

                    item {
                        androidx.compose.material.Text(
                            text = "Instruction",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    item { Spacer(modifier = Modifier.height(30.dp)) }
                    val instructionList = listOf(
                        "1) Lie down on the incline bench at an angle of 30 degrees, holding dumbbells in bent arms at the sides of your chest (palms facing forward).",
                        "2) After taking a deep breath, squeeze the dumbbells up into fully straightened arms. At the uppermost point, exhale.",
                        "3) Smoothly return to the starting position. Your elbows should be out to the sides and move along an imaginary line...",
                        // Add more instructions here
                    )

                    val warningList = listOf(
                        "Keep your back straight.",
                        "Avoid locking your elbows.",
                        "Maintain a consistent speed."
                    )


                    instructionList.forEach { instruction ->
                        item {
                            InstructionCard(text = instruction)

                        }
                    }

                    item { Spacer(modifier = Modifier.height(30.dp)) }

                    item {
                        androidx.compose.material.Text(
                            text = "Warning",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    item { Spacer(modifier = Modifier.height(30.dp)) }

                    warningList.forEach { warning ->
                        item {
                            WarningCard(message = warning)

                        }
                        item { Spacer(Modifier.height(5.dp)) }
                    }

                    item {
                        if (showDialog.value) {
                            FeedbackAlertDialog(
                                title = "Select a social",
                                message = "",
                                onDismissRequest = { showDialog.value = false },
                                onConfirm = {
                                    showDialog.value = false
                                    selectHandler(SelectEvent.SelectShowDialogShared(true))
                                },
                                confirmButtonText = "Ok",
                                dismissButtonText = "",
                                color = MaterialTheme.colorScheme.onError,
                                showDialog
                            )
                        }
                    }


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
        linkedApps?.let {
            ShareContentPagePopup(
                showPopup2,
                it,
                showDialogShared,
                { selectHandler(SelectEvent.SelectShowDialogShared(true)) },
                navController,shareContentViewModel)
        }

    }
}




@Preview(showBackground = true)
@Composable
fun ExerciseDetailsScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel:GeneralViewModel = hiltViewModel()
    val shareContentViewModel:ShareContentViewModel = hiltViewModel()
    ExerciseDetailsScreen(
        navController = navController,
        generalViewModel = generalViewModel,
        shareContentViewModel = shareContentViewModel,
        selectHandler= {  }

    )
}

