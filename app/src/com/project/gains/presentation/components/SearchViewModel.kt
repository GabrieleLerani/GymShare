package com.project.gains.presentation.components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.data.Categories
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseCategories
import com.project.gains.data.Socials
import com.project.gains.presentation.components.events.ManageCategoriesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {


    val categories = listOf(Categories.User.toString(), Categories.Workout.toString(), Categories.Keyword.toString(), Categories.Social.toString())
    val exerciseCategories = listOf(
        ExerciseCategories.Glutes.toString(),
        ExerciseCategories.Abdominals.toString(),
        ExerciseCategories.Arms.toString(),
        ExerciseCategories.Chest.toString(),
        ExerciseCategories.Shoulders.toString(),
        ExerciseCategories.Back.toString()
    )


    private var _selectedCategory: MutableLiveData<Categories> = MutableLiveData<Categories>()
    val selectedCategory: LiveData<Categories> = _selectedCategory

    private var _selectedExerciseCategory: MutableLiveData<ExerciseCategories> = MutableLiveData<ExerciseCategories>()
    val selectedExerciseCategory: LiveData<ExerciseCategories> = _selectedExerciseCategory

    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _searchedExercises = MutableLiveData<List<Exercise>>(listOf())
    val searchedExercises: LiveData<List<Exercise>> = _searchedExercises

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setAllExercises(exercises: List<Exercise>) {
        _searchedExercises.value = exercises
    }
    fun updateSearchedExercises(exercises: List<Exercise>) {
        _searchedExercises.value = exercises
    }

    fun onCategoriesEvent(event: ManageCategoriesEvent) {
        when(event) {
            is ManageCategoriesEvent.AssignCategoryEvent -> {
                _selectedCategory.value = event.category
            }

            is ManageCategoriesEvent.AssignExerciseCategoryEvent -> {
                _selectedExerciseCategory.value = event.category
            }
        }
    }
}