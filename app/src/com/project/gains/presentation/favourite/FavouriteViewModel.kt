package com.project.gains.presentation.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType

import com.project.gains.data.Song

import com.project.gains.data.Workout
import com.project.gains.data.generateRandomSongs
import com.project.gains.data.generateSampleExercises

import com.project.gains.data.generateSampleWorkouts
import com.project.gains.presentation.events.MusicEvent
import com.project.gains.presentation.favourite.events.FavouriteEvent
import com.project.gains.presentation.workout.events.ManageWorkoutEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor() : ViewModel(){


    private var _favouriteExercises = MutableLiveData<MutableList<Exercise>>()
    val favouriteExercises : MutableLiveData<MutableList<Exercise>> = _favouriteExercises
    private var _favouriteWorkouts= MutableLiveData<MutableList<Workout>>()
    val favouriteWorkouts : MutableLiveData<MutableList<Workout>> = _favouriteWorkouts

    init {
        favouriteWorkouts.value= mutableListOf()
        favouriteExercises.value= mutableListOf()
    }

    fun onSave(event: FavouriteEvent) {
        when(event){
            is FavouriteEvent.AddExercise ->{
                favouriteExercises.value?.add(event.exercise)

            }
            is FavouriteEvent.DeleteExercise ->{
                favouriteExercises.value?.remove(event.exercise)


            }
            is FavouriteEvent.AddWorkout ->{
                favouriteWorkouts.value?.add(event.workout)


            }
            is FavouriteEvent.DeleteWorkout ->{
                favouriteWorkouts.value?.remove(event.workout)

            }

        }
    }
}