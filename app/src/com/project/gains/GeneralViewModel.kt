package com.project.gains

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Message
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.GymPost
import com.project.gains.data.Plot
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.Session
import com.project.gains.data.Song

import com.project.gains.data.Workout
import com.project.gains.data.generateRandomGymPost
import com.project.gains.data.generateRandomPlots
import com.project.gains.data.generateRandomSongs
import com.project.gains.data.generateSampleExercises
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
class GeneralViewModel @Inject constructor() : ViewModel() {

    private val _exercises = MutableLiveData<MutableList<Exercise>>()
    val exercises: MutableLiveData<MutableList<Exercise>> = _exercises

    private val _plots = MutableLiveData<MutableList<Plot>>()
    val plots: MutableLiveData<MutableList<Plot>> = _plots

    private val _songs = MutableLiveData<MutableList<Song>>()
    val songs: MutableLiveData<MutableList<Song>> = _songs

    private val _selectedApp = MutableLiveData<Int>()

    private val _selectedPlotPreview = MutableLiveData<ProgressChartPreview>()
    val selectedPlotPreview: MutableLiveData<ProgressChartPreview> = _selectedPlotPreview

    private val _selectedExercise = MutableLiveData<Exercise>()
    val selectedExercise: MutableLiveData<Exercise> = _selectedExercise

    private val _selectedExerciseType = MutableLiveData<ExerciseType>()
    val selectedExerciseType: MutableLiveData<ExerciseType> = _selectedExerciseType

    private val _linkedApps = MutableLiveData<MutableList<Int>>()
    val linkedApps: MutableLiveData<MutableList<Int>> = _linkedApps

    private val _showMusic = MutableLiveData<Boolean>()
    val showMusic: MutableLiveData<Boolean> = _showMusic

// PAGE POPUP HANDLING
private val _showDialogWorkout = MutableLiveData<Boolean>()
    val showDialogWorkout: MutableLiveData<Boolean> = _showDialogWorkout
    private val _showDialogShared = MutableLiveData<Boolean>()
    val showDialogShared: MutableLiveData<Boolean> = _showDialogShared
    private val _workoutTitle = MutableLiveData<TextFieldValue>()
    val workoutTitle: MutableLiveData<TextFieldValue> = _workoutTitle
    private val _isToAdd = MutableLiveData<Boolean>()
    val isToAdd: MutableLiveData<Boolean> = _isToAdd
    private val _previewsPage = MutableLiveData<String>()
    val previewsPage: MutableLiveData<String> = _previewsPage
    private val _clicked = MutableLiveData<Boolean>()
    val clicked: MutableLiveData<Boolean> = _clicked
    private val _showPopup = MutableLiveData<Boolean>()
    val showPopup: MutableLiveData<Boolean> = _showPopup
    private val _showPopup3 = MutableLiveData<Boolean>()
    val showPopup3: MutableLiveData<Boolean> = _showPopup3
    private val _showPopup4 = MutableLiveData<Boolean>()
    val showPopup4: MutableLiveData<Boolean> = _showPopup4
    private val _addedExercises = MutableLiveData<MutableList<Exercise>>()
    val addedExercises: MutableLiveData<MutableList<Exercise>> = _addedExercises
    private val _linkedSharingMedia = MutableLiveData<MutableList<ImageVector>>()
    val linkedSharingMedia: MutableLiveData<MutableList<ImageVector>> = _linkedSharingMedia

    private val _selectedSessionsPlan = HashMap<Int,HashMap<Int,MutableList<Session>>>()

    private val _currentSessions = MutableLiveData<MutableList<Session>>()
    val currentSessions: MutableLiveData<MutableList<Session>> = _currentSessions

    private val _currentSong = MutableLiveData<Song>()
    val currentSong: MutableLiveData<Song> = _currentSong

    private var songIndex = 0

    init {
        Log.d("LOAD","FETCHING DATA FROM DB")
        _exercises.value = generateSampleExercises(ExerciseType.ARMS,R.drawable.arms)
        _plots.value = generateRandomPlots()
        _linkedApps.value = mutableListOf()
        _currentSong.value=Song("","","")
        _currentSessions.value = mutableListOf()
        _addedExercises.value= mutableListOf()
        _workoutTitle.value=TextFieldValue()
        _songs.value= generateRandomSongs(5)
        _selectedExercise.value= generateSampleExercises(ExerciseType.ARMS,R.drawable.arms2).get(0)
        _linkedSharingMedia.value?.add(Icons.Default.Email)
        _linkedSharingMedia.value?.add(Icons.Default.Message)

    }

