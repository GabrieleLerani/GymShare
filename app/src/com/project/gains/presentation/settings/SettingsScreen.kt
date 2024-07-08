package com.project.gains.presentation.settings


//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Support
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.LogoUser
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.SettingItem
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.navgraph.Route

import com.project.gains.theme.GainsAppTheme


@Composable
fun SettingsScreen(
    navController: NavController
) {

    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                item {
                    SettingItem(
                        icon = Icons.Default.Person, // Replace with your desired icon
                        title = "Account Preferences",
                        onClick = {navController.navigate(Route.AccountScreen.route) }
                    )
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(20.dp))

                }
                item {
                    SettingItem(
                        icon = Icons.AutoMirrored.Filled.Send, // Replace with your desired icon
                        title = "Sharing Preferences",
                        onClick = { navController.navigate(Route.LinkedSocialSettingScreen.route) }
                    )
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(20.dp))

                }
                item {
                    SettingItem(
                        icon = Icons.Default.Group, // Replace with your desired icon
                        title = "Tutorial",
                        onClick = { /* Handle click */ }
                    )
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(20.dp))

                }
                item {
                    SettingItem(
                        icon = Icons.Default.Support, // Replace with your desired icon
                        title = "Write to support",
                        onClick = {  }
                    )
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(20.dp))

                }
                item {
                    SettingItem(
                        icon = Icons.Default.Share, // Replace with your desired icon
                        title = "Tell a friend",
                        onClick = { /* Handle click */ }
                    )
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(20.dp))

                }
                item {
                    SettingItem(
                        icon = Icons.Default.Star, // Replace with your desired icon
                        title = "Rate the app",
                        onClick = { /* Handle click */ }
                    )
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
        }
    }
}


@Composable
@Preview
fun settingsPreview(){

    GainsAppTheme {
        SettingsScreen(navController = rememberNavController())
    }
}




