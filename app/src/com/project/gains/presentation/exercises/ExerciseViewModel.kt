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

    private val _addedExercises = MutableLiveData<MutableList<Exercise>>()
    val addedExercises: MutableLiveData<MutableList<Exercise>> = _addedExercises

    // TODO check where it should be placed
    private val _previousPage = MutableLiveData<String>()
    val previousPage: MutableLiveData<String> = _previousPage

    private val _isToAdd = MutableLiveData<Boolean>()
    val isToAdd: MutableLiveData<Boolean> = _isToAdd

    private val _selectedExercise = MutableLiveData<Exercise>()
    val selectedExercise: MutableLiveData<Exercise> = _selectedExercise

    init {
        _addedExercises.value= mutableListOf()
        _selectedExercise.value= generateSampleExercises(ExerciseType.ARMS, R.drawable.arms2).get(0)
    }

    fun onPreviousPageEvent(event: PreviousPageEvent){
        when (event) {
            is PreviousPageEvent.SelectPreviewsPage -> {
                _previousPage.value=event.name
            }
        }
    }


    fun onExerciseEvent(event: ExerciseEvent){
        when (event) {
            is ExerciseEvent.SelectIsToAdd -> {
                _isToAdd.value = event.value
            }

            is ExerciseEvent.RemoveExerciseToAdd -> {
                _addedExercises.value?.remove(event.exercise)
            }
            is ExerciseEvent.SelectExercise -> {
                _selectedExercise.value = event.exercise
            }
            is ExerciseEvent.SelectExerciseToAdd -> TODO()
            is ExerciseEvent.SelectExerciseType -> TODO()
        }
    }

}