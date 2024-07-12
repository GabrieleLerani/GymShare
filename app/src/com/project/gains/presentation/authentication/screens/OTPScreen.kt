package com.project.gains.presentation.authentication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.project.gains.R
import com.project.gains.presentation.authentication.AuthenticationViewModel
import com.project.gains.presentation.navgraph.Route

@Composable
fun OTPScreen(
    authenticationViewModel: AuthenticationViewModel,
    onVerifyClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    val correctOTP by authenticationViewModel.otp.observeAsState()
    var otp by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

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
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.otp_icon),
                    contentDescription = "OTP Icon",
                    modifier = Modifier
                        .scale(0.5f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Verification",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You will get an OTP via email",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )

                // TODO substitute it with a notification
                Text(
                    text = correctOTP.toString(),
                    style = MaterialTheme.typography.labelMedium,
                )

                Spacer(modifier = Modifier.height(50.dp))

                OutlinedTextField(
                    value = otp,
                    onValueChange = { otp = it },
                    label = {
                        Text(
                            "OTP",
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

                if (showError) {
                    Text(
                        text = "The OTP does not match, please check again.",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                    )
                } else {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = "Wrong email? Change it",
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable { onBackClicked() }
                        .align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (otp.toIntOrNull() == correctOTP) {
                            showError = false
                            onVerifyClicked()
                        } else {
                            showError = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Verify")
                }
            }
        }
    }
}