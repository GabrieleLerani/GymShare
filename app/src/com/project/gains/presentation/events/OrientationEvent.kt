package com.project.gains.presentation.events

sealed class OrientationEvent {
    data object ChangeOrientation : OrientationEvent()
}