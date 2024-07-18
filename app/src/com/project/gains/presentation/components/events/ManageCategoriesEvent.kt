package com.project.gains.presentation.components.events

import com.project.gains.data.Categories
import com.project.gains.data.ExerciseCategories

sealed class ManageCategoriesEvent {
    data class AssignCategoryEvent(val category: Categories) : ManageCategoriesEvent()

    data class AssignExerciseCategoryEvent(val category: ExerciseCategories) : ManageCategoriesEvent()
}