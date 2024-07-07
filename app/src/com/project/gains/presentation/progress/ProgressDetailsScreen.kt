package com.project.gains.presentation.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowDropDown

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.generateRandomTrainingData
import com.project.gains.presentation.settings.ShareContentViewModel
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.MetricPopup
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.PeriodPopup
import com.project.gains.presentation.components.ShareContentPagePopup
import com.project.gains.presentation.components.TopBar

import com.project.gains.presentation.components.TrainingOverviewChart
import com.project.gains.presentation.settings.events.ManageDialogEvent

import com.project.gains.theme.GainsAppTheme

@Composable
fun ProgressDetailsScreen(
    navController: NavController,
    shareContentViewModel: ShareContentViewModel,
    progressViewModel: ProgressViewModel,
    selectHandler: (ManageDialogEvent) -> Unit
) {
    val linkedApps by shareContentViewModel.linkedApps.observeAsState()
    val showDialogShared by shareContentViewModel.showDialogShared.observeAsState()

    val progressChartPreview by progressViewModel.selectedPlotPreview.observeAsState()


    val popupVisible1 = remember { mutableStateOf(false) }
    val popupVisible2 = remember { mutableStateOf(false) }

    val showPopup2 = remember { mutableStateOf(false) }


    var selectedMetric by remember { mutableStateOf(TrainingMetricType.BPM) }
    var selectedPeriod by remember { mutableStateOf(PeriodMetricType.MONTH) }
    val trainingData: MutableList<TrainingData> = generateRandomTrainingData(10).toMutableList()


    val metrics = remember {
        mutableListOf(TrainingMetricType.BPM,TrainingMetricType.DISTANCE,TrainingMetricType.DURATION)
    }
    val periods = remember {
        mutableListOf(PeriodMetricType.YEAR,PeriodMetricType.MONTH,PeriodMetricType.WEEK)
    }
    val notification = remember {
        mutableStateOf(false)
    }
    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    message = "Progress Details",
                    button= {
                        IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {

                                showPopup2.value=true

                            }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.graphicsLayer {
                                    rotationZ = -45f // Rotate 45 degrees counterclockwise
                                }
                            )
                        }
                    }
                ) {
                    BackButton {
                        navController.popBackStack()
                    }
                }
                if (notification.value){
                    NotificationCard(message ="Notification", onClose = {notification.value=false})
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
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp),

                ) {
                    item {
                        Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(selectedPeriod.name, style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface)

                        IconButton(onClick = { popupVisible2.value = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Change Metric")
                        }
                        Spacer(modifier = Modifier.width(170.dp))
                        Text(selectedMetric.name, style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface)

                        IconButton(onClick = { popupVisible1.value = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Change Metric")
                        }
                    } }

                    item {
                        MetricPopup(
                        selectedMetricMap = metrics,
                        popupVisible = popupVisible1,
                        onDismiss = { popupVisible1.value = false },
                        onOptionSelected = { metric ->
                            selectedMetric = metric
                            popupVisible1.value = false
                        },
                        selectedMetric = selectedMetric
                    )
                    }
                    item {
                        PeriodPopup(
                            periods,
                            popupVisible = popupVisible2,
                            onDismiss = { popupVisible2.value = false },
                            onOptionSelected = { period ->
                                selectedPeriod = period
                                popupVisible2.value = false
                            },
                            selectedMetric = selectedPeriod
                        )
                    }

                    item {    TrainingOverviewChart(
                        trainingData =trainingData,
                        selectedMetric = selectedMetric,
                        selectedPeriod = selectedPeriod,
                        selectedPlotType = progressChartPreview ?: ProgressChartPreview("", R.drawable.plot3)
                    ) }

                    }
                if (showDialogShared==true) {

                    FeedbackAlertDialog(
                        title = "You have successfully Shared your content!",
                        onDismissRequest = {
                        },
                        onConfirm = {
                            selectHandler(ManageDialogEvent.SelectShowDialogShared(false))
                        },
                        show = showPopup2
                    )
                }
                }
            }
        linkedApps?.let {
            ShareContentPagePopup(
                showPopup2,
                { selectHandler(ManageDialogEvent.SelectShowDialogShared(true))},
                navController,
                shareContentViewModel
            )
        } }

}







@Preview(showBackground = true)
@Composable
fun ProgressDetailsScreenPreview() {
    val navController = rememberNavController()
    val shareContentViewModel: ShareContentViewModel = hiltViewModel()
    val progressViewModel : ProgressViewModel = hiltViewModel()
    ProgressDetailsScreen(
        navController = navController,
        shareContentViewModel = shareContentViewModel,
        progressViewModel = progressViewModel,
        shareContentViewModel::onManageDialogEvent
    )
}
