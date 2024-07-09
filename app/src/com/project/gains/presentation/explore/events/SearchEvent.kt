package com.project.gains.presentation.explore.events

import com.project.gains.data.Categories

sealed class SearchEvent {
    data class SearchGymPostEvent(val text: String, val selectedCategory: Categories?) : SearchEvent()
}