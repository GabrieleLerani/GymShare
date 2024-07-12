package com.project.gains.presentation.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.gains.presentation.Dimension.MediumPadding1
import com.project.gains.presentation.Dimension.MediumPadding2
import com.project.gains.presentation.onboarding.Page
import com.project.gains.presentation.onboarding.pages

import com.project.gains.R
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.onboarding.OnBoardingEvent
import com.project.gains.presentation.plan.components.GeneralCard
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.theme.GainsAppTheme
import kotlinx.coroutines.launch

/*
* On boarding page composable, is the onboarding pages shown the first time you enter in the application
* */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingPage(
    onClickHandler:()->Unit,
    pagerState: PagerState,
    page: Page,
) {
    val rememberPage = remember { page }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()


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
                    modifier = Modifier
                        .padding(16.dp),
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                )
                Spacer(modifier = Modifier.padding(30.dp))
            }

        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .height(400.dp)
                    .background(Color.Gray, RoundedCornerShape(16.dp))
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = rememberPage.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                }
                Text(
                    text = rememberPage.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )

            }

        item {
            if (pagerState.currentPage==2) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OnBoardingButton(
                        text = "Start Gym Share!",
                        onClick = {
                            scope.launch {
                                    onClickHandler()
                            }
                        }
                    )
                }
            }


        }
    }
}


