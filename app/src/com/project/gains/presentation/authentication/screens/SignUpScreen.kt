package com.project.gains.presentation.authentication.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.gains.R
import com.project.gains.presentation.Dimension
import com.project.gains.presentation.authentication.AuthenticationViewModel
import com.project.gains.presentation.authentication.events.SignUpEvent
import com.project.gains.presentation.components.FeedbackAlertDialogOptions
import com.project.gains.presentation.navgraph.Route

import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.Constants.SIGN_UP_SUCCESS

// Presentation Layer
@Composable
fun SignUpScreen(signInHandler: (SignUpEvent.SignUp) -> Unit, viewModel: AuthenticationViewModel, navController: NavController) {
    GainsAppTheme {
        DefaultSignUpContent(signInHandler,
            viewModel,
            navController)
    }
}

@Composable
fun DefaultSignUpContent(
    signInHandler: (SignUpEvent.SignUp) -> Unit,
    viewModel: AuthenticationViewModel,
    navController: NavController
) {
    // mutable state
    val isLoading by viewModel.isLoading.observeAsState()

    // fields of interest
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    // observed state
    val data by viewModel.data.observeAsState()
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }
    val openPopup = remember { mutableStateOf(false) }

    val errorMessage = remember { mutableStateOf("") }
    var inputInserted by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (errorMessage.value.isNotEmpty()) {
            Card(
                backgroundColor = Color.hsl(0f, 1f, 0.85f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.red_alert_icon),
                        contentDescription = "Wrong credentials",
                        modifier = Modifier
                            .weight(1f, true)
                            .align(Alignment.CenterVertically)
                            .padding(start = 15.dp)
                    )
                    Text(
                        text = "Sign up failed. Please check your credentials and try again.",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(9f, true)
                    )
                }
            }
        }

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
                    Text(
                        text = "Sign up",
                        style = MaterialTheme.typography.displayMedium,
                        fontSize = 45.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 35.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = {
                            Text(
                                "Name",
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
                            focusedBorderColor = if (name.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = if (name.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    )

                    if (inputInserted && name.isEmpty()) {
                        Text(
                            text = "Empty name. Please insert one.",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .align(Alignment.Start)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
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
                            focusedBorderColor = if (email.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = if (email.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    )

                    if (inputInserted && email.isEmpty()) {
                        Text(
                            text = "Empty email. Please insert one.",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .align(Alignment.Start)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(
                                text = "Password",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff


                            val description = if (passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = description)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(size = Dimension.ButtonCornerShape),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (password.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = if (password.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    )

                    if (inputInserted && password.isEmpty()) {
                        Text(
                            text = "Empty password. Please insert one.",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .align(Alignment.Start)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                                inputInserted=true
                            } else {
                                openPopup.value = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("SignUp")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Already have an account? Login",
                color = Color.Blue,
                style = TextStyle(textDecoration = TextDecoration.Underline ),
                modifier = Modifier.clickable {
                    navController.navigate(Route.SignInScreen.route)
                    viewModel.onNavigationComplete()}
            )
        }

        if (isLoading == true) {
            val progress = remember { Animatable(0f) }
            LaunchedEffect(Unit) {

                progress.animateTo(
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1000),
                        repeatMode = RepeatMode.Reverse
                    )
                )

            }

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                CircularProgressIndicator(
                    progress = { progress.value },
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        if (openPopup.value == true) {
            FeedbackAlertDialogOptions(
                message = "Are you sure your credentials are correct?",
                popupVisible = openPopup
            ) { signInHandler(SignUpEvent.SignUp(name, email, password, password)) }
        }

        // Observe changes in data
        if (data?.isNotEmpty() == true) {

            if (data.equals(SIGN_UP_SUCCESS)){
            }else{
                errorMessage.value = data.toString()
            }

            // Change page if all ok
            if (viewModel.navigateToAnotherScreen.value==true) {
                navController.navigate(Route.HomeScreen.route)
                viewModel.onNavigationComplete()
            }

        }
    }
}