    fun onSaveSessionEvent(event: SaveSessionEvent) {
        when (event) {

            is SaveSessionEvent.SaveSession -> {
                _currentSessions.value?.add(event.session)
                if (_selectedSessionsPlan[event.plan] != null) {
                    _selectedSessionsPlan[event.plan]?.get(event.workout)?.add(event.session)
                }
                else{
                    val hashMap: HashMap<Int,MutableList<Session>> = HashMap()
                    hashMap[event.workout] = mutableListOf()
                    _selectedSessionsPlan[event.plan] = hashMap
                }
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
            /*
            is CreateEvent.CreatePlan -> {
                val num = when(_selectedPeriod.value) {
                    PeriodMetricType.WEEK -> 4
                    PeriodMetricType.YEAR -> 192
                    PeriodMetricType.MONTH -> 16
                    null -> TODO()
                }
                val workouts = generateRandomPlan(_selectedTrainingType.value ?: TrainingType.STRENGTH,event.selectedExerciseType,_selectedLvl.value?: Level.BEGINNER,num)

                int += 1

                _selectedLevelMap[int] = _selectedLvl.value ?: Level.BEGINNER
                _selectedMetricsMap[int] = event.selectedMetricType
                _selectedBackupsMap[int] = event.selectedBackup
                _selectedMusicsMap[int] = event.selectedMusic
                val periods:MutableList<PeriodMetricType> = mutableListOf()

                if (PeriodMetricType.WEEK == _selectedPeriod.value){
                    periods.add(PeriodMetricType.WEEK)
                }
                if (PeriodMetricType.MONTH == _selectedPeriod.value){
                    periods.add(PeriodMetricType.WEEK)
                    periods.add(PeriodMetricType.MONTH)

                }
                if (PeriodMetricType.YEAR == _selectedPeriod.value){
                    periods.add(PeriodMetricType.WEEK)
                    periods.add(PeriodMetricType.MONTH)
                    periods.add(PeriodMetricType.YEAR)

                }
                _selectedPeriodsMap[int] = periods
                val plan  = Plan(int,"plan+$int", workouts = workouts.toMutableList(), period = _selectedPeriod.value ?: PeriodMetricType.WEEK)
                _plans.value?.add(plan)
                _selectedPlan.value = plan

            }
             */
            /*
            is CreateEvent.CreateWorkout -> {
                _workouts.value?.add(event.workout)
                _selectedWorkout.value = event.workout

            }
             */
            /*
            is CreateEvent.SetPlanOptions -> {
                _selectedPeriod.value=event.selectedPeriod
                _selectedLvl.value=event.selectedLevel
                _selectedTrainingType.value=event.selectedTrainingType
            }
             */
            is CreateEvent.CreateExercise -> {
                _exercises.value?.add(event.exercise)
                _selectedExercise.value = event.exercise

            }
        }
    }

    fun onDeleteEvent(event: DeleteEvent) {// delete exercise plan workout
        when (event) {
/*
            is DeleteEvent.DeletePlan -> {
                _plans.value?.remove(event.plan)
            }

 */
/*
            is DeleteEvent.DeleteWorkout -> {
                _workouts.value?.remove(event.workout)
            }
*/
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

            is SelectEvent.SelectIsToAdd -> {

                    _isToAdd.value = event.value


            }

            is SelectEvent.SelectPlanPopup -> {
                    _showPopup.value = event.showPopup

            }

            is SelectEvent.SelectExerciseToAdd -> {
                _addedExercises.value?.add(event.exercise)
            }

            is SelectEvent.SelectExerciseType -> {
                _selectedExerciseType.value = event.exerciseType
            }
            is SelectEvent.SelectLinkedApp -> {
                _selectedApp.value = event.app
            }
/*
            is SelectEvent.SelectPlan -> {
                _selectedPlan.value = event.plan
            }
*/
            is SelectEvent.SelectWorkout -> {
                _selectedWorkout.value = event.workout
            }

            is SelectEvent.SelectExercise -> {
                _selectedExercise.value = event.exercise
            }

            is SelectEvent.SelectClicked -> {

                _clicked.value=event.clicked
            }
            is SelectEvent.SelectPreviewsPage -> {

                _previewsPage.value=event.name
            }
            is SelectEvent.SelectShowPopup3 -> {

                _showPopup3.value=event.showPopup3
            }
            is SelectEvent.SelectShowPopup4 -> {
                _showPopup4.value=event.showPopup4
            }

            is SelectEvent.RemoveExerciseToAdd -> {

                _addedExercises.value?.remove(event.exercise)
            }

            is SelectEvent.SelectWorkoutStored -> {
                _workoutTitle.value=event.name
            }

            is SelectEvent.SelectShowDialogShared -> {
                _showDialogShared.value=event.value

            }
/*
            is SelectEvent.SelectShowDialogPlan -> {
                _showDialogPlan.value=event.value
            }
 */
            is SelectEvent.SelectShowDialogWorkout -> {
                _showDialogWorkout.value=event.value

            }
        }
    }
}
