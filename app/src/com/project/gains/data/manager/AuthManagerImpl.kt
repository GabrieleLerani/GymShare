package com.project.gains.data.manager

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.biometrics.BiometricPrompt
import android.location.Location
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.project.gains.data.UserProfileBundle

import kotlinx.coroutines.tasks.await


import com.project.gains.domain.manager.AuthManager
import com.project.gains.domain.manager.LocalUserManager
import com.project.gains.util.Constants.EMAIL_IS_IN_USE
import com.project.gains.util.Constants.LOGIN_FAILED
import com.project.gains.util.Constants.LOGIN_SUCCESS
import com.project.gains.util.Constants.SIGN_UP_SUCCESS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface UpdateListener {
    fun onUpdate(data: Location)
    fun onUpdate(data: String)

}
class AuthManagerImpl @Inject constructor (private val firebaseAuth: FirebaseAuth?,
                                           private val firestore: FirebaseFirestore,
                                           private val localUserManager: LocalUserManager
) : AuthManager {
    private var updateListener: UpdateListener? = null
    private lateinit var contxt: Context

    override suspend fun signIn(email: String, password: String, update: Boolean) : Boolean{
        val success :Boolean = doSignIn(email, password)
        if (success) {
            try {
                // Sign-in was successful,maybe I did a signOut and lost the local data so we fetch those data
                val name = firebaseAuth?.currentUser?.displayName ?: email
                // save user profile application state
                val userProfileBundle =
                    UserProfileBundle(displayName = name, email = email)
                localUserManager.saveUserProfile(userProfileBundle)


                // save bio application state
                if(update) {
                    // Notify listener of successful sign-up
                    updateListener?.onUpdate(LOGIN_SUCCESS)
                }
                return true
            } catch (e: Exception) {
                // Handle exceptions within runBlocking block
                Log.e("AUTH_MANAGER",e.toString())
                if(update) {
                    // Notify listener of successful sign-up
                    updateListener?.onUpdate("Error: ${e.message}")
                }
                return false
            }
        } else {
            // Sign-in failed or fields were empty, handle accordingly
            if(update) {
                // Notify listener of successful sign-up
                updateListener?.onUpdate(LOGIN_FAILED)
            }
            return false
        }
    }



    override suspend fun signUp(name: String, email: String, password: String, confirmPass: String) {
        if (password != confirmPass) {
            updateListener?.onUpdate("Two passwords must coincide")
            return // Return early if passwords don't match
        }
        firebaseAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign-up success
                    Log.d(TAG, "New User $email,$password created successfully")
                    // Update user profile
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        //.setPhotoUri(createDummyPhotoUri())
                        .build()

                    firebaseAuth.currentUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                // Display name updated successfully
                                Log.d(TAG, "Display name: $name updated successfully")
                                try {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        // Save user profile application state and so player local state
                                        val userProfileBundle = UserProfileBundle(
                                            displayName = name,
                                            email = email
                                        )
                                        localUserManager.saveUserProfile(userProfileBundle)
                                        // Save user rank


                                        localUserManager.saveFirestoreDocumentId(name)
                                        // Save bio application state

                                    }
                                    // Notify listener of successful sign-up
                                    updateListener?.onUpdate(SIGN_UP_SUCCESS)
                                } catch (e: Exception) {
                                    Log.d(TAG, "Failed to set user state: ${e.message}")
                                    // Handle exceptions within runBlocking block
                                    updateListener?.onUpdate("Error: ${e.message}")
                                }
                            } else {
                                // Display name update failed
                                Log.d(TAG, "Failed to update display name: ${updateTask.exception?.message}")
                                // Notify listener of failure
                                updateListener?.onUpdate("Failed to update display name: ${updateTask.exception?.message}")
                            }
                        }
                } else {
                    // Sign-up failed
                    val errorMessage = task.exception?.message ?: "Unknown error"
                    updateListener?.onUpdate(errorMessage)
                }
            }
            ?.addOnFailureListener { exception ->
                // Handle exceptions
                if (exception is FirebaseAuthUserCollisionException) {
                    // User already exists with the same email
                    updateListener?.onUpdate(EMAIL_IS_IN_USE)
                } else {
                    // Other exceptions
                    updateListener?.onUpdate(exception.message.toString())
                }
            }
    }




    override fun authCheck(): Boolean {
        return firebaseAuth?.currentUser != null && localUserManager.getUserProfile().email.isNotEmpty()
    }




    override fun setUpdateListener(ref: UpdateListener) {
        updateListener=ref
    }




    private suspend fun doSignIn(email: String, password: String): Boolean {
        return if (email.isNotEmpty() && password.isNotEmpty()) {
            try {
                val task = firebaseAuth?.signInWithEmailAndPassword(email, password)?.await()
                task?.user != null // Check if the user is not null to determine success
            } catch (e: Exception) {
                // Handle any exceptions
                Log.e("AUTH_MANAGER",e.message.toString())
                false
            }
        } else {
            false
        }
    }

}