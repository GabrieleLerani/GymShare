package com.project.gains.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.slidingLineTransition(pagerState: PagerState, distance: Float) =
    graphicsLayer {
        val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
        translationX = scrollPosition * distance
    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreenSlidingComponent(
    spacing: Dp,
    dotWidth: Dp,
    dotHeight: Dp,
    inactiveColor: Color,
    activeColor: Color,
    pagerState: PagerState,
    distance: Float,
) {

    val count = pagerState.pageCount

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Text showing the current page out of the total count
        Text(
            text = "${pagerState.currentPage + 1} of $count",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val scope = rememberCoroutineScope()

                repeat(count) {int ->
                    Box(
                        modifier = Modifier
                            .size(width = dotWidth, height = dotHeight)
                            .background(
                                color = inactiveColor,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        page = int
                                    )

                                }
                            }
                    )
                }
            }

            Box(
                Modifier
                    .slidingLineTransition(pagerState, distance)
                    .size(width = dotWidth, height = dotHeight)
                    .background(
                        color = activeColor,
                        shape = RoundedCornerShape(5.dp),
                    )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanSlidingComponent(
    inactiveColor: Color,
    activeColor: Color,
    pagerState: PagerState,
    modifier : Modifier
) {
    val count = pagerState.pageCount
    val targetProgress = (pagerState.currentPage + 1) / count.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 500),
        label = "effect"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Text showing the current page out of the total count
        Text(
            text = "${pagerState.currentPage + 1} of $count",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LinearProgressIndicator(
            progress = animatedProgress,
            color = activeColor,
            backgroundColor = inactiveColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewSlidingComponent2() {
    val pagerState = rememberPagerState(pageCount = { 6 })

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        PlanSlidingComponent(
            inactiveColor = Color.Gray,
            activeColor = Color.Blue,
            pagerState = pagerState,
            modifier = Modifier.padding()
        )
        Spacer(modifier = Modifier.padding(5.dp))

        HorizontalPager(state = pagerState) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (page % 2 == 0) Color.LightGray else Color.DarkGray)
            )
        }
    }
}

