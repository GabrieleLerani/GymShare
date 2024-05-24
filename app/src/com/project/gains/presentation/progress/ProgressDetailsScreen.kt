package com.project.gains.presentation.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.MetricPopup
import com.project.gains.presentation.components.PeriodPopup

import com.project.gains.presentation.components.TrainingOverviewChart
import com.project.gains.presentation.events.ShareContentEvent

import com.project.gains.theme.GainsAppTheme

@Composable
fun ProgressDetailsScreen(
    navController: NavController,
    shareHandler: (ShareContentEvent.SharePlot) -> Unit,
    generalViewModel: GeneralViewModel
) {
    var popupVisible by remember { mutableStateOf(false) }
    var selectedMetric by remember { mutableStateOf(TrainingMetricType.DURATION) }
    var selectedPeriod by remember { mutableStateOf(PeriodMetricType.MONTH) }
    val sessions by generalViewModel.currentSessions.observeAsState()
    var training_data: MutableList<TrainingData> = mutableListOf()

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
                intensitiesInts.forEach {
                    training_data.add(TrainingData(TrainingMetricType.DURATION,it))
                }

            }
            TrainingMetricType.DISTANCE -> {
                training_data = mutableListOf()
                intensitiesInts.forEach {
                    training_data.add(TrainingData(TrainingMetricType.DISTANCE,it))
                }
            }
            TrainingMetricType.KCAL -> {
                training_data = mutableListOf()
                intensitiesInts.forEach {
                    training_data.add(TrainingData(TrainingMetricType.KCAL,it))
                }

            }
            TrainingMetricType.BPM -> {
                training_data = mutableListOf()
                intensitiesInts.forEach {
                    training_data.add(TrainingData(TrainingMetricType.BPM,it))
                }

            }
            TrainingMetricType.REST -> {
                training_data = mutableListOf()
                intensitiesInts.forEach {
                    training_data.add(TrainingData(TrainingMetricType.REST,it))
                }

            }
        }

    }

    GainsAppTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            androidx.compose.material3.Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Your Training Overview",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Text(selectedPeriod.name, style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface)

                            IconButton(onClick = { popupVisible = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Change Metric")
                            }
                            Spacer(modifier = Modifier.width(170.dp))
                            Text(selectedMetric.name, style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface)

                            IconButton(onClick = { popupVisible = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Change Metric")
                            }
                        }
                        MetricPopup(
                            popupVisible = popupVisible,
                            onDismiss = { popupVisible = false },
                            onOptionSelected = { metric ->
                                selectedMetric = metric
                                popupVisible = false
                            },
                            selectedMetric = selectedMetric
                        )

                        PeriodPopup(
                            popupVisible = popupVisible,
                            onDismiss = { popupVisible = false },
                            onOptionSelected = { period ->
                                selectedPeriod = period
                                popupVisible = false
                            },
                            selectedMetric = selectedPeriod
                        )

                        TrainingOverviewChart(
                            trainingData =training_data ,
                            selectedMetric = selectedMetric,
                            selectedPeriod = selectedPeriod,
                            shareHandler = shareHandler
                        )
                    }
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