package com.project.gains.presentation.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.project.gains.R
import com.project.gains.presentation.components.WarningCard

import com.project.gains.theme.GainsAppTheme
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen() {
    // State for the timer
    var timerState by remember { mutableStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }

    // Function to start the timer
    if (isTimerRunning) {
        LaunchedEffect(Unit) {
            while (isTimerRunning) {
                delay(1000) // Update timer every second
                timerState++
            }
        }
    }

    // Formatting the timer as MM:SS
    val minutes = timerState / 60
    val seconds = timerState % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("1/12 Arms") },
            navigationIcon = {
                IconButton(onClick = { /* handle back */ }) {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                }
            },
            actions = {
                Text(
                    text = "12:21",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 16.dp)
                )
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Exercise Image (Animated GIF)
        Image(
            painter = painterResource(id = R.drawable.exercise2), // Ensure this is a GIF resource
            contentDescription = "Curl",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Exercise Title
        Text(
            text = "Curl",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Exercise Details
        Text(
            text = "Recommended time: 8 min, 110 - 140 bpm",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Counters Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Repetitions Counter
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("8", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Repeats required", color = Color.Gray, fontSize = 12.sp)
            }

            // Dynamic Counter for Minutes
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(formattedTime, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Minutes made", color = Color.Gray, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Row (Start/Stop Button)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    isTimerRunning = !isTimerRunning
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                if (isTimerRunning){
                    Icon(imageVector = Icons.Default.Stop, contentDescription = "Stop", modifier = Modifier.size(60.dp))

                }else{
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Start",modifier = Modifier.size(60.dp))

                }
            }
        }

        // Spacer between button and error cards
        Spacer(modifier = Modifier.height(16.dp))

        // Error Warning Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WarningCard(message = "Keep your back straight.")
            WarningCard(message = "Avoid locking your elbows.")
            WarningCard(message = "Maintain a consistent speed.")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GainsAppTheme {
        SessionScreen()
    }
}
