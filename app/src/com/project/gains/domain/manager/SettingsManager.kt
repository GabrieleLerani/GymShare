package com.project.gains.domain.manager


import com.project.gains.data.UserProfileBundle
import com.project.gains.data.manager.UpdateListener


interface SettingsManager {
    suspend fun update(name:String,email:String,password:String)

    suspend fun signOut()
    suspend fun fetch(): UserProfileBundle?
    fun setUpdateListener(ref: UpdateListener)
}