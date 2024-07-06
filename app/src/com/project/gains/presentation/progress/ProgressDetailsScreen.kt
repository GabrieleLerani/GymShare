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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Send
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
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.generateRandomTrainingData
import com.project.gains.presentation.ShareContentViewModel
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.MetricPopup
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.PeriodPopup
import com.project.gains.presentation.components.ShareContentPagePopup
import com.project.gains.presentation.components.TopBar

import com.project.gains.presentation.components.TrainingOverviewChart
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.events.ShareContentEvent
import com.project.gains.presentation.navgraph.Route

import com.project.gains.theme.GainsAppTheme

@Composable
fun ProgressDetailsScreen(
    navController: NavController,
    shareHandler: (ShareContentEvent.SharePlot) -> Unit,
    generalViewModel: GeneralViewModel,
    shareContentViewModel: ShareContentViewModel,
    selectHandler:(SelectEvent)->Unit
) {
    var popupVisible1 = remember { mutableStateOf(false) }
    var popupVisible2 = remember { mutableStateOf(false) }
    var isTimerRunning by remember { mutableStateOf(false) }
    val linkedApps by generalViewModel.linkedApps.observeAsState()
    var showPopup2 = remember { mutableStateOf(false) }
    val showDialogShared by generalViewModel.showDialogShared.observeAsState()
    var showDialog = remember { mutableStateOf(false) }

    val selectedPlan by generalViewModel.selectedPlan.observeAsState()
    //var selectedMetric = generalViewModel._selectedMetricsMap.get(selectedPlan?.id)?.get(0)
    //var selectedPeriod = generalViewModel._selectedPeriodsMap.get(selectedPlan?.id)?.get(0)

    var selectedMetric by remember { mutableStateOf(TrainingMetricType.BPM) }
    var selectedPeriod by remember { mutableStateOf(PeriodMetricType.MONTH) }
    var trainingData: MutableList<TrainingData> = generateRandomTrainingData(10).toMutableList()

    val progressChartPreview by generalViewModel.selectedPlotPreview.observeAsState()
    val metrics = remember {
        mutableListOf(TrainingMetricType.BPM,TrainingMetricType.DISTANCE,TrainingMetricType.DURATION)
    }
    val periods = remember {
        mutableListOf(PeriodMetricType.YEAR,PeriodMetricType.MONTH,PeriodMetricType.WEEK)
    }
    var notification = remember {
        mutableStateOf(false)
    }
    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = "Progress Details" ,
                    button= {
                        androidx.compose.material.IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {

                                showPopup2.value=true

                            }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.graphicsLayer {
                                    rotationZ = -45f // Rotate 45 degrees counterclockwise
                                }
                            )
                        }
                    },
                    button1 = {
                        BackButton {
                            navController.popBackStack()
                        }
                    }
                )
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
                        trainingData =trainingData ,
                        selectedMetric = selectedMetric,
                        selectedPeriod = selectedPeriod,
                        shareHandler = shareHandler,
                        selectedPlotType = progressChartPreview ?: ProgressChartPreview("", R.drawable.plot3)
                    ) }

                    }
                if (showDialogShared==true) {

                    FeedbackAlertDialog(
                        title = "You have successfully Shared your content!",
                        message = "",
                        onDismissRequest = {
                        },
                        onConfirm = {
                            selectHandler(SelectEvent.SelectShowDialogShared(false))
                        },
                        confirmButtonText = "Ok",
                        dismissButtonText = "",
                        color = MaterialTheme.colorScheme.onError,
                        show = showPopup2
                    )
                }
                }
            }
        linkedApps?.let {
            ShareContentPagePopup(
                showPopup2,
                it,
                showDialogShared,
                { selectHandler(SelectEvent.SelectShowDialogShared(true))},
                navController,shareContentViewModel)
        } }

}







@Preview(showBackground = true)
@Composable
fun ProgressDetailsScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel:GeneralViewModel = hiltViewModel()
    val shareContentViewModel:ShareContentViewModel = hiltViewModel()
    ProgressDetailsScreen(
        navController = navController,
        shareHandler = {  },
        generalViewModel,
        shareContentViewModel,
        selectHandler = {}

    )
}