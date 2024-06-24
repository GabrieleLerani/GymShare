package com.project.gains.presentation.screensNew

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.gains.R
import com.project.gains.theme.GainsAppTheme


@Composable
fun ExerciseScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Chest",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
        }

        item{
            Image(
                painter = painterResource(R.drawable.gi),
                contentDescription = "Exercise",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )
        }

        item {
            Text(
            text = "30-degree incline dumbbell bench press",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )  }

        item {
            Text(
                text = "The inclined dumbbell bench press is considered as the best basic exercise for developing the pectoral muscles and increasing general strength. This exercise allows a greater amplitude of movement than the classic bar press, and allows you to work out the muscles more efficiently.",
                fontSize = 16.sp
            )
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }

        item {
            Text(
                text = "Instruction",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }
        val instructionList = listOf(
            "1) Lie down on the incline bench at an angle of 30 degrees, holding dumbbells in bent arms at the sides of your chest (palms facing forward).",
            "2) After taking a deep breath, squeeze the dumbbells up into fully straightened arms. At the uppermost point, exhale.",
            "3) Smoothly return to the starting position. Your elbows should be out to the sides and move along an imaginary line...",
            // Add more instructions here
        )

        val warningList = listOf(
            "Keep your back straight.",
            "Avoid locking your elbows.",
            "Maintain a consistent speed."
        )


        instructionList.forEach { instruction ->
            item {
                InstructionCard(text = instruction)

            }
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }

        item {
            Text(
                text = "Warning",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }

        warningList.forEach { warning ->
            item {
                WarningCard(message = warning)

            }
        }


    }
}

@Composable
fun InstructionCard(text: String) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = text, style = MaterialTheme.typography.body1)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefauPreview() {
    GainsAppTheme {
        ExerciseScreen()
    }
}
