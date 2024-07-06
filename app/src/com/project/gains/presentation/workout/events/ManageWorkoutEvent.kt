package com.project.gains.presentation.workout.events

import com.project.gains.data.Workout
import com.project.gains.presentation.events.SelectEvent

sealed class ManageWorkoutEvent {
    data class CreateWorkout(val workout : Workout) : ManageWorkoutEvent()
    data class DeleteWorkout(val workout : Workout) : ManageWorkoutEvent()

    data class SelectWorkout(val workout : Workout) : ManageWorkoutEvent()

}