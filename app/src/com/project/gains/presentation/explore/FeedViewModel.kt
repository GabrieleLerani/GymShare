package com.project.gains.presentation.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.data.GymPost
import com.project.gains.data.generateRandomGymPost
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor() : ViewModel() {
    private val _posts = MutableLiveData<MutableList<GymPost>>()
    val posts: MutableLiveData<MutableList<GymPost>> = _posts

    init {
        _posts.value = generateRandomGymPost(10).toMutableList()
    }
}