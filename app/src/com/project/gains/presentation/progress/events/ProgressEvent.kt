package com.project.gains.presentation.progress.events

import com.project.gains.data.ExerciseType
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.Plan
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.TrainingType
import com.project.gains.presentation.plan.events.ManagePlanEvent


sealed class ProgressEvent {

    data class SelectPlotPreview(val preview: ProgressChartPreview) : ProgressEvent()
}