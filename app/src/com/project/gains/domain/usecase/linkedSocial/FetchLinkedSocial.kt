package com.project.gains.domain.usecase.linkedSocial

import com.project.gains.data.UserProfileBundle
import com.project.gains.domain.manager.LocalUserManager
import com.project.gains.domain.manager.SettingsManager

class FetchLinkedSocial(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke() : List<Int> {
        return localUserManager.getLinkedSocial()
    }
}