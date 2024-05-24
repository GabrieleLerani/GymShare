package com.project.gains.data.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.gains.data.UserProfileBundle
import com.project.gains.domain.manager.LocalUserManager
import com.project.gains.util.Constants
import com.project.gains.util.Constants.USER_SETTINGS
import com.project.gains.util.Constants.USER_SETTINGS2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/*
* Implementation of user manager, it implements method to store user preferences on a datastore,
* accessible from the application context*/
class LocalUserManagerImpl(
    private val context: Context,
 private val firestore: FirebaseFirestore
): LocalUserManager {

    private var updateListener: UpdateListener? = null
    val dataScope = CoroutineScope(Dispatchers.IO)
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(USER_SETTINGS2, Context.MODE_PRIVATE)
    private val gson = Gson()



    override fun saveObject(key: String, item: Any) {
        val jsonString = gson.toJson(item)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    override fun getObject(key: String): String {
        val jsonString = sharedPreferences.getString(key, null)
        return gson.fromJson(jsonString, String::class.java)
    }

    override fun getObjectList(key: String): List<String> {
        val jsonString = sharedPreferences.getString(key, null)

        // Check if jsonString is null
        if (jsonString == null) {
            return emptyList() // Return an empty list if jsonString is null
        }

        // Define the type of the list
        val listType = object : TypeToken<List<String>>() {}.type

        // Deserialize the JSON array into a list of strings
        val resultList: List<String> = gson.fromJson(jsonString, listType)
        return resultList
    }


    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): List<Boolean> {
        val result = mutableListOf<Boolean>()
        val data = context.dataStore.data
        val preferences = runBlocking { data.first() } // Blocking operation to get the first emission
        result.add(preferences[PreferencesKeys.APP_ENTRY] ?: false)

        return result
    }

    override suspend fun clearAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = false
        }
    }

    override suspend fun saveAnchorId(anchorId: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.CLOUD_ANCHOR_ID] = anchorId
        }
    }

    override fun readAnchorId(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.CLOUD_ANCHOR_ID] ?: ""
        }
    }



    override suspend fun saveFirestoreDocumentId(name: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.FIRESTORE_ID] = name
        }
    }

    override fun readFirestoreDocumentId(): String {
        val data = context.dataStore.data
        val preferences = runBlocking { data.first() } // Blocking operation to get the first emission
        val name = preferences[PreferencesKeys.FIRESTORE_ID] ?: ""
        return name
    }







    override fun getUserProfile(): UserProfileBundle {
        val data = context.dataStore.data
        val preferences = runBlocking { data.first() } // Blocking operation to get the first emission
        val displayName = preferences[PreferencesKeys.DISPLAY_NAME] ?: ""
        val email = preferences[PreferencesKeys.EMAIL] ?: ""
        return UserProfileBundle(displayName, email)
    }

    override suspend fun saveUserProfile(userProfile: UserProfileBundle) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.DISPLAY_NAME] = userProfile.displayName
            settings[PreferencesKeys.EMAIL] = userProfile.email
        }
        saveObject(Constants.USER,userProfile.email)
        saveObject(Constants.USERNAME,userProfile.displayName)
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
    // ar entry preferences
    val CLOUD_ANCHOR_ID = stringPreferencesKey(name = Constants.CLOUD_ANCHOR_ID)
    // firestore
    val FIRESTORE_ID = stringPreferencesKey(name = Constants.FIRESTORE_ID)

    // auth user
    val DISPLAY_NAME = stringPreferencesKey("display_name")
    val EMAIL = stringPreferencesKey("email")
    // user ranking and player
    val SCORE = stringPreferencesKey("score")
}

