package com.project.gains.presentation.plan.events

import androidx.compose.ui.text.input.TextFieldValue
import com.project.gains.data.Exercise

sealed class ManageExercises {
    data class AddExercise(val exercise: Exercise) : ManageExercises()
    data class DeleteExercise(val exercise: Exercise) : ManageExercises()
    data object DeleteAllExercise : ManageExercises()

    data class SelectWorkoutStored(val name: TextFieldValue) :
        ManageExercises()
}