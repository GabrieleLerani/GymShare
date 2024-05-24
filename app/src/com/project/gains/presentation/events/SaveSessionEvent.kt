package com.project.gains.presentation.events

import com.project.gains.data.Session


sealed class SaveSessionEvent{
    data class SaveSession(val session: Session) : SaveSessionEvent()
}