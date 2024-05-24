package com.project.gains.domain.usecase.auth

import com.project.gains.domain.manager.AuthManager


class AuthCheck(
    private val authManager: AuthManager
){
     operator fun invoke():Boolean{
        return authManager.authCheck()
    }

}