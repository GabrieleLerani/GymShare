package com.project.gains.presentation.events

import com.project.gains.presentation.workout.events.ManageWorkoutEvent

sealed class PreviousPageEvent {
    data class SelectPreviewsPage(val name: String) : PreviousPageEvent()
}