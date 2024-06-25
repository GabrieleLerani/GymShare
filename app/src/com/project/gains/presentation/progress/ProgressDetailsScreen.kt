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
import androidx.compose.material.icons.filled.History
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
import com.project.gains.presentation.components.MetricPopup
import com.project.gains.presentation.components.PeriodPopup
import com.project.gains.presentation.components.TopBar

import com.project.gains.presentation.components.TrainingOverviewChart
import com.project.gains.presentation.events.ShareContentEvent

import com.project.gains.theme.GainsAppTheme

@Composable
fun ProgressDetailsScreen(
    navController: NavController,
    shareHandler: (ShareContentEvent.SharePlot) -> Unit,
    generalViewModel: GeneralViewModel
) {
    var popupVisible1 = remember { mutableStateOf(false) }
    var popupVisible2 = remember { mutableStateOf(true) }

    val selectedPlan by generalViewModel.selectedPlan.observeAsState()
    var selectedMetricMap = generalViewModel._selectedMetricsMap.get(selectedPlan?.id)
    var selectedPeriodMap = generalViewModel._selectedPeriodsMap.get(selectedPlan?.id)

    var selectedMetric by remember { mutableStateOf(TrainingMetricType.BPM) }
    var selectedPeriod by remember { mutableStateOf(PeriodMetricType.MONTH) }
    val sessions by generalViewModel.currentSessions.observeAsState()
    var training_data: MutableList<TrainingData> = mutableListOf()
    var trainingData: MutableList<TrainingData> = generateRandomTrainingData(10).toMutableList()

    val progressChartPreview by generalViewModel.selectedPlotPreview.observeAsState()
    sessions?.let { sessionList ->
        val kcalValuesInts = sessionList.map { it.kcal }
        val bpmsInts = sessionList.map { it.bpm }
        val restTimesInts = sessionList.map { it.restTime }
        val durationsInts = sessionList.map { it.duration }
        val intensitiesInts = sessionList.map { it.intensity }
        val distancesInts = sessionList.map { it.distance }
        when (selectedMetric) {
            TrainingMetricType.INTENSITY -> {
                training_data = mutableListOf()
                intensitiesInts.forEach {
                    training_data.add(TrainingData(TrainingMetricType.INTENSITY,it))
                }
            }
            TrainingMetricType.DURATION -> {
                training_data = mutableListOf()
                durationsInts.forEach {
                    training_data.add(TrainingData(TrainingMetricType.DURATION,it))
                }

            }
            TrainingMetricType.DISTANCE -> {
                training_data = mutableListOf()
                distancesInts.forEach {
                    training_data.add(TrainingData(TrainingMetricType.DISTANCE,it))
                }
            }
            TrainingMetricType.KCAL -> {
                training_data = mutableListOf()
                kcalValuesInts.forEach {
                    training_data.add(TrainingData(TrainingMetricType.KCAL,it))
                }

            }
            TrainingMetricType.BPM -> {
                training_data = mutableListOf()
                bpmsInts.forEach {
                    training_data.add(TrainingData(TrainingMetricType.BPM,it))
                }

            }
            TrainingMetricType.REST -> {
                training_data = mutableListOf()
                restTimesInts.forEach {
                    training_data.add(TrainingData(TrainingMetricType.REST,it))
                }

            }
        }

    }
    val metrics = remember {
        mutableListOf(TrainingMetricType.BPM,TrainingMetricType.DISTANCE,TrainingMetricType.DURATION)
    }
    val periods = remember {
        mutableListOf(PeriodMetricType.YEAR,PeriodMetricType.MONTH,PeriodMetricType.WEEK)
    }

    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = "Progress Details" ,
                    button= {
                        IconButton(
                            modifier = Modifier.size(45.dp),
                            onClick = {
                                // Handle history button click
                                // TODO history popus page
                                //navController.navigate(Route.HistoryScreen.route)
                            }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = "History",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                )
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

                        IconButton(onClick = { popupVisible1.value = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Change Metric")
                        }
                        Spacer(modifier = Modifier.width(170.dp))
                        Text(selectedMetric.name, style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface)

                        IconButton(onClick = { popupVisible2.value = true }) {
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
                        selectedMetric = metrics[0]
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
                            selectedMetric = periods[0]
                        )
                    }

                    item {    TrainingOverviewChart(
                        trainingData =trainingData ,
                        selectedMetric = selectedMetric,
                        selectedPeriod = selectedPeriod,
                        shareHandler = shareHandler,
                        selectedPlotType = progressChartPreview ?: ProgressChartPreview("", R.drawable.plo1)
                    ) }

                    }
                }
            }
        }
    }







@Preview(showBackground = true)
@Composable
fun ProgressDetailsScreenPreview() {
    val navController = rememberNavController()
    val generalViewModel:GeneralViewModel = hiltViewModel()
    ProgressDetailsScreen(
        navController = navController,
        shareHandler = {  },
        generalViewModel

    )
}