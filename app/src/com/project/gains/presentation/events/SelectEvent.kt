package com.project.gains.presentation.events

import com.project.gains.data.Exercise
import com.project.gains.data.Plan
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.TrainingData
import com.project.gains.data.Workout

sealed class SelectEvent {
    data class SelectExercise(val exercise: Exercise) : SelectEvent()
    data class SelectLinkedApp(val app: Int) : SelectEvent()
    data class SelectPlan(val plan : Plan) : SelectEvent()
    data class SelectPlotPreview(val preview: ProgressChartPreview) : SelectEvent()
    data class SelectWorkout(val workout : Workout) : SelectEvent()

}