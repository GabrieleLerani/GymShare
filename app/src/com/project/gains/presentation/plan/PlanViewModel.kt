package com.project.gains.presentation.plan


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.data.Frequency
import com.project.gains.data.Level
import com.project.gains.data.Plan
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout
import com.project.gains.data.generateSamplePlans
import com.project.gains.presentation.plan.events.ManagePlanEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor() : ViewModel() {

    private val _plans = MutableLiveData<MutableList<Plan>>()
    val plans: MutableLiveData<MutableList<Plan>> = _plans

    private val _selectedPlan = MutableLiveData<Plan>()
    val selectedPlan: MutableLiveData<Plan> = _selectedPlan

    private val _selectedMusicsMap = HashMap<Int, Boolean>()

    private val _selectedBackupsMap = HashMap<Int, Boolean>()

    private val _selectedLvl = MutableLiveData<Level>()
    private val _selectedTrainingType = MutableLiveData<TrainingType>()
    val selectedTrainingType: MutableLiveData<TrainingType> = _selectedTrainingType
    private val _selectedFrequency = MutableLiveData<Frequency>()
    val selectedFrequency: MutableLiveData<Frequency> = _selectedFrequency

    private var int = 0

    private val _selectedWorkouts = MutableLiveData<MutableList<Workout>>()
    val selectedWorkouts: MutableLiveData<MutableList<Workout>> = _selectedWorkouts

    init {
        _plans.value = generateSamplePlans()
        _selectedWorkouts.value = mutableListOf()
        _selectedPlan.value = generateSamplePlans()[0]
        _selectedLvl.value = Level.BEGINNER
        _selectedTrainingType.value = TrainingType.STRENGTH
        _selectedFrequency.value = Frequency.THREE
    }

    // TODO check if required
    fun updateSelectedWorkouts(workouts: List<Workout>){
        _selectedWorkouts.value?.addAll(workouts)
    }

    fun deleteSelectedWorkout(workout: Workout){
        //Log.d("DEBUG", _selectedWorkouts.value?.remove(workout).toString())
        _selectedWorkouts.value = _selectedWorkouts.value?.filter { it != workout } as MutableList<Workout>?
    }


    fun onCreatePlanEvent(event: ManagePlanEvent) {
        when (event) {

            is ManagePlanEvent.CreatePlan -> {

                int += 1

                _selectedBackupsMap[int] = event.selectedBackup
                _selectedMusicsMap[int] = event.selectedMusic

                val plan = Plan(
                    id = int,
                    name = "Plan $int",
                    workouts = event.workouts.toMutableList(),
                    frequency = _selectedFrequency.value ?: Frequency.THREE,
                    level = _selectedLvl.value ?: Level.BEGINNER,
                    training = _selectedTrainingType.value ?: TrainingType.STRENGTH,
                )
                _plans.value?.add(plan)
                _selectedPlan.value = plan
            }

            is ManagePlanEvent.SetPlanOptions -> {
                _selectedFrequency.value = event.selectedFrequency
                _selectedLvl.value = event.selectedLevel
                _selectedTrainingType.value = event.selectedTrainingType
            }

            is ManagePlanEvent.SelectPlan -> {
                _selectedPlan.value = event.plan
            }

            is ManagePlanEvent.DeletePlan -> {
                _plans.value?.remove(event.plan)
            }
        }
    }
}