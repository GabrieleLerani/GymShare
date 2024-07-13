package com.project.gains.presentation.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.R
import com.project.gains.data.Categories
import com.project.gains.data.GymPost
import com.project.gains.data.generateRandomGymPost
import com.project.gains.presentation.explore.events.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor() : ViewModel() {
    private val _posts = MutableLiveData<MutableList<GymPost>>()
    val posts: MutableLiveData<MutableList<GymPost>> = _posts

    init {
        _posts.value = generateRandomGymPost(10).toMutableList()
    }

    fun onSearchEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.SearchGymPostEvent -> {

                val temporalPosts = _posts.value ?: mutableListOf()
                val results: MutableList<GymPost> = mutableListOf()

                when (event.selectedCategory) {
                    Categories.User -> {
                        temporalPosts.forEach { post ->
                            if (post.username.contains(event.text)) {
                                results.add(post)
                            }
                        }
                    }

                    Categories.Workout -> {
                        temporalPosts.forEach { post ->
                            // TODO whenever a new post is added to the system, insert the "Workout" string inside the caption automatically
                            if (post.caption.contains(event.text) && post.caption.contains("Workout")) {
                                results.add(post)
                            }
                        }
                    }

                    Categories.Keyword -> {
                        temporalPosts.forEach { post ->
                            if (post.caption.contains(event.text)) {
                                results.add(post)
                            }
                        }
                    }

                    Categories.Social -> {
                        temporalPosts.forEach { post ->
                            if (post.social.contains(event.text)) {
                                results.add(post)
                            }
                        }
                    }

                    else -> {
                        temporalPosts.forEach { post ->
                            if (post.username.contains(event.text) ||
                                post.caption.contains(event.text) && post.caption.contains("Workout") ||
                                post.caption.contains(event.text) ||
                                post.social.contains(event.text)
                            ) {
                                results.add(post)
                            }
                        }
                    }
                }

                _posts.value = results.toMutableList()
            }

            is SearchEvent.GymPostWorkoutEvent -> {
                val workoutName: String = event.workout.name // Assuming event.workout.name is the workout name

                val desc: String = buildString {
                    appendLine("Workout: $workoutName")
                    event.workout.exercises.forEachIndexed { index, exercise ->
                        appendLine("${index + 1}. ${exercise.name}")
                    }
                }
                var social:String="Instagram"
                if (event.social==R.drawable.instagram_icon){
                    social="Instagram"
                }
                else if (event.social==R.drawable.x_logo_icon){
                    social="X"

                }
                else if (event.social==R.drawable.facebook_icon){
                    social="Facebook"

                }
                else if (event.social==R.drawable.tiktok_logo_icon){
                    social="TikTok"

                }
                val user =event.username
                val post = GymPost(id ="1", userResourceId = R.drawable.pexels5, imageResourceId = R.drawable.logo, username = event.username, social = social, randomSocialId = R.drawable.instagram_icon, caption = desc, time = "10:13", likes = "123", comment = "234")
                _posts.value?.add(post)
            }

            is SearchEvent.ResetPostEvent -> {
                _posts.value = generateRandomGymPost(10).toMutableList()
            }
        }
    }
}