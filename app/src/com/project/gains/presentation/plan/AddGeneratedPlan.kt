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
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.onboarding.components.OnBoardingButton
import com.project.gains.presentation.onboarding.components.OnBoardingTextButton
import com.project.gains.presentation.onboarding.components.PagerIndicator
import com.project.gains.presentation.plan.components.OnGeneratedPage
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.theme.GainsAppTheme
import kotlinx.coroutines.launch

// TODO test it because it can be completely broken
// TODO if it is, then insert the pages with checkboxes inside the onboardingpages
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddGeneratedPlan(
    navController: NavController,
    planOptionsHandler: (ManagePlanEvent.SetPlanOptions) -> Unit,
    createPlanHandler: (ManagePlanEvent.CreatePlan) -> Unit
) {


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
                OnGeneratedPage(
                    pagerState = pagerState,
                    page = pages[index],
                    navController = navController,
                    planOptionsHandler = planOptionsHandler
                )
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

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val scope = rememberCoroutineScope()
                    OnBoardingButton(
                        text = buttonsState.value[0],
                        onClick = {
                            scope.launch {
                                // if the current page is not the first one, then simply go back
                                // otherwise go back to the choice screen
                                if (pagerState.currentPage != 0) {
                                    pagerState.animateScrollToPage(
                                        page = pagerState.currentPage - 1
                                    )
                                } else {
                                    navController.navigate(Route.NewPlanScreen.route)
                                }
                            }
                        }
                    )
                }
                PagerIndicator(
                    pageSize = pages.size,
                    selectedPage = pagerState.currentPage,
                    selectedColor = MaterialTheme.colorScheme.inversePrimary,
                )
            }
            Spacer(modifier = Modifier.weight(0.5f))
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