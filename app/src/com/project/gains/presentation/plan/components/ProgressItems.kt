package com.project.gains.presentation.plan.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.gains.R

@Composable
fun SummaryCard(
    workouts: Int,
    calories: Int,
    minutes: Int
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SummaryItem(iconId = R.drawable.medal, value = workouts, label = "Workouts")
                SummaryItem(iconId = R.drawable.fire, value = calories, label = "kcal")
                SummaryItem(iconId = R.drawable.timer, value = minutes, label = "Minutes")
            }
        }
    }
}
@Composable
fun SummaryItem(
    iconId: Int,
    value: Int,
    label: String
) {
    Column(
        modifier = Modifier
            .padding(8.dp),
            //.width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = value.toString(),
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium

        )
        Text(
            text = label,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodySmall
        )
    }
}