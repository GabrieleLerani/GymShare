package com.project.gains

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.ui.Modifier
import com.project.gains.presentation.MainScreen
import com.project.gains.presentation.MainViewModel
import com.project.gains.presentation.components.SearchViewModel
import com.project.gains.presentation.navgraph.NavGraph
import com.project.gains.theme.GainsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GainsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val startDestination = viewModel.startDestination
                    MainScreen(
                        startDestination = startDestination,
                        searchViewModel = searchViewModel
                    )
                }
            }
        }
    }
}

