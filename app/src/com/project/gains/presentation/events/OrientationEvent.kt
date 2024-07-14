package com.project.gains.presentation.events

sealed class OrientationEvent {
    data class SetOrientation(val newOrientation: Int) : OrientationEvent()
}