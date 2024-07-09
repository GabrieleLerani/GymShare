package com.project.gains.presentation.onboarding.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


/*
* Composable buttons used in the onboarding pages*/
@Composable
fun OnBoardingButton(
    text: String,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(size = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary, // Orange
            contentColor = MaterialTheme.colorScheme.onPrimary // Text color
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),

        )
    }
}


@Composable
fun OnBoardingTextButton(
    text: String,
    onClick: () -> Unit
){
    TextButton(onClick = onClick, colors =  ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary, // Orange
        contentColor = MaterialTheme.colorScheme.onPrimary // Text color
    )) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),

        )
    }
}