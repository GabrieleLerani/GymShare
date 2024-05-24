package com.mygdx.game.domain.usecase.settings


import com.project.gains.data.UserProfileBundle
import com.project.gains.domain.manager.SettingsManager


class FetchUserProfile(
    private val settingsManager: SettingsManager
) {
     suspend operator fun invoke() : UserProfileBundle? {
        return settingsManager.fetch()
    }
}