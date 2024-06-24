import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.project.gains.R
import com.project.gains.presentation.components.MuscleGroupItem
import com.project.gains.theme.GainsAppTheme



@Composable
fun ExerciseTypeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = "Exercises",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        MuscleGroupItem(imageResId = R.drawable.exercise2, title = "Chest")
        MuscleGroupItem(imageResId = R.drawable.exercise2, title = "Back")
        MuscleGroupItem(imageResId = R.drawable.exercise2, title = "Legs")
        MuscleGroupItem(imageResId = R.drawable.exercise2, title = "Gluteus")
        MuscleGroupItem(imageResId = R.drawable.exercise2, title = "Deltoids")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GainsAppTheme {
        ExerciseTypeScreen()
    }
}
