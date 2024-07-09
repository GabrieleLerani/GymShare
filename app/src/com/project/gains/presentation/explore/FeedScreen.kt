package com.project.gains.presentation.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.GymPost
import com.project.gains.presentation.components.LogoUser
import com.project.gains.presentation.components.SearchAppBar
import com.project.gains.presentation.navgraph.Route
import com.project.gains.theme.GainsAppTheme

@Composable
fun FeedScreen(
    navController: NavController,
    feedViewModel: FeedViewModel
) {
    val gymPosts by feedViewModel.posts.observeAsState()

    // Assuming you have a function to fetch gym posts from an API or elsewhere
    // You'll need to implement this function accordingly
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
                        text = "",
                        placeholder = "Search here...",
                        onTextChange = {},
                        onCloseClicked = {},
                        onSearchClicked = {},
                        onClick = {
                            navController.navigate(Route.SearchScreen.route)
                        },
                        enabled = false
                    )
                }

                gymPosts?.forEach{gymPost ->
                    item { FeedPost(gymPost) }
                }
            }
        }
    }
}

@Composable
fun FeedPost(gymPost: GymPost) {
    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                )
                Text(
                    text = gymPost.time,
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.width(150.dp))
            Image(
                painter = painterResource(id = gymPost.randomSocialId),
                contentDescription = "Social Icon",
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

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
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Text Content Section
        Text(
            text = gymPost.caption,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
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
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = gymPost.likes,
                    fontSize = 14.sp,
                )
            }

            // Comments Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Comment,
                    contentDescription = "Comment Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = gymPost.comment,
                    fontSize = 14.sp,
                )
            }
        }
    }
    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    val navController = rememberNavController()
    val feedViewModel: FeedViewModel = hiltViewModel()
    FeedScreen(
        navController = navController,
        feedViewModel = feedViewModel
    )
}