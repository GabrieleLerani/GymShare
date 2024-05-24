package com.project.gains.presentation.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.GymPostsList
import com.project.gains.presentation.events.ShareContentEvent
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
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()

                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TopBar(userProfile = null, navController = navController)
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            androidx.compose.material3.Text(
                                text = "Your Posts",
                                style = MaterialTheme.typography.headlineMedium,
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }
                        GymPostsList(gymPosts,shareHandler)
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