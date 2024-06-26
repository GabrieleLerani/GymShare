package com.project.gains.presentation.events

import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.Plan
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.TrainingData
import com.project.gains.data.Workout

sealed class SelectEvent {
    data class SelectExercise(val exercise: Exercise) : SelectEvent()
    data class SelectLinkedApp(val app: Int) : SelectEvent()
    data class SelectPlan(val plan : Plan) : SelectEvent()
    data class SelectPlotPreview(val preview: ProgressChartPreview) : SelectEvent()
    data class SelectWorkout(val workout : Workout) : SelectEvent()
    data class SelectExerciseType(val exerciseType: ExerciseType) : SelectEvent()
    data class SelectExerciseToAdd(val exercise: Exercise) : SelectEvent()
    data object SelectIsToAdd : SelectEvent()

    data class SelectClicked(val clicked: Boolean) :
        SelectEvent()
    data class SelectShowPopup3(val showPopup3: Boolean) :
        SelectEvent()
    data class SelectShowPopup4(val showPopup4: Boolean) :
        SelectEvent()

    data class SelectPreviewsPage(val name: String) :
        SelectEvent()

    data class SelectPlanPopup(val showPopup:Boolean) :
        SelectEvent()



}