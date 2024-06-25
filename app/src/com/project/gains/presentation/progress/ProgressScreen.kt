package com.project.gains.presentation.progress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.data.generateRandomPlots
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.LogoUser
import com.project.gains.presentation.components.ProgressChartCard
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.theme.GainsAppTheme

@Composable
fun ProgressScreen(
    navController: NavController,
    selectHandler: (SelectEvent) -> Unit,
    generalViewModel: GeneralViewModel

) {
    val plots = generateRandomPlots()

    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = "Account Setting" ,
                    button= {
                        LogoUser(
                            modifier = Modifier.size(60.dp), R.drawable.pexels5
                        ) { navController.navigate(Route.AccountScreen.route) }
                    }
                )
            },            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    ) {
                    plots.forEach{plot ->

                        item{
                            ProgressChartCard(plot.preview) {
                                navController.navigate(Route.ProgressDetailsScreen.route)
                                selectHandler(
                                    SelectEvent.SelectPlotPreview(
                                        plot.preview
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp)) // Add spacing between cards
                        }
                    }

                    }

                }
            }
        }
    }

@Preview(showBackground = true)
@Composable
fun ProgressScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel:GeneralViewModel = hiltViewModel()
    ProgressScreen(
        navController = navController,
        selectHandler = {  },
        generalViewModel

    )
}