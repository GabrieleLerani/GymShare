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

    private val _searchWidgetState: MutableState<SearchWidgetState> = mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    val categories = listOf(Categories.User, Categories.Workout, Categories.Keyword, Categories.Social)

    private var _selectedCategory: MutableLiveData<Categories> = MutableLiveData<Categories>()
    val selectedCategory: LiveData<Categories> = _selectedCategory

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun onCategoriesEvent(event: ManageCategoriesEvent) {
        when(event) {
            is ManageCategoriesEvent.AssignCategoryEvent -> {
                _selectedCategory.value = event.category
            }
        }
    }
}