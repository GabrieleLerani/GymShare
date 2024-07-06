package com.project.gains.presentation.events

sealed class ManageDataStoreEvent {
    data class Save(val apps: MutableList<Int>) : ManageDataStoreEvent()
    data object Retrieve : ManageDataStoreEvent()
}