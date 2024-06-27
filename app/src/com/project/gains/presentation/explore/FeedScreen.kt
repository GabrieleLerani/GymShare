package com.project.gains.presentation.explore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.FeedPost
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.LogoUser
import com.project.gains.presentation.events.ShareContentEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.theme.GainsAppTheme

@Composable
fun FeedScreen(
    navController: NavController,
    shareHandler: (ShareContentEvent) -> Unit,
    generalViewModel: GeneralViewModel
) {
    val gymPosts by generalViewModel.posts.observeAsState()


    // Assuming you have a function to fetch gym posts from an API or elsewhere
    // You'll need to implement this function accordingly
    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = " Explore Feed" ,
                    button= {
                        LogoUser(
                            modifier = Modifier.size(60.dp), R.drawable.pexels5
                        ) { navController.navigate(Route.AccountScreen.route) }
                    },
                    button1 = {}
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
                        .fillMaxSize() ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    gymPosts?.forEach{gymPost ->
                        item { FeedPost(gymPost) }
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel :GeneralViewModel= hiltViewModel()
    FeedScreen(
        navController = navController,
        shareHandler = {},
        generalViewModel
    )
}