package com.project.gains.presentation.authentication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.gains.R
import com.project.gains.presentation.Dimension
import com.project.gains.presentation.authentication.events.OTPEvent
import com.project.gains.presentation.authentication.events.SignInEvent
import com.project.gains.presentation.components.GoogleLoginButton
import com.project.gains.presentation.navgraph.Route

@Composable
fun ForgotPasswordScreen(
    onSendClicked: (String) -> Unit,
    generateOTPHandler: (OTPEvent.GenerateOTP) -> Unit
) {
    var email by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    var inputInserted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 20.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.forgot_password_icon),
                    contentDescription = "Forgot password Icon",
                    modifier = Modifier
                        .scale(0.5f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Forgot Password",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Enter your email and we'll send you an otp to reset your password",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(50.dp))

                if (email.isEmpty() && inputInserted) {
                    Card(
                        backgroundColor = MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Empty " + if (email.isEmpty()) {
                                "Email"
                            } else {
                                ""
                            } + ". Please insert one.",
                            color = MaterialTheme.colorScheme.surface,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            "Email",
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

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (email.isNotEmpty()) {
                            generateOTPHandler(OTPEvent.GenerateOTP)
                            onSendClicked(email)
                        } else {
                            inputInserted = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Get OTP")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewForgotPasswordScreen() {
    ForgotPasswordScreen(
        generateOTPHandler = {},
        onSendClicked = { email ->
            // Handle send button click
            println("Email entered: $email")
        }
    )
}