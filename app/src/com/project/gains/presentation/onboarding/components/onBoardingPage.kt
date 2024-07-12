package com.project.gains.presentation.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    modifier: Modifier = Modifier,
    page: Page,
) {
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
        item {
            Column(modifier = modifier) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.60f),
                    painter = painterResource(id = page.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    text = page.title,
                    modifier = Modifier.padding(horizontal = MediumPadding2),
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    text = page.description,
                    modifier = Modifier.padding(horizontal = MediumPadding2),
                    style = MaterialTheme.typography.bodyMedium,
                )

            }
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


                    val scope = rememberCoroutineScope()

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


