package com.project.gains.presentation.onboarding
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.project.gains.presentation.Dimension
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.onboarding.components.OnBoardingButton
import com.project.gains.presentation.onboarding.components.OnBoardingPage
import com.project.gains.presentation.onboarding.components.OnBoardingTextButton
import com.project.gains.presentation.onboarding.components.PagerIndicator
import com.project.gains.theme.GainsAppTheme

/*
* Composable to combine all the OnBoarding components*/
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit, navController: NavController
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
                OnBoardingPage(page = pages[index])
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
                    pageSize = com.project.gains.presentation.plan.pages.size,
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


}