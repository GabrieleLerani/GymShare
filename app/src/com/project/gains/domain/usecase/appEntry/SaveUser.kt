package com.project.gains.domain.usecase.appEntry

import com.project.gains.data.UserProfileBundle
import com.project.gains.domain.manager.LocalUserManager


class SaveUser(
    private val localUserManager: LocalUserManager
){
    suspend operator fun invoke(userProfileBundle: UserProfileBundle) {
        return localUserManager.saveUserProfile(userProfileBundle)
    }
}