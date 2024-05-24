package com.project.gains



import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.data.Exercise
import com.project.gains.data.GymPost
import com.project.gains.data.Plan
import com.project.gains.data.Plot
import com.project.gains.data.Session

import com.project.gains.data.Workout
import com.project.gains.data.generateRandomPlots
import com.project.gains.data.generateRandomSongTitle
import com.project.gains.data.generateSampleExercises
import com.project.gains.data.generateSamplePlans
import com.project.gains.data.generateSampleWorkouts
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.DeleteEvent
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.MusicEvent

import com.project.gains.presentation.events.SaveSessionEvent
import com.project.gains.presentation.events.SaveSharingPreferencesEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.events.ShareContentEvent
import dagger.hilt.android.lifecycle.HiltViewModel


import javax.inject.Inject

@HiltViewModel
class GeneralViewModel @Inject constructor() : ViewModel(){

    private val _plans = MutableLiveData<MutableList<Plan>>()
    val plans: MutableLiveData<MutableList<Plan>> = _plans

    private val _workouts = MutableLiveData<MutableList<Workout>>()
    val workouts: MutableLiveData<MutableList<Workout>> = _workouts

    private val _exercises = MutableLiveData<MutableList<Exercise>>()
    val exercises: MutableLiveData<MutableList<Exercise>> = _exercises

    private val _plots = MutableLiveData<MutableList<Plot>>()
    val plots: MutableLiveData<MutableList<Plot>> = _plots

    private val _selectedPlan = MutableLiveData<Plan>()
    val selectedPlan: MutableLiveData<Plan> = _selectedPlan

    private val _selectedWorkout = MutableLiveData<Workout>()
    val selectedWorkout: MutableLiveData<Workout> = _selectedWorkout

    private val _selectedExercise = MutableLiveData<Exercise>()
    val selectedExercise: MutableLiveData<Exercise> = _selectedExercise

    private val _oldExercise = MutableLiveData<Exercise>()
    val oldExercise: MutableLiveData<Exercise> = _oldExercise

    private val _selectedApp = MutableLiveData<Int>() // when selecting an app in share screen
    val selectedApp: MutableLiveData<Int> = _selectedApp

    private val _linkedApps = MutableLiveData<MutableList<Int>>()
    val linkedApps: MutableLiveData<MutableList<Int>> = _linkedApps

    private val _posts = MutableLiveData<MutableList<GymPost>>()
    val posts: MutableLiveData<MutableList<GymPost>> = _posts

    private val _showMusic = MutableLiveData<Boolean>()
    val showMusic: MutableLiveData<Boolean> = _showMusic

    private val _currentSong = MutableLiveData<String>()
    val currentSong: MutableLiveData<String> = _currentSong

    private val _currentSessions = MutableLiveData<MutableList<Session>>()
    val currentSessions: MutableLiveData<MutableList<Session>> = _currentSessions






    init {
        Log.d("DEBUG","FETCHING DATA FROM DB")
        _plans.value = generateSamplePlans()
        _workouts.value = generateSampleWorkouts()
        _exercises.value = generateSampleExercises()
        _plots.value = generateRandomPlots()


    }


    fun onSaveSessionEvent(event: SaveSessionEvent) {
        when (event) {
            is SaveSessionEvent.SaveSession -> {
                _currentSessions.value?.add(event.session)
            }

        }
    }

    fun onMusicEvent(event: MusicEvent) {
        when (event) {
            is MusicEvent.Music -> {
                _currentSong.value = generateRandomSongTitle()
            }


        }
    }

    fun onSaveSharingPreferencesEvent(event: SaveSharingPreferencesEvent) {
        when (event) {
            is SaveSharingPreferencesEvent.SaveSharingPreferences -> {
                // TODO save session
            }


        }
    }



    fun onLinkAppEvent(event: LinkAppEvent) {
        when (event) {
            is LinkAppEvent.LinkApp -> {
                // TODO save session
            }


        }
    }

    fun onShareContentEvent(event: ShareContentEvent) {
        when (event) {
            is ShareContentEvent.ShareSession -> {
                // TODO save session
            }

            is ShareContentEvent.SharePlot -> {
                // TODO save session
            }

            is ShareContentEvent.ShareLink -> {
                // TODO save session
            }

            is ShareContentEvent.SharePlan -> {
                // TODO save session
            }

            is ShareContentEvent.ShareWorkout -> {
                // TODO save session
            }

            is ShareContentEvent.ShareExercise -> {
                // TODO save session
            }
        }
    }

    fun onCreateEvent(event: CreateEvent) {
        when (event) {
            is CreateEvent.CreatePlan -> {
                // TODO save session
            }

            is CreateEvent.CreateWorkout -> {
                // TODO save session
            }
            is CreateEvent.CreateExercise -> {
                // TODO save session
            }
        }
    }

    fun onDeleteEvent(event: DeleteEvent) {// delete exercise plan workout
        when (event) {
            is DeleteEvent.DeletePlan -> {
                // TODO save session
            }

            is DeleteEvent.DeleteWorkout -> {
                // TODO save session
            }

            is DeleteEvent.DeleteExercise -> {
                // TODO save session
            }

        }
    }

    fun onSelectEvent(event: SelectEvent) {// delete exercise plan workout
        when (event) {
            is SelectEvent.SelectPlot -> {
                // TODO save session
            }

            is SelectEvent.SelectPlotPreview -> {
                // TODO save session
            }

            is SelectEvent.SelectLinkedApp -> {
                // TODO save session
            }

            is SelectEvent.SelectPlan -> {
                // TODO save session
            }

            is SelectEvent.SelectWorkout -> {
                // TODO save session
            }

            is SelectEvent.SelectExercise -> {
                // TODO save session
            }

        }
    }








}
