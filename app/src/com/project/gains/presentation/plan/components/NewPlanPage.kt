package com.project.gains.presentation.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.project.gains.presentation.onboarding.pages

import com.project.gains.R
import com.project.gains.presentation.components.GeneralCard
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.PlanPages
import com.project.gains.theme.GainsAppTheme

/*
* On boarding page composable, is the onboarding pages shown the first time you enter in the application
* */
@Composable
fun NewPlanPage(
    modifier: Modifier = Modifier,
    page: PlanPages,
    navController:NavController
) {

    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = page.title,
                    button= {
                    }
                )
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center

                ) {
                    page.pages.forEach{ content ->
                        item {
                            GeneralCard(imageResId = content.image, title = content.title){
                                navController.navigate(Route.ExerciseTypeScreen.route)
                            }
                        }
                    }
                }
            }

        }
    }

    }



@Preview(showBackground = true)
@Composable
fun NewPlanPagePreview(){
    GainsAppTheme {
        OnBoardingPage(page = pages[0])
    }
}