package com.project.gains.presentation.authentication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.project.gains.R
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.navgraph.Route

@Composable
fun ChangePasswordScreen(onChangePassword: () -> Unit) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current



        if (showDialog.value) {
            FeedbackAlertDialog(
                title = "You have successfully updated your password!",
                onDismissRequest = {
                    showDialog.value = false
                },
                onConfirm = {
                    showDialog.value = false
                   onChangePassword()
                },
                show = showDialog
            )
        }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        androidx.compose.material.Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 20.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.change_password_icon),
                    contentDescription = "Change Password Icon",
                    modifier = Modifier
                        .scale(0.4f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Change Password",
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(50.dp))

                if (showError) {
                    androidx.compose.material.Card(
                        backgroundColor = MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "The password must match, please check again",
                            color = MaterialTheme.colorScheme.surface,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }

                androidx.compose.material.OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = {
                        Text(
                            "New Password",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(size = 20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                androidx.compose.material.OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = {
                        Text(
                            "Confirm New Password",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(size = 20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )



                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (newPassword == confirmPassword) {
                            showError = false
                            showDialog.value=true
                        } else {
                            showError = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Change Password")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChangePasswordScreen() {
    ChangePasswordScreen { /*newPassword, confirmPassword ->
        // Handle change password logic*/
    }
}
