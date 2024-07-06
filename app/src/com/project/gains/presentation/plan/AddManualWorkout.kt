package com.project.gains.presentation.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.gains.GeneralViewModel
import com.project.gains.data.Exercise
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.DeleteExerciseButton
import com.project.gains.presentation.events.SelectEvent

@Composable
fun AddManualWorkout(
    generalViewModel: GeneralViewModel
){
    var workoutTitle by remember { mutableStateOf(TextFieldValue("")) }
    val workoutTitleStored by generalViewModel.workoutTitle.observeAsState()
    val selectedExercises by generalViewModel.addedExercises.observeAsState()

    val removedExercises = remember {
        mutableStateOf(listOf<Exercise>())
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(20.dp)
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 290.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { selectHandler(SelectEvent.SelectPlanPopup(false)) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon"
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Create Your Workout!",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            item {
                Text(
                    text = "Manually add each exercise to your workout day, press the button + to search for an exercise and then click save button to submit",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {

                (if (workoutTitle.text == "") workoutTitleStored else workoutTitle)?.let {
                    OutlinedTextField(
                        value = it,
                        onValueChange = { newValue ->
                            workoutTitle = newValue
                            selectHandler(SelectEvent.SelectWorkoutStored(newValue))
                        },
                        label = {
                            Text(
                                "Enter workout name...",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        },
                        shape = RoundedCornerShape(size = 20.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface), // Set the text color to white

                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimary, // Set the contour color when focused
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary // Set the contour color when not focused
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    selectedExercises?.forEach { exercise ->
                        println(removedExercises)
                        if (!removedExercises.value.contains(exercise)) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AddExerciseItem(
                                    modifier = Modifier.weight(1f),
                                    exercise = exercise,
                                    onItemClick = {},
                                    onItemClick2 = {},
                                    isSelected = true,
                                    isToAdd = false
                                )
                                Spacer(modifier = Modifier.width(5.dp))

                                DeleteExerciseButton {
                                    removedExercises.value = listOf(exercise)
                                    selectHandler(SelectEvent.RemoveExerciseToAdd(exercise))
                                }

                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { /* Add exercise action */ }) {
                            Text(text = "Add new exercise", color = Color.Cyan)
                        }
                        Spacer(modifier = Modifier.width(80.dp))
                        Spacer(modifier = Modifier.width(20.dp))
                        AddExerciseButton {
                            onItemClick()
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                Button(
                    onClick = {
                        selectHandler(SelectEvent.SelectShowDialogWorkout(true))
                        selectHandler(SelectEvent.SelectPlanPopup(false))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(
                        text = "SAVE WORKOUT",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

        }
    }
}

@Composable
fun AddExerciseButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(color = Color.Cyan, shape = CircleShape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "+",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DeleteExerciseButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(color = Color.Red, shape = CircleShape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "-",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}