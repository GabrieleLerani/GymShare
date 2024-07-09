package com.project.gains.presentation.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.gains.domain.usecase.linkedSocial.LinkedSocialUseCases
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.ManageDataStoreEvent
import com.project.gains.presentation.settings.events.ManageDialogEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareContentViewModel @Inject constructor(
    private val linkedSocialUseCases: LinkedSocialUseCases
) : ViewModel(){

    private val _linkedSharingMedia = MutableLiveData<MutableList<ImageVector>>()
    val linkedSharingMedia: MutableLiveData<MutableList<ImageVector>> = _linkedSharingMedia

    // used to store linked social network
    private val _linkedApps = MutableLiveData<MutableList<Int>>()
    val linkedApps: MutableLiveData<MutableList<Int>> = _linkedApps

    private val _showDialogShared = MutableLiveData<Boolean>()
    val showDialogShared: MutableLiveData<Boolean> = _showDialogShared

    init {
        _linkedApps.value = mutableListOf()

        _linkedSharingMedia.value?.add(Icons.Default.Email)
        _linkedSharingMedia.value?.add(Icons.AutoMirrored.Filled.Message)
    }

    fun onSaveSharingPreferencesEvent(event: ManageDataStoreEvent) {

        when (event) {
            is ManageDataStoreEvent.Save -> {
                viewModelScope.launch {
                    linkedSocialUseCases.storeLinkedSocial(event.apps)
                    _linkedApps.value = event.apps
                }
            }

            ManageDataStoreEvent.Retrieve -> {
                viewModelScope.launch {
                    _linkedApps.value = linkedSocialUseCases.fetchLinkedSocial().toMutableList()
                }
            }
        }
    }


    fun onLinkAppEvent(event: LinkAppEvent) {
        when (event) {
            is LinkAppEvent.LinkApp -> {
                _linkedApps.value?.add(event.app)
            }
        }
    }

    fun onManageDialogEvent(event: ManageDialogEvent) {
        when (event) {
            is ManageDialogEvent.SelectShowDialogShared -> {
                _showDialogShared.value = event.value
            }
        }
    }
}