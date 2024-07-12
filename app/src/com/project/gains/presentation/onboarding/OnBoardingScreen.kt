package com.project.gains.presentation.onboarding
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.project.gains.presentation.Dimension
import com.project.gains.presentation.Dimension.dotHeight
import com.project.gains.presentation.Dimension.dotWidth
import com.project.gains.presentation.Dimension.spacing
import com.project.gains.presentation.components.SlidingComponent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.onboarding.components.OnBoardingButton
import com.project.gains.presentation.onboarding.components.OnBoardingPage
import com.project.gains.presentation.onboarding.components.PagerIndicator
import com.project.gains.theme.GainsAppTheme

/*
* Composable to combine all the OnBoarding components*/
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit, navController: NavController
) {
    val distance = with(LocalDensity.current) { (dotWidth + spacing).toPx() }
    val pagerState = rememberPagerState(initialPage = 0) { pages.size }

    GainsAppTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.surface,
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {


                SlidingComponent(
                    spacing = spacing,
                    dotWidth = dotWidth,
                    dotHeight = dotHeight,
                    inactiveColor = Color.Gray,
                    activeColor = MaterialTheme.colorScheme.inversePrimary,
                    pagerState = pagerState,
                    distance = distance
                )

                Spacer(modifier = Modifier.padding(top = 10.dp))

                HorizontalPager(state = pagerState) { index ->
                    OnBoardingPage(page = pages[index], pagerState = pagerState, onClickHandler = {
                        event(OnBoardingEvent.SaveAppEntry)
                        // navigate to the main screen
                        navController.navigate(Route.SignInScreen.route)
                    })
                }


            }
        }
    }
}
