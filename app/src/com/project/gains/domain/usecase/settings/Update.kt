package com.mygdx.game.domain.usecase.settings

import com.project.gains.domain.manager.SettingsManager

class Update(
    private val settingsUsecase: SettingsManager
) {
    suspend operator fun invoke(name:String, email:String, password:String) {
       settingsUsecase.update(name,email,password)
    }
}