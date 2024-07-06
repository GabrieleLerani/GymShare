package com.project.gains.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Message
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareContentViewModel @Inject constructor() : ViewModel(){

    private val _linkedSharingMedia = MutableLiveData<MutableList<ImageVector>>()
    val linkedSharingMedia: MutableLiveData<MutableList<ImageVector>> = _linkedSharingMedia

    init {
        _linkedSharingMedia.value?.add(Icons.Default.Email)
        _linkedSharingMedia.value?.add(Icons.AutoMirrored.Filled.Message)
    }
}