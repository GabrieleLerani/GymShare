package com.project.gains.presentation.plan

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.presentation.components.SlidingComponent
import com.project.gains.presentation.plan.components.OnGeneratedPage
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.theme.GainsAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddGeneratedPlan(
    navController: NavController,
    planOptionsHandler: (ManagePlanEvent.SetPlanOptions) -> Unit
) {

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
                    //.padding(10.dp)
            ) {

                SlidingComponent(
                    inactiveColor = MaterialTheme.colorScheme.secondaryContainer,
                    activeColor = MaterialTheme.colorScheme.primary,
                    pagerState = pagerState,
                    modifier = Modifier.padding(20.dp),
                )

                Spacer(modifier = Modifier.padding(top = 10.dp))

                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) { index ->
                    OnGeneratedPage(
                        pagerState = pagerState,
                        page = pages[index],
                        navController = navController,
                        planOptionsHandler = planOptionsHandler
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddGeneratedPlan() {
    val navController = rememberNavController()
    AddGeneratedPlan(
        navController = navController,
        planOptionsHandler = { /* Handle plan options */ }
    )
}