package com.project.gains.presentation.settings.events


sealed class ManageDialogEvent {
    data class SelectShowDialogShared(val value:Boolean) : ManageDialogEvent()
}