package com.project.gains.presentation.explore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.gains.data.Categories
import com.project.gains.presentation.components.ResearchFilter
import com.project.gains.presentation.components.SearchAppBar
import com.project.gains.theme.GainsAppTheme

@Composable
fun SearchScreen() {
    val categories = listOf(Categories.User, Categories.Workout, Categories.Keyword, Categories.Social)
    var selectedCategories by remember { mutableStateOf(listOf<Categories>()) }

    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .fillMaxSize() ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SearchAppBar(
                        text = "Look for posts on other social media",
                        onTextChange = {},
                        onCloseClicked = {},
                        onSearchClicked = {}
                    )
                }
                item {
                    ResearchFilter(
                        categories = categories,
                        selectedCategories = selectedCategories,
                        onCategorySelected = { category ->
                            selectedCategories = if (selectedCategories.contains(category)) {
                                selectedCategories - category
                            } else {
                                selectedCategories + category
                            }
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
    SearchScreen()
}