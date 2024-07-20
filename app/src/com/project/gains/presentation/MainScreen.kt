package com.project.gains.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp


import com.project.gains.presentation.components.DynamicBottomBar
import com.project.gains.presentation.components.DynamicTopBar
import com.project.gains.presentation.components.currentRoute

import com.project.gains.presentation.navgraph.NavGraph
import com.project.gains.presentation.navgraph.Route
import com.project.gains.theme.GainsAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    startDestination: String,
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val messageState = remember { mutableStateOf("") } // Shared state for the message

    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
        
    val modifier = if (currentRoute(navController) == Route.FeedScreen.route){
        Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    } else { Modifier }

    GainsAppTheme {
        Scaffold(
            modifier = modifier,
            topBar = { DynamicTopBar(navController = navController) },
            bottomBar = { DynamicBottomBar(navController = navController, scrollBehavior = scrollBehavior) },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) {
                Snackbar(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    action = {
                        IconButton(onClick = { messageState.value="" }) {
                            Icon(
                                imageVector = Icons.Default.Close, // Adjusted here
                                contentDescription = "Close Icon",
                            )
                        }
                    },
                    content = {
                        Text(text = messageState.value)
                    }
                )
            }},


        ) {
            paddingValues ->
            NavGraph(
                startDestination = startDestination,
                navController = navController,
                paddingValues = paddingValues,
                completionMessage= messageState,
                mainViewModel =  mainViewModel
            )
            Log.d("DEBUG", messageState.value)
            if (messageState.value.isNotEmpty()) {
                LaunchedEffect(key1 = messageState.value) {
                    val result = snackBarHostState.showSnackbar(
                        message = messageState.value,
                        duration = SnackbarDuration.Long
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed,SnackbarResult.Dismissed -> {
                            messageState.value = ""
                        }
                    }
                }
            }
        }
    }
}