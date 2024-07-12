package com.project.gains.presentation.authentication.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
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
    val isError by viewModel.isError.observeAsState()

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

        if (inputInserted &&(name.isEmpty() || email.isEmpty() || password.isEmpty())) {
            Card(
                backgroundColor = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Empty " +  if (name.isEmpty()){ "Name" }else{ ""} + if(password.isEmpty()){ " And Password" }else{ "" }+ if (email.isEmpty()){ ", Email" }else{ "" }+ ". Please insert one.",
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
                    text = "SignUp failed. Please check your credentials and try again. The error is: ${errorMessage.value}",
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }

        Text(
            text = "Sign up",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 45.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 35.dp)
        )
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
        Button(
            onClick = {
                focusManager.clearFocus()
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    inputInserted=true
                } else {
                    errorMessage.value = ""
                    openPopup.value = true
                }
            },
            shape = RoundedCornerShape(size = 20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // Orange
                contentColor = MaterialTheme.colorScheme.onPrimary // Text color
            )
        ) {
            Text("Sign Up")
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
                append("You don't have an account? Click here to go to Login ")
            }
            pop()
        }.toAnnotatedString()

        ClickableText(
            text = text,
            onClick = { offset ->
                text.getStringAnnotations("LINK", offset, offset)
                    .firstOrNull()?.let {
                        navController.navigate(Route.SignInScreen.route)
                        viewModel.onNavigationComplete()
                    }
            },
            modifier = Modifier.padding(top = 5.dp)
        )

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
