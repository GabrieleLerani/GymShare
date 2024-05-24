package com.project.gains.domain.usecase.appEntry

import com.project.gains.domain.manager.LocalUserManager

class ReadAppEntry(
    private val localUserManager: LocalUserManager
){
     operator fun invoke(): List<Boolean> {
        return localUserManager.readAppEntry()
    }
}