@file:Suppress("DEPRECATION")

package com.project.gains.data.manager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.project.gains.data.UserProfileBundle


import com.project.gains.domain.manager.LocalUserManager
import com.project.gains.domain.manager.SettingsManager
import com.project.gains.util.Constants.SIGN_OUT_SUCCESS
import com.project.gains.util.Constants.UPDATE_SUCCESS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsManagerImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth?,
    private val localUserManager: LocalUserManager,

) : SettingsManager {
    private var updateListener: UpdateListener? = null

    override suspend fun update(name: String, email: String, password: String) {

        val user = firebaseAuth?.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        if (password != "New Password") {
            user?.updateEmail(email)?.continueWithTask { emailUpdateTask ->
                if (emailUpdateTask.isSuccessful) {
                    return@continueWithTask user.updatePassword(password)
                } else {
                    throw emailUpdateTask.exception!!
                }
            }?.continueWithTask { passwordUpdateTask ->
                if (passwordUpdateTask.isSuccessful) {
                    return@continueWithTask user.updateProfile(profileUpdates)
                } else {
                    throw passwordUpdateTask.exception!!
                }
            }?.addOnCompleteListener { combinedTask ->
                if (combinedTask.isSuccessful) {

                    try {
                        CoroutineScope(Dispatchers.IO).launch {
                            val oldName= localUserManager.readFirestoreDocumentId()
                            // save user profile application state
                            val userProfileBundle =
                                UserProfileBundle(displayName = name, email = email)
                            localUserManager.saveUserProfile(userProfileBundle)
                            // update local bio
                        }
                        // All updates were successful
                        updateListener?.onUpdate(UPDATE_SUCCESS)
                    } catch (e: Exception) {
                        // Handle exceptions within runBlocking block
                        updateListener?.onUpdate("Error: ${e.message}")
                    }
                } else {
                    // Handle the failure, you can access the exception from combinedTask.exception
                    updateListener?.onUpdate("Something went wrong, retry later")
                }
            }
        }
        else {
            user?.updateEmail(email)?.continueWithTask { emailUpdateTask ->
                if (emailUpdateTask.isSuccessful) {
                    return@continueWithTask user.updateProfile(profileUpdates)
                } else {
                    println(emailUpdateTask.exception!!.message)
                    throw emailUpdateTask.exception!!
                }
            }?.addOnCompleteListener { combinedTask ->
                if (combinedTask.isSuccessful) {
                    CoroutineScope(Dispatchers.IO).launch {
                            val oldName= localUserManager.readFirestoreDocumentId()
                            // save user profile application state
                            val userProfileBundle =
                                UserProfileBundle(displayName = name, email = email)
                            localUserManager.saveUserProfile(userProfileBundle)
                            // update local bio
                    }
                    // All updates were successful
                    updateListener?.onUpdate(UPDATE_SUCCESS)
                } else {
                    // Handle the failure, you can access the exception from combinedTask.exception
                    updateListener?.onUpdate("Something went wrong, retry later")
                }
            }
        }
    }

    override suspend fun fetch(): UserProfileBundle {
        return localUserManager.getUserProfile()
    }


    override suspend fun signOut() {
        try {
            // clear firebase
            firebaseAuth?.signOut()

            // clear user profile state
            val userProfileBundle= UserProfileBundle("", "")
            localUserManager.saveUserProfile(userProfileBundle)
            // update listener
            updateListener?.onUpdate(SIGN_OUT_SUCCESS)

        } catch (e: Exception) {
            updateListener?.onUpdate(e.message.toString())
        }
    }

    override fun setUpdateListener(ref: UpdateListener) {
        updateListener=ref    }

}






