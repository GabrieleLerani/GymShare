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
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.DeleteEvent
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.MusicEvent
import com.project.gains.presentation.events.SaveSharingPreferencesEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.workout.events.ManageWorkoutEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor() : ViewModel(){

    private val _workouts = MutableLiveData<MutableList<Workout>>()
    val workouts: MutableLiveData<MutableList<Workout>> = _workouts

    private val _selectedWorkout = MutableLiveData<Workout>()
    val selectedWorkout: MutableLiveData<Workout> = _selectedWorkout

    private val _linkedApps = MutableLiveData<MutableList<Int>>()
    val linkedApps: MutableLiveData<MutableList<Int>> = _linkedApps

    private val _showDialogShared = MutableLiveData<Boolean>()
    val showDialogShared: MutableLiveData<Boolean> = _showDialogShared

    private val _currentSong = MutableLiveData<Song>()
    val currentSong: MutableLiveData<Song> = _currentSong

    private val _songs = MutableLiveData<MutableList<Song>>()

    private val _exercises = MutableLiveData<MutableList<Exercise>>()
    val exercises: MutableLiveData<MutableList<Exercise>> = _exercises

    private var songIndex = 0


    init {
        _linkedApps.value = mutableListOf()
        _workouts.value = generateSampleWorkouts()
        _exercises.value = generateSampleExercises(ExerciseType.ARMS,R.drawable.arms)
        _currentSong.value=Song("","","") // Dummy init
        _songs.value= generateRandomSongs(5)
        _selectedWorkout.value= generateSampleWorkouts()[0]
    }

    fun onSaveSharingPreferencesEvent(event: SaveSharingPreferencesEvent) {
        when (event) {
            is SaveSharingPreferencesEvent.SaveSharingPreferences -> {
                _linkedApps.value = (_linkedApps.value?.plus(event.apps))?.toSet()?.toMutableList()
            }
        }
    }

    fun onLinkAppEvent(event: LinkAppEvent) {
        when (event) {
            is LinkAppEvent.LinkApp -> {
                _linkedApps.value?.add(event.app)
            }


        }
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

    fun onCreateEvent(event: CreateEvent) {
        when (event) {

            is CreateEvent.CreateWorkout -> {
                _workouts.value?.add(event.workout)
                _selectedWorkout.value = event.workout

            }

            is CreateEvent.CreateExercise -> {
                _exercises.value?.add(event.exercise)
                // TODO create exercise is on exercises details
                //_selectedExercise.value = event.exercise
            }
            is CreateEvent.CreatePlan -> TODO()
            is CreateEvent.SetPlanOptions -> TODO()
        }
    }

    fun onSelectEvent(event: SelectEvent) {// delete exercise plan workout
        when (event) {

            is SelectEvent.SelectShowDialogShared -> {
                _showDialogShared.value=event.value

            }

            is SelectEvent.RemoveExerciseToAdd -> TODO()
            is SelectEvent.SelectClicked -> TODO()
            is SelectEvent.SelectExercise -> TODO()
            is SelectEvent.SelectExerciseToAdd -> TODO()
            is SelectEvent.SelectExerciseType -> TODO()
            is SelectEvent.SelectIsToAdd -> TODO()
            is SelectEvent.SelectLinkedApp -> TODO()
            is SelectEvent.SelectPlan -> TODO()
            is SelectEvent.SelectPlanPopup -> TODO()
            is SelectEvent.SelectPlotPreview -> TODO()
            is SelectEvent.SelectPreviewsPage -> TODO()
            is SelectEvent.SelectShowDialogPlan -> TODO()
            is SelectEvent.SelectShowDialogWorkout -> TODO()
            is SelectEvent.SelectShowPopup3 -> TODO()
            is SelectEvent.SelectShowPopup4 -> TODO()
            is SelectEvent.SelectWorkout -> {
                _selectedWorkout.value = event.workout
            }
            is SelectEvent.SelectWorkoutStored -> TODO()
        }
    }

    fun onDeleteEvent(event: DeleteEvent) {// delete exercise plan workout
        when (event) {
            is DeleteEvent.DeletePlan -> TODO()

            is DeleteEvent.DeleteWorkout -> {
                _workouts.value?.remove(event.workout)
            }

            is DeleteEvent.DeleteExercise -> {
                _exercises.value?.remove(event.exercise)
            }

        }
    }

    fun onManageWorkoutEvent(event: ManageWorkoutEvent) {
        when (event) {

            is ManageWorkoutEvent.CreateWorkout -> {
                _workouts.value?.add(event.workout)
                _selectedWorkout.value = event.workout
            }

            is ManageWorkoutEvent.DeleteWorkout -> {
                _workouts.value?.remove(event.workout)
            }

            is ManageWorkoutEvent.SelectWorkout -> {
                _selectedWorkout.value = event.workout
            }
        }
    }

}