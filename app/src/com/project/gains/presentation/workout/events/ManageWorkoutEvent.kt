package com.project.gains.presentation.workout.events

import com.project.gains.data.Workout

sealed class ManageWorkoutEvent {
    data class CreateWorkout(val workout : Workout) : ManageWorkoutEvent()
    data class DeleteWorkout(val workout : Workout) : ManageWorkoutEvent()
}