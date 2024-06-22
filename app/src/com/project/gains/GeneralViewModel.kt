package com.project.gains



import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.data.Exercise
import com.project.gains.data.GymPost
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.Plan
import com.project.gains.data.Plot
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.Session
import com.project.gains.data.TrainingMetricType

import com.project.gains.data.Workout
import com.project.gains.data.generateRandomGymPost
import com.project.gains.data.generateRandomPlan
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

    private val _selectedApp = MutableLiveData<Int>()
    val selectedApp: MutableLiveData<Int> = _selectedApp

    private val _selectedPlotPreview = MutableLiveData<ProgressChartPreview>()
    val selectedPlotPreview: MutableLiveData<ProgressChartPreview> = _selectedPlotPreview

    private val _selectedWorkout = MutableLiveData<Workout>()
    val selectedWorkout: MutableLiveData<Workout> = _selectedWorkout

    private val _selectedExercise = MutableLiveData<Exercise>()
    val selectedExercise: MutableLiveData<Exercise> = _selectedExercise

    private val _oldExercise = MutableLiveData<Exercise>()
    val oldExercise: MutableLiveData<Exercise> = _oldExercise

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

    // FOR EACH PLAN

    val _selectedSessionsPlan = HashMap<Int,HashMap<Int,MutableList<Session>>>()
    val _selectedMetricsMap = HashMap<Int,MutableList<TrainingMetricType>>()
    val _selectedPeriodsMap = HashMap<Int,MutableList<PeriodMetricType>>()


    val _selectedMusicsMap = HashMap<Int,Boolean>()

    val _selectedBackupsMap = HashMap<Int,Boolean>()


    private var int = 0






    init {
        Log.d("LOAD","FETCHING DATA FROM DB")
        _plans.value = generateSamplePlans()
        _workouts.value = generateSampleWorkouts()
        _exercises.value = generateSampleExercises()
        _plots.value = generateRandomPlots()
        _posts.value = generateRandomGymPost(10).toMutableList()
        _currentSessions.value = mutableListOf()
        _linkedApps.value = mutableListOf()

    }


    fun onSaveSessionEvent(event: SaveSessionEvent) {
        when (event) {
            is SaveSessionEvent.SaveSession -> {
                _currentSessions.value?.add(event.session)
                if (_selectedSessionsPlan.get(event.plan) != null) {
                    _selectedSessionsPlan.get(event.plan)?.get(event.workout)?.add(event.session)
                }
                else{
                    val hashMap: HashMap<Int,MutableList<Session>> = HashMap<Int,MutableList<Session>>()
                    hashMap.set(event.workout, mutableListOf())
                    _selectedSessionsPlan.set(event.plan,hashMap)
                }
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

    fun onShareContentEvent(event: ShareContentEvent) {
        when (event) {
            is ShareContentEvent.ShareSession -> {
                Log.d("SHARE","YOU HAVE SHARED THIS CONTENT: ${event.session}")
            }

            is ShareContentEvent.SharePlot -> {
                Log.d("SHARE","YOU HAVE SHARED THIS CONTENT: ${event.plot}")
            }

            is ShareContentEvent.ShareLink -> {
                Log.d("SHARE","YOU HAVE OPENED THIS CONTENT: ${event.post}")
            }

            is ShareContentEvent.SharePlan -> {
                Log.d("SHARE","YOU HAVE SHARED THIS CONTENT: ${event.plan}")
            }

            is ShareContentEvent.ShareWorkout -> {
                Log.d("SHARE","YOU HAVE SHARED THIS CONTENT: ${event.workout}")
            }

            is ShareContentEvent.ShareExercise -> {
                Log.d("SHARE","YOU HAVE SHARED THIS CONTENT: ${event.exercise}")
            }
        }
    }

    fun onCreateEvent(event: CreateEvent) {
        when (event) {
            is CreateEvent.CreatePlan -> {
                val num = when(event.selectedPeriod) {
                    PeriodMetricType.WEEK -> 4
                    PeriodMetricType.YEAR -> 192
                    PeriodMetricType.MONTH -> 16
                }
                val workouts = generateRandomPlan(event.selectedTrainingType,event.selectedExerciseType.toMutableList(),num,6)
                val options_selected = event.selectedOptions
                Log.d("PLAN","THESE ARE YOUR OPTIONS: $options_selected")
                generateSamplePlans()
                int = int + 1
                _selectedMetricsMap.set(int,event.selectedMetricType)
                _selectedBackupsMap.set(int,event.selectedBackup)
                _selectedMusicsMap.set(int,event.selectedMusic)
                val periods:MutableList<PeriodMetricType> = mutableListOf()

                if (PeriodMetricType.WEEK == event.selectedPeriod){
                    periods.add(PeriodMetricType.WEEK)
                }
                if (PeriodMetricType.MONTH == event.selectedPeriod){
                    periods.add(PeriodMetricType.WEEK)
                    periods.add(PeriodMetricType.MONTH)

                }
                if (PeriodMetricType.YEAR == event.selectedPeriod){
                    periods.add(PeriodMetricType.WEEK)
                    periods.add(PeriodMetricType.MONTH)
                    periods.add(PeriodMetricType.YEAR)

                }
                _selectedPeriodsMap.set(int,periods)
                val plan : Plan = Plan(int,"plan+$int", workouts = workouts.toMutableList(), period = event.selectedPeriod)
                _plans.value?.add(plan)
                _selectedPlan.value = plan

            }
            is CreateEvent.CreateWorkout -> {
                _workouts.value?.add(event.workout)
                _selectedWorkout.value = event.workout

            }
            is CreateEvent.CreateExercise -> {
                _exercises.value?.add(event.exercise)
                _selectedExercise.value = event.exercise

            }
        }
    }

    fun onDeleteEvent(event: DeleteEvent) {// delete exercise plan workout
        when (event) {
            is DeleteEvent.DeletePlan -> {
                _plans.value?.remove(event.plan)
            }

            is DeleteEvent.DeleteWorkout -> {
                _workouts.value?.remove(event.workout)
            }

            is DeleteEvent.DeleteExercise -> {
                _exercises.value?.remove(event.exercise)
            }

        }
    }

    fun onSelectEvent(event: SelectEvent) {// delete exercise plan workout
        when (event) {

            is SelectEvent.SelectPlotPreview -> {
                _selectedPlotPreview.value = event.preview
            }

            is SelectEvent.SelectLinkedApp -> {
                _selectedApp.value = event.app
            }

            is SelectEvent.SelectPlan -> {
                _selectedPlan.value = event.plan
            }

            is SelectEvent.SelectWorkout -> {
                _selectedWorkout.value = event.workout
            }

            is SelectEvent.SelectExercise -> {
                _selectedExercise.value = event.exercise
            }

        }
    }
}
