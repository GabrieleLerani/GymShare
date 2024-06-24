import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.project.gains.presentation.components.FeedPost
import com.project.gains.presentation.components.LogoUser
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.theme.GainsAppTheme


@Composable
fun FeedScreen() {
    var showPopup = remember { mutableStateOf(true) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sport Feed", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Handle click */ }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            )
            NotificationCard(message = "Hit the start button once you start to sweat.\nThat's when the workout begins!") {
                showPopup.value=false
            }
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.FitnessCenter, contentDescription = "Workouts") },
                    label = { Text("Workouts") },
                    selected = false,
                    onClick = { /* Handle click */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Feed, contentDescription = "Feed") },
                    label = { Text("Feed") },
                    selected = true,
                    onClick = { /* Handle click */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Message, contentDescription = "Messages") },
                    label = { Text("Messages") },
                    selected = false,
                    onClick = { /* Handle click */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Book, contentDescription = "Handbook") },
                    label = { Text("Handbook") },
                    selected = false,
                    onClick = { /* Handle click */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.MoreVert, contentDescription = "More") },
                    label = { Text("More") },
                    selected = false,
                    onClick = { /* Handle click */ }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize() ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { FeedPost() }
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun DefaultPeview() {
    GainsAppTheme {
        FeedScreen()
    }
}