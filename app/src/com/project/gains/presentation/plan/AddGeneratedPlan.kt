package com.project.gains.presentation.plan

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.gains.data.ExerciseType
import com.project.gains.data.Option
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.generateOptions
import com.project.gains.presentation.Dimension
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.onboarding.components.PagerIndicator
import com.project.gains.presentation.plan.components.OnGeneratedPage
import com.project.gains.theme.GainsAppTheme
import kotlinx.coroutines.launch

// TODO check entirely
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddGeneratedPlan(
    navController: NavController,
) {
    val allOptions = remember { generateOptions() } // List to store selected options
    val options = remember { mutableStateListOf<Option>() } // List to store selected options
    val selectedExerciseTypes = remember { mutableStateListOf<ExerciseType>() } // List to store selected options
    val selectedMetrics = remember { mutableStateListOf<TrainingMetricType>() } // List to store selected options
    val selectedMusic = remember { mutableStateOf(false) } // List to store selected options
    val selectedBackup = remember { mutableStateOf(false) } // List to store selected options

    // Function to handle checkbox state change
    fun onOptionSelected(option: Option, isChecked: Boolean) {
        if (isChecked) {
            options.add(option)
        } else {
            options.remove(option)
        }
    }

    GainsAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            val pagerState = rememberPagerState(initialPage = 0) {
                pages.size
            }
            val buttonsState = remember {
                derivedStateOf {
                    when (pagerState.currentPage) {
                        0 -> listOf("Back", "Next")
                        1 -> listOf("Back", "Next")
                        2 -> listOf("Back", "Next")
                        else -> listOf("", "")
                    }
                }
            }

            HorizontalPager(state = pagerState) { index ->
                OnGeneratedPage(page = pages[index])
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimension.MediumPadding2)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PagerIndicator(
                    pageSize = pages.size,
                    selectedPage = pagerState.currentPage,
                    selectedColor = MaterialTheme.colorScheme.onPrimary,
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val scope = rememberCoroutineScope()
                    //Hide the button when the first element of the list is empty
                    if (buttonsState.value[0].isNotEmpty()) {
                        OnBoardingTextButton(
                            text = buttonsState.value[0],
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        page = pagerState.currentPage - 1
                                    )
                                }

                            }
                        )
                    }
                    OnBoardingButton(
                        text = buttonsState.value[1],
                        onClick = {
                            scope.launch {
                                if (pagerState.currentPage == 3){
                                    // save a value in datastore preferences
                                    // we launch an event that will be captured by the view model
                                    event(OnBoardingEvent.SaveAppEntry)
                                    // navigate to the main screen

                                    navController.navigate(Route.SignInScreen.route)


                                }else{
                                    pagerState.animateScrollToPage(
                                        page = pagerState.currentPage + 1
                                    )
                                }
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.5f))
        }
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
                    text = "Create New Plan",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            item {
                Text(
                    text = "Set the following options and press the generate plan button to create a personalized workout plan based on your needs.",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                Spacer(
                    modifier = Modifier.height(
                        10.dp
                    )
                )
            }
            item {
                Text(
                    text = "Choose if you want to have music while training",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        ) // Optional background for emphasis
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[0],
                    onOptionSelected = { isChecked ->
                        selectedMusic.value = true
                        onOptionSelected(
                            allOptions[0],
                            isChecked
                        )
                    }
                )
            }
            item {
                Text(
                    text = "Choose if you want to have backup on your workout",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[1],
                    onOptionSelected = { isChecked ->
                        selectedBackup.value = true
                        onOptionSelected(
                            allOptions[1],
                            isChecked
                        )
                    }
                )
            }
            item {
                Text(
                    text = "Choose the metrics to track in your progress",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[2],
                    onOptionSelected = { isChecked ->
                        selectedMetrics.add(TrainingMetricType.BPM)
                        onOptionSelected(
                            allOptions[2],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[3],
                    onOptionSelected = { isChecked ->
                        selectedMetrics.add(TrainingMetricType.KCAL)

                        onOptionSelected(
                            allOptions[3],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[4],
                    onOptionSelected = { isChecked ->
                        selectedMetrics.add(TrainingMetricType.DURATION)

                        onOptionSelected(
                            allOptions[4],
                            isChecked
                        )
                    }
                )
            }
            item {
                Text(
                    text = "Choose the muscle groups to include in your plan",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[5],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.CHEST)
                        onOptionSelected(
                            allOptions[5],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[6],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.BACK)

                        onOptionSelected(
                            allOptions[6],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[7],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.SHOULDERS)

                        onOptionSelected(
                            allOptions[7],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[8],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.ARMS)

                        onOptionSelected(
                            allOptions[8],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[9],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.LEGS)
                        onOptionSelected(
                            allOptions[9],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[10],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.CORE)
                        onOptionSelected(
                            allOptions[10],
                            isChecked
                        )
                    }
                )
            }


            item { Spacer(modifier = Modifier.height(10.dp)) }


            item {
                Button(
                    onClick = {
                        createHandler(
                            CreateEvent.CreatePlan(
                                selectedMetrics,
                                selectedExerciseTypes,
                                selectedMusic.value,
                                selectedBackup.value
                            )
                        )

                        selectHandler(SelectEvent.SelectPlanPopup(false))
                        onItemClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(
                        text = "GENERATE PLAN",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

        }
    }
}

@Composable
fun OptionCheckbox(
    option: Option,
    onOptionSelected: (Boolean) -> Unit
) {
    val isChecked = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = option.name,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = {
                    isChecked.value = it
                    onOptionSelected(it)
                },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedColor = MaterialTheme.colorScheme.primary,
                    checkedColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}