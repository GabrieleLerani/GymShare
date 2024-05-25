package com.project.gains.presentation.events

import com.project.gains.data.Session


sealed class SaveSessionEvent{
    data class SaveSession(val plan:Int,val workout:Int,val session: Session) : SaveSessionEvent()
}