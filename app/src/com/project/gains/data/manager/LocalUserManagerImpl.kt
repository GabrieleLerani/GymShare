package com.project.gains.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson


import com.project.gains.data.UserProfileBundle
import com.project.gains.domain.manager.LocalUserManager
import com.project.gains.util.Constants
import com.project.gains.util.Constants.USER_SETTINGS

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/*
* Implementation of user manager, it implements method to store user preferences on a datastore,
* accessible from the application context*/
class LocalUserManagerImpl(
    private val context: Context,
): LocalUserManager {

    private var updateListener: UpdateListener? = null

    private val dataStore: DataStore<Preferences> = context.dataStore

    private val gson = Gson()

    override suspend fun saveAppEntry() {
        dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): List<Boolean> {
        val result = mutableListOf<Boolean>()
        val data = dataStore.data
        val preferences = runBlocking { data.first() } // Blocking operation to get the first emission
        result.add(preferences[PreferencesKeys.APP_ENTRY] ?: false)

        return result
    }

    override suspend fun clearAppEntry() {
        dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = false
        }
    }


    override suspend fun saveFirestoreDocumentId(name: String) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.FIRESTORE_ID] = name
        }
    }

    override fun readFirestoreDocumentId(): String {
        val data = dataStore.data
        val preferences = runBlocking { data.first() } // Blocking operation to get the first emission
        return preferences[PreferencesKeys.FIRESTORE_ID] ?: ""
    }




    override fun getUserProfile(): UserProfileBundle {
        val data = dataStore.data
        val preferences = runBlocking { data.first() } // Blocking operation to get the first emission
        val displayName = preferences[PreferencesKeys.DISPLAY_NAME] ?: ""
        val email = preferences[PreferencesKeys.EMAIL] ?: ""
        return UserProfileBundle(displayName, email)
    }

    override suspend fun saveUserProfile(userProfile: UserProfileBundle) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.DISPLAY_NAME] = userProfile.displayName
            settings[PreferencesKeys.EMAIL] = userProfile.email
        }

    }


    override suspend fun saveLinkedSocial(apps: List<Int>) {
        val appStrings = gson.toJson(apps)
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LINKED_APPS] = appStrings
        }
    }


    override suspend fun getLinkedSocial(): List<Int> {
        val preferences = runBlocking {
            dataStore.data.first()
        }
        val linkedApps = preferences[PreferencesKeys.LINKED_APPS] ?: "[]"
        return gson.fromJson(linkedApps, Array<Int>::class.java).toList()
    }

    override fun setUpdateListener(ref: UpdateListener) {
        updateListener = ref
    }
}

// Define a datastore to keep user preferences
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

// Define a boolean preferences
private object PreferencesKeys{
    // app entry preferences
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)

    val FIRESTORE_ID = stringPreferencesKey(name = Constants.FIRESTORE_ID)

    val LINKED_APPS = stringPreferencesKey(name = Constants.LINKED_APPS)

    // auth user
    val DISPLAY_NAME = stringPreferencesKey("display_name")
    val EMAIL = stringPreferencesKey("email")

}

