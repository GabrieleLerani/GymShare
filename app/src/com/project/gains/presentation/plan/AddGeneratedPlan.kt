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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.Option
import com.project.gains.presentation.Dimension.dotHeight
import com.project.gains.presentation.Dimension.dotWidth
import com.project.gains.presentation.Dimension.spacing
import com.project.gains.presentation.components.SlidingComponent
import com.project.gains.presentation.plan.components.OnGeneratedPage
import com.project.gains.presentation.plan.events.ManagePlanEvent

// TODO test it because it can be completely broken
// TODO if it is, then insert the pages with checkboxes inside the onboardingpages
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddGeneratedPlan(
    navController: NavController,
    planOptionsHandler: (ManagePlanEvent.SetPlanOptions) -> Unit,
    createPlanHandler: (ManagePlanEvent.CreatePlan) -> Unit
) {


    val pagerState = rememberPagerState(initialPage = 0) { pages.size }
    val distance = with(LocalDensity.current) { (dotWidth + spacing).toPx() }

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



@Preview(showBackground = true)
@Composable
fun PreviewAddGeneratedPlan() {
    val navController = rememberNavController()
    AddGeneratedPlan(
        navController = navController,
        planOptionsHandler = { /* Handle plan options */ },
        createPlanHandler = { /* Handle plan creation */ }
    )
}