package com.project.gains.presentation.workout.events

sealed class VideoEvent {
    data class VisibilityVideoEvent(val visible: Boolean) : VideoEvent()
}