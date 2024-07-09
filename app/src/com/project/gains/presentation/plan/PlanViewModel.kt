package com.project.gains.presentation.plan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.data.Frequency
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.Plan
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.TrainingType
import com.project.gains.data.generateRandomPlan
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

    val _selectedMetricsMap = HashMap<Int,MutableList<TrainingMetricType>>()

    val _selectedPeriodsMap = HashMap<Int,MutableList<PeriodMetricType>>()

    val _selectedLevelMap = HashMap<Int, Level>()
    val _selectedFrequencyMap = HashMap<Int, Frequency>()


    private val _selectedMusicsMap = HashMap<Int,Boolean>()

    private val _selectedBackupsMap = HashMap<Int,Boolean>()

    private val _selectedLvl = MutableLiveData<Level>()
    val selectedLvl : MutableLiveData<Level> = _selectedLvl
    private val _selectedPeriod = MutableLiveData<PeriodMetricType>()
    val selectedPeriod : MutableLiveData<PeriodMetricType> = _selectedPeriod
    private val _selectedTrainingType = MutableLiveData<TrainingType>()
    val selectedTrainingType : MutableLiveData<TrainingType> = _selectedTrainingType
    private val _selectedFrequency = MutableLiveData<Frequency>()
    val selectedFrequency : MutableLiveData<Frequency> = _selectedFrequency

    private val _showDialogPlan = MutableLiveData<Boolean>()
    val showDialogPlan: MutableLiveData<Boolean> = _showDialogPlan

    private var int = 0

    init {
        _plans.value = generateSamplePlans()
        _selectedPlan.value= generateSamplePlans().get(0)
        _selectedLvl.value = Level.BEGINNER
        _selectedPeriod.value=PeriodMetricType.WEEK
        _selectedTrainingType.value=TrainingType.STRENGTH
        _selectedFrequency.value=Frequency.THREE

    }

    fun onCreatePlanEvent(event: ManagePlanEvent) {
        when (event) {

            is ManagePlanEvent.CreatePlan -> {
                val num = when(_selectedPeriod.value) {
                    PeriodMetricType.WEEK -> 4
                    PeriodMetricType.YEAR -> 192
                    PeriodMetricType.MONTH -> 16
                    null -> TODO()
                }
                val workouts = generateRandomPlan(_selectedTrainingType.value ?: TrainingType.STRENGTH,event.selectedExerciseType,num)

                int += 1

                _selectedLevelMap[int] = _selectedLvl.value ?: Level.BEGINNER
                _selectedFrequencyMap[int]=_selectedFrequency.value?: Frequency.THREE
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

            is ManagePlanEvent.SetPlanOptions -> {
                _selectedPeriod.value = event.selectedPeriod
                _selectedFrequency.value=event.selectedFrequency
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