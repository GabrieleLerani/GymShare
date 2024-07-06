package com.project.gains.presentation.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.SaveSharingPreferencesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareContentViewModel @Inject constructor() : ViewModel(){

    // TODO check if used
    private val _linkedSharingMedia = MutableLiveData<MutableList<ImageVector>>()
    val linkedSharingMedia: MutableLiveData<MutableList<ImageVector>> = _linkedSharingMedia

    // used to store linked social network
    private val _linkedApps = MutableLiveData<MutableList<Int>>()
    val linkedApps: MutableLiveData<MutableList<Int>> = _linkedApps

    init {

        _linkedApps.value = mutableListOf()

        _linkedSharingMedia.value?.add(Icons.Default.Email)
        _linkedSharingMedia.value?.add(Icons.AutoMirrored.Filled.Message)
    }

    fun onSaveSharingPreferencesEvent(event: SaveSharingPreferencesEvent) {
        // TODO implement storage on data store

        when (event) {
            is SaveSharingPreferencesEvent.SaveSharingPreferences -> {
                _linkedApps.value = (_linkedApps.value?.plus(event.apps))?.toSet()?.toMutableList()
            }

        }
    }



    fun onLinkAppEvent(event: LinkAppEvent) {
        // TODO implement saving on data store
        when (event) {
            is LinkAppEvent.LinkApp -> {
                _linkedApps.value?.add(event.app)
            }
        }
    }
}