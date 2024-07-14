package com.project.gains.presentation.share

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PostScreen(
    onExit: () -> Unit
){

    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    var imageUri by remember { mutableStateOf<Uri?>(null) }


    BottomSheetScaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onExit) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                },
                actions = {

                    AttachButton(onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    })

                    FilledTonalButton(
                        onClick = { },
                        modifier = Modifier.padding(end = 8.dp),
                        enabled = textState.text.isNotEmpty() || imageUri != null
                    ) {
                        Text("Post")
                    }

                }
            )
        },
        scaffoldState = scaffoldState,
        sheetContent = {
            SheetContent { selectedUri ->
                imageUri = selectedUri
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                imageUri?.let {

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(
                                onClick = { imageUri = null } ,
                                modifier = Modifier
                                    .padding(end = 4.dp),

                                colors = IconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = MaterialTheme.colorScheme.primary,
                                    disabledContentColor = MaterialTheme.colorScheme.primary,
                                    disabledContainerColor = MaterialTheme.colorScheme.primary
                                ),
                                enabled = imageUri != null
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Remove Image")
                            }

                            AsyncImage(
                                model = imageUri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        }


                    }

                }


                OutlinedTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    modifier = Modifier.fillMaxSize(),
                    placeholder = { Text(text = "Share your opinions...") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    )
                )

            }

        }
    }

}


@Composable
fun AttachButton(onClick: () -> Unit){
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(end = 4.dp),
        colors = IconButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(Icons.Default.Add, contentDescription = "Attach content")
    }
}



@Composable
fun SheetContent(onImageSelected: (Uri) -> Unit) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                onImageSelected(it)
            }
        }

    GridContent(onItemClick = {
        launcher.launch(
            "image/*"
        )
    })

}



@Composable
fun GridContent(onItemClick: () -> Unit) {

    val items = listOf(
        Triple("Exercise", Icons.Default.SportsGymnastics, {}),
        Triple("Workout", Icons.Default.FormatListNumbered, {}),
        Triple("Plan", Icons.Default.CalendarMonth, {}),
        Triple("Image", Icons.Default.Image, onItemClick),
        Triple("Video", Icons.Default.VideoFile, {}),
        Triple("Audio", Icons.Default.AudioFile, {}),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // set at most 3 column
        modifier = Modifier.padding(16.dp)
    ) {
        items(items.size) { index ->
            val (label, icon, onClick) = items[index]
            GridItem(
                icon = icon,
                label = label,
                onClick = onClick)
        }
    }
}

@Composable
fun GridItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
            onClick = onClick,
            colors = IconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(icon, contentDescription = label)
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun Prev(){
    val navController = rememberNavController()

    PostScreen { navController.popBackStack() }
}