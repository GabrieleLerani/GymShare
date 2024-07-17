package com.project.gains.presentation.plan.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R
import com.project.gains.data.Frequency
import com.project.gains.data.Level
import com.project.gains.data.TrainingType
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.PlanPage
import com.project.gains.presentation.plan.PlanPages
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
    val selectedTraining = remember { mutableStateOf(TrainingType.STRENGTH) }
    val selectedFrequency = remember { mutableStateOf(Frequency.THREE) }

    val rememberPage = remember { page }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Track if an option has been selected
    val isOptionSelected = remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
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
                    GeneralCard(imageResId = content.image, title = content.title) {
                        scope.launch {
                            isOptionSelected.value = true
                            when (pagerState.currentPage) {
                                0 -> selectedLevel.value = content.level
                                1 -> selectedTraining.value = content.trainingType
                                2 -> selectedFrequency.value = content.frequency
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OutlinedButton(
                onClick =  {
                    scope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            isOptionSelected.value = false // Reset option selected state
                        }
                    }
                } ,
                modifier = Modifier
            ) {
                Text("Back")
            }

            Button(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < 2) {
                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            isOptionSelected.value = false // Reset option selected state
                        } else {
                            planOptionsHandler(ManagePlanEvent.SetPlanOptions(
                                selectedLevel = selectedLevel.value,
                                selectedTrainingType = selectedTraining.value,
                                selectedFrequency = selectedFrequency.value
                            ))
                            navController.navigate(Route.LastNewPlanScreen.route)
                        }
                    }
                },
                enabled = isOptionSelected.value
            ) {
                Text(text = "Next")
            }
        }
    }
}

@Composable
fun GeneralCard(imageResId: Int, title: String, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(150.dp)
            .background(Color.Gray, RoundedCornerShape(16.dp))
            .clickable {
                onItemClick()
            }
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)) // Clip to the rounded corners
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 300f
                    ),
                    RoundedCornerShape(16.dp)
                )
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewOnGeneratedPage() {
    // Mock data for preview
    val page = PlanPages(
        title = "Sample Plan Page",
        pages = listOf(
            PlanPage(image = R.drawable.pexels1, title = "Page 1", level = Level.BEGINNER, trainingType = TrainingType.STRENGTH, frequency = Frequency.THREE),
            PlanPage(image = R.drawable.pexels1, title = "Page 2", level = Level.INTERMEDIATE, trainingType = TrainingType.CROSSFIT, frequency = Frequency.TWO),
            PlanPage(image = R.drawable.pexels1, title = "Page 3", level = Level.EXPERT, trainingType = TrainingType.CALISTHENICS, frequency = Frequency.FOUR)
        )
    )
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { page.pages.size })//rememberPagerState(initialPage = 0)

    val navController = rememberNavController()
    val planOptionsHandler: (ManagePlanEvent.SetPlanOptions) -> Unit = {}

    OnGeneratedPage(
        pagerState = pagerState,
        page = page,
        navController = navController,
        planOptionsHandler = planOptionsHandler
    )
}
