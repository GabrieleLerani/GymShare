package com.project.gains.presentation.authentication.screens

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
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
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.CircularProgressIndicator

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.material3.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.gains.R
import com.project.gains.presentation.CustomBackHandler
import com.project.gains.presentation.Dimension.ButtonCornerShape

import com.project.gains.presentation.authentication.AuthenticationViewModel
import com.project.gains.presentation.authentication.events.SignInEvent
import com.project.gains.presentation.components.SocialMediaIcon
import com.project.gains.presentation.navgraph.Route
import com.project.gains.theme.GainsAppTheme

import com.project.gains.util.Constants.LOGIN_FAILED

@Composable
fun SignInScreen(
    signInHandler: (SignInEvent.SignIn) -> Unit,
    viewModel: AuthenticationViewModel,
    navController: NavController) {


    CustomBackHandler(
        onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher ?: return,
        enabled = true // Set to false to disable back press handling
    ) {
    }

    GainsAppTheme {
        DefaultSignInContent(
            signInHandler,
            viewModel,
            navController
        )
    }
}

@Composable
fun DefaultSignInContent(
    signInHandler: (SignInEvent.SignIn) -> Unit,
    viewModel: AuthenticationViewModel,
    navController: NavController
) {
    // mutable state
    val isLoading by viewModel.isLoading.observeAsState()

    // fields of interest
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    // observed state
    val data by viewModel.data.observeAsState()

    // focus
    val focusManager = LocalFocusManager.current

    // Validation state
    val errorMessage = remember { mutableStateOf("") }
    var inputInserted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (inputInserted && (email.isEmpty() || password.isEmpty())) {
            Card(
                backgroundColor = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Empty " +  if(password.isEmpty()){ "Password" }else{ "" }+ if (email.isEmpty()){ " And Email" }else{ "" }+ ". Please insert one.",
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
        if (errorMessage.value.isNotEmpty()) {
            Card(
                backgroundColor = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Login failed. Please check your credentials and try again. The error is: ${errorMessage.value}",
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                )
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
                        text = "Login",
                        style = MaterialTheme.typography.displayMedium,
                        fontSize = 45.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 35.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        label = {
                            Text(
                                text = "Email",
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
                        shape = RoundedCornerShape(size = ButtonCornerShape),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (email.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = if (email.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
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
                        shape = RoundedCornerShape(size = ButtonCornerShape),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (password.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = if (password.isEmpty() && inputInserted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Forgot Password?",
                        color = MaterialTheme.colorScheme.tertiary,
                        style = TextStyle(textDecoration = TextDecoration.Underline ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { navController.navigate(Route.ForgotPasswordScreen.route)
                                viewModel.onNavigationComplete()}
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            if (email.isEmpty() || password.isEmpty()) {
                                inputInserted = true
                            } else {
                                signInHandler(SignInEvent.SignIn(email, password))
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Login")
                    }
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(8.dp)) {
                        SocialMediaIcon(icon = R.drawable.google, onClick = { navController.navigate(Route.HomeScreen.route) }, isSelected = false)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Don't have an account? Sign up",
                style = TextStyle(textDecoration = TextDecoration.Underline ),
                color = Color.Blue,
                modifier = Modifier.clickable { navController.navigate(Route.SignUpScreen.route)
                    viewModel.onNavigationComplete()}
            )
        }

        // Observe changes in data
        if (data?.isNotEmpty() == true) {
            when (data) {
                LOGIN_FAILED -> {
                    errorMessage.value = data.toString()
                }
            }

            // Change page if all ok
            if (viewModel.navigateToAnotherScreen.value == true) {
                navController.navigate(Route.HomeScreen.route)
                viewModel.onNavigationComplete()
            }
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
                modifier = Modifier
                    .padding(top = 20.dp).height(60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                CircularProgressIndicator(
                    progress = { progress.value },
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}


