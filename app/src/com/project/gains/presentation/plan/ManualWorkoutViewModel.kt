package com.project.gains.presentation.plan

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.data.Exercise
import com.project.gains.presentation.plan.events.ManageExercises
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManualWorkoutViewModel @Inject constructor() : ViewModel() {
    private var _selectedExercises = MutableLiveData<MutableList<Exercise>>()
    val selectedExercises : MutableLiveData<MutableList<Exercise>> = _selectedExercises
    private val _workoutTitle = MutableLiveData<TextFieldValue>()
    val workoutTitle: MutableLiveData<TextFieldValue> = _workoutTitle
    init {
        _workoutTitle.value=TextFieldValue()

        _selectedExercises.value = mutableListOf()
    }

    fun onManageExercisesEvent(event: ManageExercises) {
        when(event) {
            is ManageExercises.AddExercise -> {

                // add element only if it doesn't exist yet
                _selectedExercises.value?.let {
                  if (!it.contains(event.exercise)){
                      _selectedExercises.value?.add(event.exercise)
                  }
                }

            }
            is ManageExercises.DeleteExercise -> {
                _selectedExercises.value?.remove(event.exercise)
            }
            is ManageExercises.SelectWorkoutStored -> {
                _workoutTitle.value=event.name
            }

            is ManageExercises.AddAllExercises -> {

                event.list.forEach {
                    if (!_selectedExercises.value?.contains(it)!!){
                        _selectedExercises.value?.add(it)
                    }
                }

            }

            is ManageExercises.DeleteAllExercise -> {
                _selectedExercises.value = mutableListOf()
            }


        }
    }
}