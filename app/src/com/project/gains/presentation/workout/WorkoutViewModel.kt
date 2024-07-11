package com.project.gains.presentation.workout

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
import com.project.gains.presentation.plan.events.ManagePlanEvent
import com.project.gains.presentation.workout.events.ManageWorkoutEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor() : ViewModel(){

    private val _workouts = MutableLiveData<MutableList<Workout>>()
    val workouts: MutableLiveData<MutableList<Workout>> = _workouts

    private val _selectedWorkout = MutableLiveData<Workout>()
    val selectedWorkout: MutableLiveData<Workout> = _selectedWorkout

    private val _createdWorkouts = MutableLiveData<MutableList<Workout>>()
    val createdWorkouts: MutableLiveData<MutableList<Workout>> = _createdWorkouts


    private val _currentSong = MutableLiveData<Song>()
    val currentSong: MutableLiveData<Song> = _currentSong

    private val _songs = MutableLiveData<MutableList<Song>>()

    private val _exercises = MutableLiveData<MutableList<Exercise>>()
    val exercises: MutableLiveData<MutableList<Exercise>> = _exercises

    private var _favouriteWorkouts = MutableLiveData<MutableList<Workout>>()
    val favouriteWorkouts : MutableLiveData<MutableList<Workout>> = _favouriteWorkouts



    private var songIndex = 0

    init {
        _createdWorkouts.value = mutableListOf()
        _favouriteWorkouts.value= mutableListOf()
        _workouts.value = generateSampleWorkouts()
        _exercises.value = generateSampleExercises()
        _currentSong.value=Song("","","") // Dummy init
        _songs.value= generateRandomSongs(5)
        _selectedWorkout.value= generateSampleWorkouts()[0]
    }

    fun onMusicEvent(event: MusicEvent) {
        when (event) {
            is MusicEvent.Music -> {
                _songs.value = generateRandomSongs(10)
                _currentSong.value= _songs.value?.get(songIndex)
            }
            is MusicEvent.Forward -> {
                songIndex+=1
                _currentSong.value= _songs.value?.get(songIndex)
            }
            is MusicEvent.Rewind -> {
                songIndex-=1

                _currentSong.value= _songs.value?.get(songIndex)
            }
        }
    }

    fun onManageWorkoutEvent(event: ManageWorkoutEvent) {
        when (event) {
            is ManageWorkoutEvent.CreateWorkout -> {
                _workouts.value?.add(event.workout)
            }

            is ManageWorkoutEvent.DeleteWorkout -> {
                _workouts.value?.remove(event.workout)
            }

            is ManageWorkoutEvent.SelectWorkout -> {
                _selectedWorkout.value = event.workout
            }


            is ManageWorkoutEvent.AddWorkoutFavourite -> {
                selectedWorkout.value?.let {
                    if (!_favouriteWorkouts.value?.contains(it)!!) {

                        _favouriteWorkouts.value?.add(it)
                    }
                }

            }
            is ManageWorkoutEvent.DeleteWorkoutFavourite -> {
                    selectedWorkout.value?.let {
                        if (_favouriteWorkouts.value?.contains(it)==true) {

                            _favouriteWorkouts.value?.remove(it)
                        }
                    }

            }

        }
    }
}