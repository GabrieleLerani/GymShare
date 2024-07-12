package com.project.gains.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AlertDialog
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.project.gains.R
import com.project.gains.data.Exercise

@Composable
fun AddExerciseItem(
    exercise: Exercise,
    onItemClick: (Exercise) -> Unit,
    onItemClick2: () -> Unit,
    isSelected: Boolean,
    isToAdd: Boolean,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
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
                if (isToAdd){
                IconButton(onClick = {
                    onItemClick2()
                }) {
                    Icon(imageVector = Icons.Default.Add , contentDescription = "Exercise Button", tint = MaterialTheme.colorScheme.surface)
                }
                    }
                IconButton(onClick = {
                        onItemClick(exercise)

                }) {
                    Icon(imageVector =  Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "Exercise Button", tint = MaterialTheme.colorScheme.surface)
                }

            }
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
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
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
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
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
fun InstructionCard(text: String) {
    androidx.compose.material.Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.onTertiary,
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            androidx.compose.material.Text(
                text = text,
                style = androidx.compose.material.MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun WarningCard(message: String) {
    androidx.compose.material.Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                RoundedCornerShape(16.dp)
            ),
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.WarningAmber,
                contentDescription = "Warning",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun FeedbackAlertDialogOptions(
    message: String,
    popupVisible: MutableState<Boolean>,
    onClick: () -> Unit,
) {
    if (popupVisible.value) {
        AlertDialog(
            onDismissRequest = { popupVisible.value=false },
            title = {
            },
            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.headlineMedium,
                )
            },
            buttons = {
                Column {
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(5.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { popupVisible.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", color = MaterialTheme.colorScheme.error)
                        }
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(50.dp)
                                .background(Color.Gray)
                        )
                        TextButton(
                            onClick = { popupVisible.value = false
                                onClick()
                                      },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Confirm", color = MaterialTheme.colorScheme.tertiary)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp) // Optional padding to reduce width
        )
    }
}

@Composable
fun FeedbackAlertDialog(
    title: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    show: MutableState<Boolean>
) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {

                Box(modifier = Modifier.padding(20.dp)){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    )  {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    }
                }


            },
            text = {
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = { show.value=false
                            onConfirm()
                                  },
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .size(60.dp),
                        colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirm Icon",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(10.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            dismissButton = {
            },
            shape = RoundedCornerShape(20.dp),
        )
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun prev(){
    FeedbackAlertDialog(
        title = "Something went wrong",
        onDismissRequest = {  },
        onConfirm = {  },
        show = mutableStateOf(true)
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
                style = MaterialTheme.typography.bodySmall,
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