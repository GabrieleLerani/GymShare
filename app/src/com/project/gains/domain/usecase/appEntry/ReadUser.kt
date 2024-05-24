package com.project.gains.domain.usecase.appEntry

import com.project.gains.data.UserProfileBundle
import com.project.gains.domain.manager.LocalUserManager

class ReadUser(
    private val localUserManager: LocalUserManager
){
     operator fun invoke(): UserProfileBundle {
        return localUserManager.getUserProfile()
    }
}