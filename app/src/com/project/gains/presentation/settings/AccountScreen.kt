package com.project.gains.presentation.settings


//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.navgraph.Route

import com.project.gains.presentation.settings.events.SignOutEvent
import com.project.gains.presentation.settings.events.UpdateEvent
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.Constants.SIGN_OUT_SUCCESS
import com.project.gains.util.Constants.UPDATE_SUCCESS


@Composable
fun AccountScreen(
    settingsHandler:(UpdateEvent.Update)-> Unit,
    signOutHandler: (SignOutEvent.SignOut) -> Unit,
    viewModel: SettingsViewModel,
    navController:NavController
) {
    // observable state
    val userProfile by viewModel.userProfile.collectAsState()
    val data by viewModel.data.observeAsState()
    var flag = remember {
        mutableStateOf(false)
    }
    var notification = remember {
        mutableStateOf(false)
    }
    // field of interest
    var newName by remember { mutableStateOf(userProfile?.displayName ?: "New Name") }
    var newEmail by remember { mutableStateOf(userProfile?.email ?: "New Name") }
    var newPassword by remember { mutableStateOf("New Password") }
    var showDialog = remember { mutableStateOf(false) }

    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = "Account Setting" ,
                    button= {
                        IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {
                                signOutHandler(SignOutEvent.SignOut)
                            }) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = "Logout",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    },
                    button1 = {
                        BackButton {
                            showDialog.value=false
                            navController.popBackStack()
                        }
                    }
                )
                if (notification.value){
                    NotificationCard(message ="Notification", onClose = {notification.value=false})
                }
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        // Name field
                        OutlinedTextField(
                            value = newName,
                            onValueChange = { newValue ->
                                newName = newValue
                            },
                            label = {
                                Text(
                                    "New Name",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            },
                            shape = RoundedCornerShape(size = 20.dp),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer), // Set the text color to white

                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary, // Set the contour color when focused
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary // Set the contour color when not focused
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        // Email field
                        OutlinedTextField(
                            value = newEmail,
                            onValueChange = { newValue ->
                                newEmail = newValue
                            },
                            label = {
                                Text(
                                    "New Email",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            },
                            shape = RoundedCornerShape(size = 20.dp),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer), // Set the text color to white

                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary, // Set the contour color when focused
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary // Set the contour color when not focused
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        // Password field
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newValue ->
                                newPassword = newValue
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            shape = RoundedCornerShape(size = 20.dp),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer), // Set the text color to white

                            label = {
                                Text(
                                    "New Password",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary, // Set the contour color when focused
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary // Set the contour color when not focused
                            )
                        )
                        // Observe changes in data
                        if (data?.isNotEmpty() == true) {
                            // Display data

                            if (data.equals(UPDATE_SUCCESS) && flag.value==false) {
                                showDialog.value=true
                                println(UPDATE_SUCCESS)
                                flag.value=true
                            } else if (!data.equals(SIGN_OUT_SUCCESS) && !data.equals(UPDATE_SUCCESS) && flag.value==false ) {
                                showDialog.value=true
                                println("OTHER")
                                flag.value=true

                            }

                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    settingsHandler(
                                        UpdateEvent.Update(
                                            newName,
                                            newEmail,
                                            newPassword
                                        )
                                    )
                                     },
                                modifier = Modifier.padding(bottom = 16.dp),
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary)
                            ) {
                                Icon(Icons.Default.Check, contentDescription = "Check Icon", tint = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                    item {
                        if (showDialog.value) {
                            FeedbackAlertDialog(
                                title = if (data.equals(UPDATE_SUCCESS)) "You have successfully updated your profile!" else if (!data.equals(SIGN_OUT_SUCCESS) && !data.equals(UPDATE_SUCCESS)) data!!.toString() else "",
                                message = "",
                                onDismissRequest = { },
                                onConfirm = {
                                    showDialog.value = false

                                },
                                confirmButtonText = "Ok",
                                dismissButtonText = "",
                                color=  if (data.equals(UPDATE_SUCCESS))  MaterialTheme.colorScheme.onSurface else if (!data.equals(SIGN_OUT_SUCCESS) && !data.equals(UPDATE_SUCCESS))  MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSurface,
                                show = showDialog

                            )
                        }
                    }
                }
            }
        }
    }
}




@Composable
@Preview
fun PreviewAccount(){
    val viewModel:SettingsViewModel = hiltViewModel()
    GainsAppTheme {
        AccountScreen(settingsHandler = {}, signOutHandler = {}, viewModel =viewModel , navController = rememberNavController())
    }
}



