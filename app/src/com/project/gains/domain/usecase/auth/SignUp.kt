package com.project.gains.domain.usecase.auth

import com.project.gains.domain.manager.AuthManager

class SignUp(
    private val authManager: AuthManager
){
    suspend operator fun invoke(name:String,email:String,password:String,confirmPass:String){
        authManager.signUp(name=name,email = email, password = password,confirmPass=confirmPass)
    }
}
