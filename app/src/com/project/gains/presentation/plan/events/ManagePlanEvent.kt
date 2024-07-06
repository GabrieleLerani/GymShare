package com.project.gains.presentation.plan.events

import com.project.gains.data.ExerciseType
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.Plan
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.TrainingType

sealed class ManagePlanEvent {
    data class CreatePlan(
        val selectedMetricType: MutableList<TrainingMetricType>,
        val selectedExerciseType: MutableList<ExerciseType>,
        val selectedMusic: Boolean,
        val selectedBackup: Boolean
    ) : ManagePlanEvent()

    data class SetPlanOptions(
        val selectedLevel: Level,
        val selectedPeriod: PeriodMetricType,
        val selectedTrainingType: TrainingType,

        ) : ManagePlanEvent()

    data class SelectPlan(val plan : Plan) : ManagePlanEvent()

    data class DeletePlan(val plan: Plan) : ManagePlanEvent()

    data class SelectShowDialogPlan(val value:Boolean) : ManagePlanEvent()
}