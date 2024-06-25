package com.project.gains.presentation.plan
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.TrainingType
import kotlinx.coroutines.launch
import com.project.gains.presentation.Dimension
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.onboarding.components.NewPlanPage
import com.project.gains.presentation.onboarding.components.OnBoardingButton
import com.project.gains.presentation.onboarding.components.OnBoardingTextButton
import com.project.gains.presentation.onboarding.components.PagerIndicator
import com.project.gains.theme.GainsAppTheme

/*
* Composable to combine all the OnBoarding components*/
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun OnBoardingScreen(
    createHandler: (CreateEvent) -> Unit, navController: NavController
) {
    GainsAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            val pagerState = rememberPagerState(initialPage = 0) {
                pages.size
            }
            val buttonsState = remember {
                derivedStateOf {
                    when (pagerState.currentPage) {
                        0 -> listOf("", "Next")
                        1 -> listOf("Back", "Next")
                        2 -> listOf("Back", "Next")
                        3 -> listOf("Back", "Get Started")
                        else -> listOf("", "")
                    }
                }
            }

            HorizontalPager(state = pagerState) { index ->
                NewPlanPage(page = pages[index], navController = navController)
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
                    selectedPage = pagerState.currentPage
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
                                    // TODO
                                    createHandler(CreateEvent.SetPlanOptions(Level.BEGINNER,PeriodMetricType.MONTH,TrainingType.STRENGTH))
                                    navController.navigate(Route.PlanScreen.route)


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


}

data class PlanPages(
    val title: String,
    val pages:MutableList<PlanPage>
)

data class PlanPage(
    val title: String,
    @DrawableRes val image: Int
)


val pages = listOf(
    PlanPages(
        "Select what you think is your level",
        mutableListOf<PlanPage>(
            PlanPage( title = Level.BEGINNER.toString(),
            image = R.drawable.pexels1),
            PlanPage( title = Level.INTERMEDIATE.toString(),
                image = R.drawable.pexels1),
            PlanPage( title = Level.EXPERT.toString(), image = R.drawable.pexels1))),
        PlanPages(
            "Select the training type",
            mutableListOf<PlanPage>(
                PlanPage( title = TrainingType.STRENGTH.toString(),
                    image = R.drawable.pexels1),
                PlanPage( title = TrainingType.CALISTHENICS.toString(),
                    image = R.drawable.pexels1),
                PlanPage( title =TrainingType.CROSSFIT.toString(), image = R.drawable.pexels1))),
        PlanPages(
            "Select the training plan period",
            mutableListOf<PlanPage>(
                    PlanPage( title = PeriodMetricType.WEEK.toString(),
                        image = R.drawable.pexels1),
                    PlanPage( title =PeriodMetricType.MONTH.toString(),
                        image = R.drawable.pexels1),
                    PlanPage( title = PeriodMetricType.YEAR.toString(), image = R.drawable.pexels1)))
    )

@Preview(showBackground = true)
@Composable
fun NewPlanPagePreview(){
    GainsAppTheme {
        OnBoardingScreen({}, rememberNavController())
    }
}