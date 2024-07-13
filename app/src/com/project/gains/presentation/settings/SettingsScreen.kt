package com.project.gains.presentation.settings


//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Support
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.presentation.components.SettingItem
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
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 25.dp),
            ) {
                item {

                    SettingItem(
                        icon = Icons.Default.Person,
                        title = "Account Preferences",
                        onClick = {navController.navigate(Route.AccountScreen.route) }
                    )



                }
                item {
                    SettingItem(
                        icon = Icons.AutoMirrored.Filled.Send,
                        title = "Sharing Preferences",
                        onClick = { navController.navigate(Route.LinkedSocialSettingScreen.route) }
                    )


                }
                item {
                    SettingItem(
                        icon = Icons.Default.Group,
                        title = "Tutorial",
                        onClick = { /* Handle click */ }
                    )


                }
                item {
                    SettingItem(
                        icon = Icons.Default.Support,
                        title = "Write to support",
                        onClick = {  }
                    )

                }
                item {
                    SettingItem(
                        icon = Icons.Default.Share,
                        title = "Tell a friend",
                        onClick = { /* Handle click */ }
                    )

                }
                item {
                    SettingItem(
                        icon = Icons.Default.Star,
                        title = "Rate the app",
                        onClick = { /* Handle click */ }
                    )


                }
            }
        }
    }
}


@Composable
@Preview
fun SettingsPreview(){

    GainsAppTheme {
        SettingsScreen(navController = rememberNavController())
    }
}




