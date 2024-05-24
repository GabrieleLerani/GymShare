package com.project.gains.presentation.events

import com.project.gains.data.Exercise
import com.project.gains.data.Plan
import com.project.gains.data.Workout

sealed class DeleteEvent {

    data class DeleteWorkout(val workout : Workout) : DeleteEvent()

    data class DeleteExercise(val exercise: Exercise) : DeleteEvent()

    data class DeletePlan(val plan: Plan) : DeleteEvent()
}