package com.project.gains.presentation.exercises.events

import com.project.gains.data.Exercise

/*
* Set of event used for manual workout generation, where user can click on plus and add
* new exercise from the list
* **/
sealed class ExerciseEvent {

    data class SelectExercise(val exercise: Exercise) : ExerciseEvent()
    data class SelectIsToAdd(val value: Boolean) : ExerciseEvent()
    data object  AddExercise : ExerciseEvent()
    data object DeleteExercise : ExerciseEvent()

}