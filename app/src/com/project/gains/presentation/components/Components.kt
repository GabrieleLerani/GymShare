package com.project.gains.presentation.components

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.theme.GainsAppTheme

@Composable
fun AddExerciseItem(
    exercise: Exercise,
    onItemClick: (Exercise) -> Unit,
    onItemClick2: () -> Unit,
    isSelected: Boolean,
    isToAdd: Boolean,
    modifier: Modifier
) {

    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        leadingContent = {
            Image(
                painter = painterResource(id = exercise.gifResId ?: R.drawable.logo),
                contentDescription = exercise.name,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
        },
        headlineContent = {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleMedium

            )
        },
        trailingContent = {
            if (isSelected) {
                Row {
                    if (isToAdd) {
                        IconButton(onClick = { onItemClick2() }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Exercise",

                            )
                        }
                    }
                    IconButton(onClick = { onItemClick(exercise) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "Forward",

                        )
                    }
                }
            }
        },

        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),

        tonalElevation = 1.dp,
        shadowElevation = 1.dp
    )

    /*
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = exercise.gifResId ?: R.drawable.logo),
            contentDescription = exercise.name,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = exercise.name,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row( modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        )  {
            if (isSelected) {
                if (isToAdd) {
                    IconButton(onClick = { onItemClick2() }) {
                        Icon(imageVector = Icons.Default.Add , contentDescription = "Exercise Button", tint = MaterialTheme.colorScheme.surface)
                    }
                }
                IconButton(onClick = { onItemClick(exercise) }) {
                    Icon(imageVector =  Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "Exercise Button", tint = MaterialTheme.colorScheme.surface)
                }
            }
        }
    }
    */
}

@Composable
fun GoogleLoginButton(onClick: () -> Unit, icon: Int) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.elevation(4.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Google Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Sign in with Google",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SocialMediaIcon(icon: Int, onClick: () -> Unit, isSelected: Boolean) {
    androidx.compose.material.IconButton(onClick = onClick) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = if (isSelected) MaterialTheme.colorScheme.primary else if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .padding(6.dp) // Add padding between the border and the image icon
        ) {
            androidx.compose.material.Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(38.dp) // Adjust the size of the icon to fit within the padding
            )
        }
    }
}

@Composable
fun SharingMediaIcon(icon: ImageVector, onClick: () -> Unit, isSelected: Boolean) {
    androidx.compose.material.IconButton(onClick = onClick) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = if (isSelected) MaterialTheme.colorScheme.primary else if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .padding(6.dp) // Add padding between the border and the image icon
        ) {
            androidx.compose.material.Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(38.dp) // Adjust the size of the icon to fit within the padding
            )
        }
    }
}


@Composable
fun LogoUser(modifier: Modifier,res:Int, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .size(70.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(res),
            contentDescription = "User Profile Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(35.dp)),  // Half of the size to make it fully rounded
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun NotificationCard(
    message: String,
    onClose: () -> Unit
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onTertiary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onClose) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close Icon",
                )
            }
        }
    }
}

@Composable
fun FeedbackAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    text: String,
    icon: ImageVector,
    dismiss: Boolean = true,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            if (dismiss){
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        }
    )
}

@Composable
fun EditProfileDialog(
    newName: String,
    newEmail: String,
    newPassword: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
    icon: ImageVector
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current


    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = "Edit Profile")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = newName,
                    onValueChange = onNameChange,
                    label = { Text("New Name") },
                    shape = RoundedCornerShape(size = 20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = newEmail,
                    onValueChange = onEmailChange,
                    label = { Text("New Email") },
                    shape = RoundedCornerShape(size = 20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = onPasswordChange,
                    label = { Text("New Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff

                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            androidx.compose.material.Icon(
                                imageVector = image,
                                contentDescription = description
                            )
                        }
                    },
                    shape = RoundedCornerShape(size = 20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}



@Composable
fun SettingItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(vertical = 12.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically)
        {
            androidx.compose.material.Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        androidx.compose.material.Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

