package com.project.gains.presentation.components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.data.Categories
import com.project.gains.data.Socials
import com.project.gains.presentation.components.events.ManageCategoriesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {


    val categories = listOf(Categories.User, Categories.Workout, Categories.Keyword, Categories.Social)

    private var _selectedCategory: MutableLiveData<Categories> = MutableLiveData<Categories>()
    val selectedCategory: LiveData<Categories> = _selectedCategory


    fun onCategoriesEvent(event: ManageCategoriesEvent) {
        when(event) {
            is ManageCategoriesEvent.AssignCategoryEvent -> {
                _selectedCategory.value = event.category
            }
        }
    }
}