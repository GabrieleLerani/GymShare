package com.project.gains.presentation.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.generateSampleExercises
import com.project.gains.presentation.events.PreviousPageEvent
import com.project.gains.presentation.exercises.events.ExerciseEvent
import javax.inject.Inject

class ExerciseViewModel @Inject constructor() : ViewModel(){



    private val _isToAdd = MutableLiveData<Boolean>()
    val isToAdd: MutableLiveData<Boolean> = _isToAdd

    private val _selectedExercise = MutableLiveData<Exercise>()
    val selectedExercise: MutableLiveData<Exercise> = _selectedExercise

    init {
        _selectedExercise.value= generateSampleExercises().get(0)
    }



    fun onExerciseEvent(event: ExerciseEvent){
        when (event) {
            is ExerciseEvent.SelectIsToAdd -> {
                _isToAdd.value = event.value
            }

            is ExerciseEvent.SelectExercise -> {
                _selectedExercise.value = event.exercise
            }


        }
    }

}