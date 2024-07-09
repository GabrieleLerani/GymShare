package com.project.gains.presentation.plan.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.project.gains.data.TrainingType
import com.project.gains.presentation.plan.PlanPages
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.gains.data.Frequency
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.presentation.GeneralCard
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManagePlanEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnGeneratedPage(
    pagerState: PagerState,
    page: PlanPages,
    navController: NavController,
    planOptionsHandler: (ManagePlanEvent.SetPlanOptions) -> Unit
) {

    val selectedLevel = remember { mutableStateOf(Level.BEGINNER) }
    val selectedPeriod = remember { mutableStateOf(PeriodMetricType.WEEK) }
    val selectedTraining = remember { mutableStateOf(TrainingType.STRENGTH) }
    val selectedFrequency = remember { mutableStateOf(Frequency.THREE) }


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
                IconButton(onClick = {
                    navController.navigate(Route.HomeScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        }
        item {
            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        page.pages.forEach{ content ->

            item {
                val scope = rememberCoroutineScope()

                GeneralCard(imageResId = content.image, title = content.title) {
                    scope.launch {
                        when (pagerState.currentPage) {
                            0 -> {
                                selectedLevel.value = content.level
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1
                                )
                            }
                            1 -> {
                                selectedTraining.value = content.trainingType
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1
                                )
                            }
                            2 -> {
                                selectedFrequency.value = content.frequency
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1
                                )
                            }
                            3 -> {
                                selectedPeriod.value = content.periodMetricType
                                planOptionsHandler(ManagePlanEvent.SetPlanOptions(
                                    selectedLevel = selectedLevel.value,
                                    selectedPeriod = selectedPeriod.value,
                                    selectedTrainingType = selectedTraining.value,
                                    selectedFrequency=selectedFrequency.value
                                ))
                                navController.navigate(Route.LastNewPlanScreen.route)
                            }
                        }
                    }
                }
            }
        }
    }
}