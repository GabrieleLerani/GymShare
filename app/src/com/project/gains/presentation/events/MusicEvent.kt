package com.project.gains.presentation.events

sealed class MusicEvent {
    data object Music : MusicEvent()
    data object  Rewind : MusicEvent()

    data object  Forward: MusicEvent()
}