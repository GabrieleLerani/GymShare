package com.project.gains.presentation.authentication.events

sealed class OTPEvent {
    data object GenerateOTP : OTPEvent()
}