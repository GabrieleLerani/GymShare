package com.project.gains.presentation.workout.events

sealed class VideoEvent {
    data class VisibilityVideoEvent(val visible: Boolean) : VideoEvent()
    data class FullscreenVideoEvent(val fullscreen: Boolean) : VideoEvent()
}