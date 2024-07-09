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
import com.project.gains.data.Categories
import com.project.gains.data.GymPost
import com.project.gains.data.Socials
import com.project.gains.presentation.components.ResearchFilter
import com.project.gains.presentation.components.SearchAppBar
import com.project.gains.presentation.components.SearchViewModel
import com.project.gains.presentation.components.events.ManageCategoriesEvent
import com.project.gains.theme.GainsAppTheme

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    feedViewModel: FeedViewModel,
    assignCategoryHandler: (ManageCategoriesEvent.AssignCategoryEvent) -> Unit,
) {
    val placeholder = "Look for posts on other social media"
    val categories = searchViewModel.categories

    var text by remember { mutableStateOf("") }
    val selectedCategory by searchViewModel.selectedCategory.observeAsState()
    val posts by feedViewModel.posts.observeAsState()

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
                    // TODO fill searchappbar
                    SearchAppBar(
                        text = text,
                        placeholder = placeholder,
                        onTextChange = {
                            text = it
                        },
                        onCloseClicked = {},
                        onSearchClicked = {
                            gymSearch(text, selectedCategory, posts)
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

fun gymSearch(text: String, selectedCategories: Categories?, posts: List<GymPost>?) {
    val results: MutableList<GymPost> = mutableListOf()

    when(selectedCategories) {
        Categories.User -> {
            posts?.forEach { post ->
                if (post.username.contains(text)) {
                    results.add(post)
                }
            }
        }
        Categories.Workout -> {
            posts?.forEach { post ->
                // TODO whenever a new post is added to the system, insert the "Workout" string inside the caption automatically
                if (post.caption.contains(text) && post.caption.contains("Workout")) {
                    results.add(post)
                }
            }
        }
        Categories.Keyword -> {
            posts?.forEach { post ->
                if (post.caption.contains(text)) {
                    results.add(post)
                }
            }
        }
        Categories.Social -> {
            posts?.forEach { post ->
                if (post.social.contains(text)) {
                    results.add(post)
                }
            }
        }
        else -> {
            posts?.forEach { post ->
                if (post.username.contains(text) ||
                    post.caption.contains(text) && post.caption.contains("Workout") ||
                    post.caption.contains(text) ||
                    post.social.contains(text)) {
                    results.add(post)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val feedViewModel: FeedViewModel = hiltViewModel()
    SearchScreen(
        searchViewModel = searchViewModel,
        feedViewModel = feedViewModel,
        assignCategoryHandler = {}
    )
}