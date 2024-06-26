package com.project.gains.presentation.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.TrainingType

import com.project.gains.presentation.components.GeneralCard
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.PlanPages
import com.project.gains.presentation.plan.pages
import com.project.gains.theme.GainsAppTheme
import kotlinx.coroutines.launch

/*
* On boarding page composable, is the onboarding pages shown the first time you enter in the application
* */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewPlanPage(
    pagerState: PagerState,
    page: PlanPages,
    createHandler:(CreateEvent)->Unit,
    navController: NavController,
    clicked: MutableState<Boolean>

) {

    val selectedLevel = remember{ mutableStateOf(Level.BEGINNER) }
    val selectedLPeriod = remember{ mutableStateOf(PeriodMetricType.WEEK) }
    val selectedTraining = remember{ mutableStateOf(TrainingType.STRENGTH) }


                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp)
                ) {
                    item { Text(
                        text = page.title,
                        style = MaterialTheme.typography.headlineMedium
                    ) }
                    page.pages.forEach{ content ->

                        item {
                            val scope = rememberCoroutineScope()

                            GeneralCard(imageResId = content.image, title = content.title){
                                scope.launch {
                                when (pagerState.currentPage) {

                                    0 ->{ pagerState.animateScrollToPage(
                                        page = pagerState.currentPage + 1
                                    )}
                                    1 -> {pagerState.animateScrollToPage(
                                        page = pagerState.currentPage + 1
                                    )}
                                    2 -> {
                                        createHandler(CreateEvent.SetPlanOptions(
                                            selectedLevel=selectedLevel.value,
                                            selectedPeriod=selectedLPeriod.value,
                                            selectedTrainingType=selectedTraining.value))
                                        navController.navigate(Route.PlanScreen.route)
                                        clicked.value=true
                                    }
                                    else -> ""
                                }
                            }
                            }}
                        }
                    }
                }



@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun NewPlanPagePreview(){
    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }
    GainsAppTheme {
        NewPlanPage(pagerState, pages[0],{}, rememberNavController(), remember {
            mutableStateOf(true)})
    }
}