package com.project.gains.presentation.plan.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.project.gains.R
import com.project.gains.presentation.Dimension
import com.project.gains.presentation.plan.PlanPage
import com.project.gains.presentation.plan.pages
import com.project.gains.theme.GainsAppTheme

// TODO check which composable is correct
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewPlanPage(
    pagerState: PagerState,
    page: PlanPages,
    createHandler:(CreateEvent)->Unit,
    selectHandler:(SelectEvent)->Unit,

    navController: NavController,
    clicked: Boolean?

) {

    val selectedLevel = remember{ mutableStateOf(Level.BEGINNER) }
    val selectedLPeriod = remember{ mutableStateOf(PeriodMetricType.WEEK) }
    val selectedTraining = remember{ mutableStateOf(TrainingType.STRENGTH) }


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 290.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {selectHandler(SelectEvent.SelectPlanPopup(false)) }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        }
        item { Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium
        ) }
        page.pages.forEach{ content ->

            item {
                val scope = rememberCoroutineScope()


                GeneralCard(imageResId = content.image, title = content.title){
                    scope.launch {
                        when (pagerState.currentPage) {

                            0 ->{ pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1
                            )}
                            1 -> {pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1
                            )}
                            2 -> {
                                createHandler(CreateEvent.SetPlanOptions(
                                    selectedLevel=selectedLevel.value,
                                    selectedPeriod=selectedLPeriod.value,
                                    selectedTrainingType=selectedTraining.value))
                                selectHandler(SelectEvent.SelectClicked(true))
                                selectHandler(SelectEvent.SelectShowPopup4(false))
                                selectHandler(SelectEvent.SelectShowPopup3(true))

                            }
                            else -> ""
                        }
                    }
                }}
        }
    }
}


@Composable
fun OnGeneratedPage(
    modifier: Modifier = Modifier,
    page: PlanPage,
) {
    Column(modifier = modifier){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(Dimension.MediumPadding1))
        Text(
            text = page.title,
            modifier= Modifier.padding(horizontal = Dimension.MediumPadding2),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.black)
        )
        Text(
            text = page.description,
            modifier= Modifier.padding(horizontal = Dimension.MediumPadding2),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.black)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview(){
    GainsAppTheme {
        com.project.gains.presentation.onboarding.components.OnBoardingPage(page = pages[0])
    }
}