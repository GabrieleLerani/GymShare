package com.project.gains.domain.usecase.auth

import com.project.gains.domain.manager.AuthManager

class SignIn(
    private val authManager: AuthManager
){
    suspend operator fun invoke(email:String,password:String){
        authManager.signIn(email = email, password = password, update = true)
    }

}