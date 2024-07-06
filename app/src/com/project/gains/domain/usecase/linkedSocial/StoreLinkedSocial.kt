package com.project.gains.domain.usecase.linkedSocial

import com.project.gains.domain.manager.LocalUserManager
import com.project.gains.domain.manager.SettingsManager

class StoreLinkedSocial(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(linkedApps: List<Int>){
        localUserManager.saveLinkedSocial(linkedApps)
    }
}