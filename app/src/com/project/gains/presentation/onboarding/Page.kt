package com.project.gains.presentation.onboarding

import androidx.annotation.DrawableRes
import com.project.gains.R


data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Welcome to GymShare!",
        description = "Reach the best version of yourself with our app, we are born to share",
        image = R.drawable.pexels8
    ),
    Page(
        title = "Plan Your workouts!",
        description = "Generate workout plans made for you without losing time and energy.",
        image = R.drawable.pexels3
    ),
    Page(
        title = "Share your progresses!",
        description = "Our app is made for compatibility with external apps link them in the settings.",
        image = R.drawable.pexels4
    ),/*
    Page(
        title = "Home Screen",
        description = "You can see the list of your favorite exercise and workouts",
        image = R.drawable.home_screen
    )
    ,
    Page(
        title = "Plan Screen",
        description = "You can manage your plan tracking also the progress done",
        image = R.drawable.plan_screen
    )
    ,
    Page(
        title = "New Screen",
        description = "You can add an automatically generated plam or create a personalized workout for a specific day",
        image = R.drawable.new_screen
    )
    ,
    Page(
        title = "Feed Screen",
        description = "You can scroll through the gym-related post coming from different social apps",
        image = R.drawable.feed_screen
    )
    ,
    Page(
        title = "Share Screen",
        description = "You can share a plan,a workout, an exercise or your progress",
        image = R.drawable.share_screen
    )*/

)
