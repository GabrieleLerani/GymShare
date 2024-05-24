package com.project.gains.presentation.settings.events

sealed class SignOutEvent {
     data object SignOut : SignOutEvent()
}