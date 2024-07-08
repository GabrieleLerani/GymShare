package com.project.gains.presentation.events

sealed class LinkAppEvent {
    data class LinkApp(val app: Int) : LinkAppEvent()

}