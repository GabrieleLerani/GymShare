package com.project.gains.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

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
import com.project.gains.R

import com.project.gains.presentation.components.BackButton

import com.project.gains.presentation.components.BottomNavigationBar

import com.project.gains.presentation.components.SocialMediaIcon
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.SaveSharingPreferencesEvent


import com.project.gains.theme.GainsAppTheme



@Composable
fun SettingScreen(
    navController: NavController,
    linkHandler: (LinkAppEvent) -> Unit,
    saveLinkHandler: (SaveSharingPreferencesEvent) -> Unit,
    generalViewModel: GeneralViewModel
) {
   val linkedApps by generalViewModel.linkedApps.observeAsState()


    GainsAppTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BackButton(
                            onClick = {navController.popBackStack()}
                        )
                        androidx.compose.material3.Text(
                            text = "Sharing Options",
                            style = MaterialTheme.typography.displayMedium,
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 0.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SocialMediaIcon(icon = R.drawable.instagram_icon, onClick = { /* Handle Facebook click */ }, isSelected = linkedApps?.contains(
                            R.drawable.instagram_icon
                        ) == true )
                        Spacer(modifier = Modifier.width(50.dp))
                        Button(
                            onClick = {
                                linkHandler(LinkAppEvent.LinkApp(R.drawable.instagram_icon)) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Icon")
                            Text("Link")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SocialMediaIcon(icon = R.drawable.facebook_icon, onClick = { /* Handle Facebook click */ }, isSelected = linkedApps?.contains(
                            R.drawable.facebook_icon
                        ) == true )
                        Spacer(modifier = Modifier.width(50.dp))
                        Button(
                            onClick = {
                                linkHandler(LinkAppEvent.LinkApp(R.drawable.facebook_icon)) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Icon")
                            Text("Link")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SocialMediaIcon(icon = R.drawable.x_logo_icon, onClick = { /* Handle Facebook click */ }, isSelected = linkedApps?.contains(
                            R.drawable.x_logo_icon
                        ) == true )
                        Spacer(modifier = Modifier.width(50.dp))
                        Button(
                            onClick = {
                                linkHandler(LinkAppEvent.LinkApp(R.drawable.x_logo_icon)) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Icon")
                            Text("Link")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SocialMediaIcon(icon = R.drawable.drive_google_icon, onClick = { /* Handle Facebook click */ }, isSelected = linkedApps?.contains(
                            R.drawable.drive_google_icon
                        ) == true )
                        Spacer(modifier = Modifier.width(50.dp))
                        Button(
                            onClick = {
                                linkHandler(LinkAppEvent.LinkApp(R.drawable.drive_google_icon)) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Icon")
                            Text("Link")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SocialMediaIcon(icon = R.drawable.spotify_icon, onClick = { /* Handle Facebook click */ }, isSelected = linkedApps?.contains(
                            R.drawable.spotify_icon
                        ) == true )
                        Spacer(modifier = Modifier.width(50.dp))
                        Button(
                            onClick = {
                                linkHandler(LinkAppEvent.LinkApp(R.drawable.spotify_icon))},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Icon")
                            Text("Link")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SocialMediaIcon(icon = R.drawable.tiktok_logo_icon, onClick = { /* Handle Facebook click */ }, isSelected = linkedApps?.contains(
                            R.drawable.tiktok_logo_icon
                        ) == true )
                        Spacer(modifier = Modifier.width(50.dp))
                        Button(
                            onClick = {
                                linkHandler(LinkAppEvent.LinkApp(R.drawable.tiktok_logo_icon)) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Icon")
                            Text("Link")
                        }
                    }

                    // Add more checkboxes for other options as needed

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { saveLinkHandler(SaveSharingPreferencesEvent.SaveSharingPreferences(linkedApps ?: mutableListOf())) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(Icons.Default.Done, contentDescription = "Add Icon")
                            Text("Save")
                        }
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel :GeneralViewModel = hiltViewModel()
    SettingScreen(
        navController = navController,
        saveLinkHandler = {  },
        linkHandler = {},
        generalViewModel = generalViewModel
    )
}
