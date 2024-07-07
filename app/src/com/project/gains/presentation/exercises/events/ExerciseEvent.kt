package com.project.gains.presentation.exercises.events

import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.presentation.events.SelectEvent

/*
* Set of event used for manual workout generation, where user can click on plus and add
* new exercise from the list
* **/
sealed class ExerciseEvent {

    data class SelectExercise(val exercise: Exercise) : ExerciseEvent()
    data class SelectIsToAdd(val value: Boolean) : ExerciseEvent()
    data class SelectExerciseType(val exerciseType: ExerciseType) : ExerciseEvent()
    data class SelectExerciseToAdd(val exercise: Exercise) : ExerciseEvent()

    data class RemoveExerciseToAdd(val exercise: Exercise) : ExerciseEvent()
}