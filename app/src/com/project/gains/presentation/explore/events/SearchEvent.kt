package com.project.gains.presentation.explore.events

import com.project.gains.data.Categories
import com.project.gains.data.Exercise
import com.project.gains.data.Workout

sealed class SearchEvent {
    data class SearchGymPostEvent(val text: String, val selectedCategory: Categories?) : SearchEvent()
    data class GymPostWorkoutEvent(val workout: Workout) : SearchEvent()

}