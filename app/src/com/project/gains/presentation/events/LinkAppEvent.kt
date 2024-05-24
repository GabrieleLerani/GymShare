package com.project.gains.presentation.events

import com.project.gains.data.Session

sealed class LinkAppEvent {
    data class LinkApp(val app: Int) : LinkAppEvent()

}