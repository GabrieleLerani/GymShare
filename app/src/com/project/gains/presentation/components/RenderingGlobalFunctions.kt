package com.project.gains.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel


@Composable
fun LogoUserImage(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit // Add a lambda for the click action
) {
    val initials = getInitials(name)

    Box(
        modifier = modifier.
            size(30.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable(onClick = onClick) // Make the Box clickable
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (initials.isNotEmpty()) {
            Text(
                text = initials,
                style = TextStyle(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontWeight = FontWeight.Bold
                )
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}
fun getInitials(name: String): String {
    return if (name.isNotEmpty()) {
        val words = name.split(" ")
        val firstInitial = words.firstOrNull()?.first()
        "$firstInitial"
    } else {
        ""
    }
}

@Composable
fun ManageLifecycle(lifecycleOwner: LifecycleOwner, viewModel: ViewModel, resume: () -> Unit, release: ()-> Unit) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME, Lifecycle.Event.ON_START, Lifecycle.Event.ON_CREATE -> resume()
                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP, Lifecycle.Event.ON_DESTROY -> release()
                else -> { }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
