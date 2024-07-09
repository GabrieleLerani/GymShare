package com.project.gains.presentation.favourite.events

import com.project.gains.data.Exercise
import com.project.gains.data.Workout


sealed class FavouriteEvent {
    data class AddExercise(val exercise: Exercise) : FavouriteEvent()
    data class DeleteExercise(val exercise: Exercise) : FavouriteEvent()
    data class AddWorkout(val workout: Workout) : FavouriteEvent()
    data class DeleteWorkout(val workout: Workout) : FavouriteEvent()

}