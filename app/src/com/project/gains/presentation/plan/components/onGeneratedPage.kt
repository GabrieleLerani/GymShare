package com.project.gains.presentation.plan.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.gains.data.Frequency
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.TrainingType
import com.project.gains.presentation.components.GeneralCard
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.PlanPages
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.presentation.plan.pages
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

    val rememberPage = remember {
        page
    }

    val listState = rememberLazyListState()


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        state = listState
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = rememberPage.title,
                    style = MaterialTheme.typography.headlineMedium,
                )

                Spacer(modifier = Modifier.padding(30.dp))
            }

        }

        items(rememberPage.pages) { content ->
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