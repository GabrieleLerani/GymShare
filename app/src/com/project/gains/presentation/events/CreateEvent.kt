package com.project.gains.presentation.events

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.project.gains.data.Exercise
import com.project.gains.data.Level
import com.project.gains.data.Option
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout

sealed class CreateEvent {

    data class CreateWorkout(val workout : Workout) : CreateEvent()

    data class CreateExercise(val exercise: Exercise) : CreateEvent()

    data class CreatePlan(
        val selectedOptions: SnapshotStateList<Option>,
        val selectedPeriod: PeriodMetricType,
        val selectedExerciseType: SnapshotStateList<TrainingType>,
        val selectedMetricType: MutableList<TrainingMetricType>,
        val selectedTrainingType: TrainingType,
        val selectedMusic: Boolean,
        val selectedBackup: Boolean

    ) : CreateEvent()

    data class SetPlanOptions(
        val selectedLevel: Level,
        val selectedPeriod: PeriodMetricType,
        val selectedTrainingType: TrainingType,

    ) : CreateEvent()



}