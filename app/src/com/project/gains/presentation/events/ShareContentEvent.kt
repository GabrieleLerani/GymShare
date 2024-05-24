package com.project.gains.presentation.events

import com.project.gains.data.Exercise
import com.project.gains.data.GymPost
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.Plan
import com.project.gains.data.Session
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.Workout

sealed class ShareContentEvent {
    data class ShareWorkout(val workout : Workout) : ShareContentEvent()

    data class ShareExercise(val exercise: Exercise) : ShareContentEvent()

    data class SharePlan(val plan: Plan) : ShareContentEvent()

    data class ShareSession(val session: Session) : ShareContentEvent()

    data class SharePlot(
        val plot: List<TrainingData>,
        val selectedMetric: TrainingMetricType,
        val selectedPeriod: PeriodMetricType
    ) : ShareContentEvent()

    data class ShareLink(val post: GymPost) : ShareContentEvent()
}