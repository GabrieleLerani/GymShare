package com.project.gains.presentation

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.data.UserProfileBundle

import com.project.gains.domain.usecase.appEntry.AppEntryUseCases
import com.project.gains.domain.usecase.auth.AuthenticationUseCases
import com.project.gains.presentation.events.OrientationEvent
import com.project.gains.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel


import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val authenticationUseCases: AuthenticationUseCases,
): ViewModel() {

    private var _userProfile = MutableLiveData(appEntryUseCases.readUser())
    val userProfile: LiveData<UserProfileBundle?> = _userProfile

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)

    private val _orientation = MutableLiveData<Int>()
    val orientation: LiveData<Int> get() = _orientation

    init {
        _orientation.value = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            startDestination = if (shouldStartFromHomeScreen) {
                if (authenticationUseCases.authCheck()) {
                    Route.HomeScreen.route
                } else {
                    Route.SignInScreen.route
                }
            } else {
                Route.OnBoardingScreen.route
            }
        }
    }

    fun onOrientationEvent(event: OrientationEvent) {
        when(event) {
            is OrientationEvent.ChangeOrientation -> {
                if (_orientation.value == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    _orientation.value = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                } else {
                    _orientation.value = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
            }
        }
    }

    fun isAuth() : Boolean{
        return authenticationUseCases.authCheck()
    }

    override fun onCleared() {
        super.onCleared()
        release()
    }


    private fun release(){
        Log.d("MAIN VIEW","releasing resources")
        _userProfile.value=null
    }

    fun resume(){
        _userProfile.value = appEntryUseCases.readUser()
        Log.d("MAIN VIEW","resuming resources ${_userProfile.value}")
    }

}



