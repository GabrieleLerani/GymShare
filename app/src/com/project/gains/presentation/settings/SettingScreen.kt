package com.project.gains.presentation.settings

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
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
import com.project.gains.presentation.components.FeedbackAlertDialog

import com.project.gains.presentation.components.SocialMediaRow
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.SaveSharingPreferencesEvent
import com.project.gains.presentation.navgraph.Route


import com.project.gains.theme.GainsAppTheme



@SuppressLint("MutableCollectionMutableState")
@Composable
fun SettingScreen(
    navController: NavController,
    linkHandler: (LinkAppEvent) -> Unit,
    saveLinkHandler: (SaveSharingPreferencesEvent) -> Unit,
    generalViewModel: GeneralViewModel
) {
    val linkedApps by generalViewModel.linkedApps.observeAsState()
    val clickedApps = remember { mutableStateOf(mutableListOf<Int>()) }

    val showDialog = remember { mutableStateOf(false) }
    val icons = listOf(
        R.drawable.instagram_icon,
        R.drawable.facebook_icon,
        R.drawable.x_logo_icon,
        R.drawable.drive_google_icon,
        R.drawable.spotify_icon,
        R.drawable.tiktok_logo_icon
    )


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
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BackButton(
                            onClick = { navController.popBackStack() }
                        )
                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = "Sharing Options",
                            style = MaterialTheme.typography.displayLarge.copy( // Using a smaller typography style
                                fontWeight = FontWeight.Bold,
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(4f, 4f),
                                    blurRadius = 8f
                                )
                            ),
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 0.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))

                    icons.forEach { icon ->
                        SocialMediaRow(
                            icon = icon,
                            isLinked = linkedApps?.contains(icon) == true,
                            linkHandler = linkHandler,
                            clickedApps=clickedApps
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                showDialog.value=true
                                saveLinkHandler(SaveSharingPreferencesEvent.SaveSharingPreferences(linkedApps ?: mutableListOf())) },
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
                    if (showDialog.value) {
                        FeedbackAlertDialog(
                            title = "",
                            message = "You have successfully updated your sharing preferences!",
                            onDismissRequest = { showDialog.value = false },
                            onConfirm = { showDialog.value = false
                                navController.navigate(Route.HomeScreen.route)
                                        },
                            confirmButtonText = "Ok",
                            dismissButtonText = ""
                        )
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
