package com.project.gains.presentation.authentication.screens

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalContext
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
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.project.gains.R
import com.project.gains.presentation.CustomBackHandler
import com.project.gains.presentation.Dimension.ButtonCornerShape

import com.project.gains.presentation.authentication.AuthenticationViewModel
import com.project.gains.presentation.authentication.events.SignInEvent
import com.project.gains.presentation.components.FeedbackAlertDialog
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
    val isError by viewModel.isError.observeAsState()

    // fields of interest
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }

    // observed state
    val data by viewModel.data.observeAsState()
    var authenticationResult by remember { mutableStateOf("") }
    val openErrorPopup = remember { mutableStateOf(false) }

    // focus
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 45.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 35.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer), // Set the text color to white

            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(size = ButtonCornerShape),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary, // Set the contour color when focused
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary // Set the contour color when not focused
            )

        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
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
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Default.VisibilityOff
                else
                    Icons.Default.Visibility

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
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Button(
            onClick = {
                focusManager.clearFocus()
                signInHandler(SignInEvent.SignIn(email, password))
            },
            shape = RoundedCornerShape(size = ButtonCornerShape),
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary, // Green
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Text color
            )
        ) {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

        }
        val text = AnnotatedString.Builder().apply {
            pushStringAnnotation(
                tag = "LINK",
                annotation = "destination_page"
            )
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.tertiary,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("You don't have an account? Click here to signUp ")
            }
            pop()
        }.toAnnotatedString()

        ClickableText(
            text = text,
            onClick = { offset ->
                text.getStringAnnotations("LINK", offset, offset)
                    .firstOrNull()?.let {
                        navController.navigate(Route.SignUpScreen.route)
                        viewModel.onNavigationComplete()
                    }
            },
            modifier = Modifier.padding(top = 5.dp)

        )

            // Observe changes in data


            if (data?.isNotEmpty() == true ) {

                when (data){
                    LOGIN_FAILED -> {
                       openErrorPopup.value=true
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
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    CircularProgressIndicator(
                        progress = { progress.value },
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    if (isError == true) {
                        Text(
                            text = "Check your internet connection and retry later",
                            color = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        if (openErrorPopup.value == true) {
            FeedbackAlertDialog(
                title = "",
                message = "Something went wrong check your input and retry",
                onDismissRequest = {  },
                onConfirm = {

                },
                confirmButtonText = "Ok",
                dismissButtonText = "",
                color = MaterialTheme.colorScheme.onError,
                openErrorPopup
            )
        }

        }
    }

