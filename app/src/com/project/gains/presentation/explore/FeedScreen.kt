package com.project.gains.presentation.explore

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.GymPost
import com.project.gains.presentation.components.FeedSearchBar
import com.project.gains.presentation.components.LogoUser
import com.project.gains.presentation.components.SearchViewModel
import com.project.gains.presentation.components.SocialMediaIcon
import com.project.gains.presentation.components.events.ManageCategoriesEvent
import com.project.gains.presentation.explore.events.SearchEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.theme.GainsAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FeedScreen(
    navController: NavController,
    feedViewModel: FeedViewModel,
    searchViewModel: SearchViewModel,
    searchGymPostHandler: (SearchEvent.SearchGymPostEvent) -> Unit,
    resetGymPostHandler: (SearchEvent.ResetPostEvent) -> Unit,
    assignCategoryHandler: (ManageCategoriesEvent.AssignCategoryEvent) -> Unit,

    ) {
    val categories = searchViewModel.categories
    val selectedCategory by searchViewModel.selectedCategory.observeAsState()
    val gymPosts by feedViewModel.posts.observeAsState()
    val listState = rememberLazyListState()
    val isFabExpanded by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 }}
    GainsAppTheme {

        Scaffold(
            floatingActionButton = { ExtendedFloatingActionButton(
                text = {Text(
                    text = "New post",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )},
                onClick = {
                    navController.navigate(Route.PostScreen.route)
                },
                icon = {Icon(
                    Icons.Default.AddComment,
                    contentDescription = "New post",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )},

                containerColor = MaterialTheme.colorScheme.primaryContainer,
                expanded = isFabExpanded,
                //modifier = Modifier.padding(8.dp)
            )}
        ) {

            Box(
                Modifier
                    .fillMaxSize()
                    .semantics { isTraversalGroup = true }) {
                FeedSearchBar(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .semantics { traversalIndex = 0f },
                    categories = categories,
                    selectedCategory = selectedCategory.toString(),
                    searchGymPostHandler = searchGymPostHandler,
                    resetGymPostHandler = resetGymPostHandler,
                    assignCategoryHandler = assignCategoryHandler
                )

                LazyColumn(
                    modifier = Modifier.semantics { traversalIndex = 1f },
                    contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = listState
                ) {

                    gymPosts?.forEach{gymPost ->
                        item {
                            FeedPost(gymPost)
                        }
                    }
                }

            }
        }


    }
}



@Composable
fun FeedPost(gymPost: GymPost) {

    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {

        // Header Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            // User Info Section
            LogoUser(modifier = Modifier.size(60.dp), gymPost.userResourceId) {
                // Placeholder for user logo
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = gymPost.username,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = gymPost.time,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(150.dp))
            SocialMediaIcon(icon = gymPost.randomSocialId, onClick = {}, isSelected = false)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Image Section
        Image(
            painter = painterResource(id = gymPost.imageResourceId),
            contentDescription = "Post Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Text Content Section
        Text(
            text = gymPost.caption,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Separator

        // Interaction Section (Likes and Comments)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            // Likes Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like Icon",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = gymPost.likes,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Comments Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Comment,
                    contentDescription = "Comment Icon",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = gymPost.comment,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
    Divider(color = Color.Gray, thickness = 0.2.dp, modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    val navController = rememberNavController()
    val feedViewModel: FeedViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    FeedScreen(
        navController = navController,
        feedViewModel = feedViewModel,
        resetGymPostHandler = {},
        assignCategoryHandler = {},
        searchGymPostHandler = {},
        searchViewModel = searchViewModel
    )
}