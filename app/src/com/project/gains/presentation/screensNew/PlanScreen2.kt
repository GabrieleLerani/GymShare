package com.project.gains.presentation.screensNew

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.gains.R
import com.project.gains.theme.GainsAppTheme


@Composable
fun WorkoutScreen() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Plan", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Handle click */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.FitnessCenter, contentDescription = "Workouts") },
                    label = { Text("Workouts") },
                    selected = true,
                    onClick = { /* Handle click */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.FoodBank, contentDescription = "Diet") },
                    label = { Text("Diet") },
                    selected = false,
                    onClick = { /* Handle click */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Schedule") },
                    label = { Text("Schedule") },
                    selected = false,
                    onClick = { /* Handle click */ }
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WorkoutHeader()
                Spacer(modifier = Modifier.height(16.dp))
                WorkoutDaysList()
            }
        }
    )
}

@Composable
fun WorkoutHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter= painterResource(id = R.drawable.pexels1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp).clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "GENERAL MUSCLE BUILDING",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "For gym • Expert",
            fontSize = 16.sp,

        )
        Text(
            text = "32 workout days (3 sessions per week)",
            fontSize = 14.sp,
        )
        Text(
            text = "Workouts done: 0",
            fontSize = 14.sp,
        )
    }
}

@Composable
fun WorkoutDaysList() {
    val workoutDays = listOf(
        "chest",
        "back",
        "legs",
        "arms+shoulders"
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Workout days",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        workoutDays.forEachIndexed { index, day ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (index == 0) Icons.Default.Circle else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (index == 0) Color.Red else Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "${index + 1} workout day",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = day,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefautPreview() {
    GainsAppTheme {
        WorkoutScreen()
    }
}
