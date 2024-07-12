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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.project.gains.R
import com.project.gains.presentation.authentication.events.OTPEvent
import com.project.gains.presentation.components.FeedbackAlertDialog

@Composable
fun ChangePasswordScreen(onChangePassword: (String) -> Unit) {
    var newPassword by remember { mutableStateOf("") }
    var showPopup =  remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

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

                Button(
                    onClick = {
                        showPopup.value = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Change Password")
                }
            }
        }
    }

    if (showPopup.value) {
        FeedbackAlertDialog(
            title = "Your password has been changed!",
            onDismissRequest = { onChangePassword(newPassword) },
            onConfirm = { onChangePassword(newPassword) },
            show = showPopup
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChangePasswordScreen() {
    ChangePasswordScreen { newPassword ->
        // Handle change password logic
    }
}
