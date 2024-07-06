package com.project.gains.domain.manager

import com.project.gains.data.UserProfileBundle


import com.project.gains.data.manager.UpdateListener


interface LocalUserManager {
    suspend fun saveAppEntry()
    fun readAppEntry(): List<Boolean>
    suspend fun clearAppEntry()
    fun getUserProfile(): UserProfileBundle
    suspend fun saveUserProfile(userProfile: UserProfileBundle)


    fun setUpdateListener(ref: UpdateListener)

    suspend fun saveFirestoreDocumentId(name: String)
    fun readFirestoreDocumentId(): String

}