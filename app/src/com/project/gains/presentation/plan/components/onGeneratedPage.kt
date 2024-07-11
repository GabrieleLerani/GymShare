package com.project.gains.presentation.plan.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.gains.data.Frequency
import com.project.gains.data.Level
import com.project.gains.data.TrainingType
import com.project.gains.presentation.navgraph.Route
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
                            planOptionsHandler(ManagePlanEvent.SetPlanOptions(
                                selectedLevel = selectedLevel.value,
                                selectedTrainingType = selectedTraining.value,
                                selectedFrequency = selectedFrequency.value
                            ))

                            navController.navigate(Route.LastNewPlanScreen.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GeneralCard(imageResId: Int, title: String, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
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