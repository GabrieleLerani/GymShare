package com.project.gains.domain.usecase.auth

import com.project.gains.domain.usecase.Subscribe

data class AuthenticationUseCases (
    val signIn: SignIn,
    val signUp: SignUp,
    val authCheck: AuthCheck,
    val subscribe: Subscribe

)