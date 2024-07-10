package com.project.gains.presentation.explore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.presentation.components.ResearchFilter
import com.project.gains.presentation.components.SearchAppBar
import com.project.gains.presentation.components.SearchViewModel
import com.project.gains.presentation.components.events.ManageCategoriesEvent
import com.project.gains.presentation.explore.events.SearchEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.theme.GainsAppTheme

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    searchGymPostHandler: (SearchEvent.SearchGymPostEvent) -> Unit,
    assignCategoryHandler: (ManageCategoriesEvent.AssignCategoryEvent) -> Unit,
    navController: NavController
) {
    val placeholder = "Look for posts on other social media"
    val categories = searchViewModel.categories

    var text by remember { mutableStateOf("") }
    val selectedCategory by searchViewModel.selectedCategory.observeAsState()

    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize() ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SearchAppBar(
                        text = text,
                        placeholder = placeholder,
                        onTextChange = {
                            text = it
                        },
                        // TODO fill onCloseClicked
                        onCloseClicked = {},
                        onSearchClicked = {
                            searchGymPostHandler(SearchEvent.SearchGymPostEvent(text = text, selectedCategory = selectedCategory))
                            navController.navigate(Route.FeedScreen.route)
                        },
                        // this is empty because it is used for a different purpose
                        onClick = {},
                        enabled = true
                    )
                }
                item {
                    ResearchFilter(
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { category ->
                            assignCategoryHandler(ManageCategoriesEvent.AssignCategoryEvent(category))
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val navController = rememberNavController()
    SearchScreen(
        searchViewModel = searchViewModel,
        searchGymPostHandler = {},
        assignCategoryHandler = {},
        navController = navController
    )
}