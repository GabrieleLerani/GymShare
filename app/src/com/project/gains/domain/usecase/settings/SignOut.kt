package com.mygdx.game.domain.usecase.settings

import com.project.gains.domain.manager.SettingsManager

class SignOut(
    private val settingsManager: SettingsManager
){
    suspend operator fun invoke(){
        settingsManager.signOut()
    }
}