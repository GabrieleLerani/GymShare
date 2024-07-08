package com.project.gains.presentation.events

sealed class PreviousPageEvent {
    data class SelectPreviewsPage(val name: String) : PreviousPageEvent()
}