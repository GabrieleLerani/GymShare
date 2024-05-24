package com.project.gains.domain.manager

import android.content.Context
import com.project.gains.data.manager.UpdateListener

interface AuthManager {
    suspend fun signUp(name:String,email:String,password:String,confirmPass:String)

    fun authCheck():Boolean

    fun setUpdateListener(ref: UpdateListener)
    suspend fun signIn(email: String, password: String, update: Boolean): Boolean
}