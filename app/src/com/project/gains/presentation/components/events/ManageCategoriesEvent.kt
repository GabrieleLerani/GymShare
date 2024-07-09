package com.project.gains.presentation.components.events

import com.project.gains.data.Categories

sealed class ManageCategoriesEvent {
    data class AssignCategoryEvent(val category: Categories) : ManageCategoriesEvent()
}