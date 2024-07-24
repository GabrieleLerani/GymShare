package com.project.gains.presentation.explore.events

import android.net.Uri
import com.project.gains.data.Categories
import com.project.gains.data.Exercise
import com.project.gains.data.Workout

sealed class SearchEvent {
    data class SearchGymPostEvent(val text: String, val selectedCategory: Categories?) : SearchEvent()
    data class GymPostWorkoutEvent(val workout: Workout, val social: Int,val username:String) : SearchEvent()

    data class GeneralPostEvent(val content: String,val social: Int, val username:String) : SearchEvent()

    data object ResetPostEvent : SearchEvent()

    data class GymPostExerciseEvent(val exercise: Exercise, val social: Int,val username:String) : SearchEvent()

    data class GymPostProgressEvent( val social: Int,val username:String) : SearchEvent()
    data class WorkoutPostEvent(val social: Int,val username:String, val imageUri: Uri?, val content: String) : SearchEvent()

}