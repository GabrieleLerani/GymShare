package com.project.gains.domain.usecase


import com.project.gains.domain.manager.SettingsManager

import com.project.gains.data.manager.UpdateListener
import com.project.gains.domain.manager.AuthManager
import com.project.gains.util.Constants.USER_AUTH
import com.project.gains.util.Constants.USER_SETTINGS


class Subscribe(
    private val settingsManager: SettingsManager,
    private val authManager: AuthManager

){
    operator fun invoke(ref: UpdateListener, type:String){
        when (type) {
            USER_AUTH ->  authManager.setUpdateListener(ref)
            USER_SETTINGS -> settingsManager.setUpdateListener(ref)
        }
    }

}