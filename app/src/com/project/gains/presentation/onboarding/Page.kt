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
        title = "Welcome to our Gym Workout Planning App!",
        description = "Reach the best version of yourself.",
        image = R.drawable.pexels1
    ),
    Page(
        title = "Plan Your workouts!",
        description = "Generate workout plans made for you without losing time and energy.",
        image = R.drawable.pexels3
    ),
    Page(
        title = "Share your progresses",
        description = "Our app is made for compatibility with external apps link them in the settings.",
        image = R.drawable.pexels4
    ),
    Page(
        title = "Track your progress!",
        description = "Analyse your progresses through indicators and plots.",
        image = R.drawable.pexels5
    )
)
