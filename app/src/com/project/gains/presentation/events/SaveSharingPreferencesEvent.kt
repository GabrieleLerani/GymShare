package com.project.gains.presentation.events

import com.project.gains.data.Session

sealed class SaveSharingPreferencesEvent {
    data class SaveSharingPreferences(val apps: MutableList<Int>) : SaveSharingPreferencesEvent()
}