package com.project.gains.presentation.plan.events

import com.project.gains.data.Frequency
import com.project.gains.data.Level
import com.project.gains.data.Plan
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout

sealed class ManagePlanEvent {
    data class CreatePlan(
        val workouts: List<Workout>,
        val selectedMusic: Boolean,
        val selectedBackup: Boolean
    ) : ManagePlanEvent()

    data class SetPlanOptions(
        val selectedLevel: Level,
        val selectedTrainingType: TrainingType,
        val selectedFrequency: Frequency
    ) : ManagePlanEvent()

    data class SelectPlan(val plan : Plan) : ManagePlanEvent()

    data class DeletePlan(val plan: Plan) : ManagePlanEvent()
}