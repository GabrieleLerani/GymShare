package com.project.gains.domain.manager

import android.location.Location
import com.project.gains.data.UserProfileBundle
import kotlinx.coroutines.flow.Flow


import com.project.gains.data.manager.UpdateListener


interface LocalUserManager {
    suspend fun saveAppEntry()
    fun readAppEntry(): List<Boolean>
    suspend fun clearAppEntry()
    fun getUserProfile(): UserProfileBundle
    suspend fun saveUserProfile(userProfile: UserProfileBundle)
    suspend fun saveAnchorId(anchorId: String)
    fun readAnchorId(): Flow<String>




    fun setUpdateListener(ref: UpdateListener)
    fun getObject(key: String): String
    fun saveObject(key: String, item: Any)
    suspend fun saveFirestoreDocumentId(name: String)
    fun readFirestoreDocumentId(): String
    fun getObjectList(key: String): List<String>
}