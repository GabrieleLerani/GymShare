package com.project.gains.presentation.progress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.gains.data.ProgressChartPreview
import com.project.gains.presentation.progress.events.ProgressEvent
import javax.inject.Inject

class ProgressViewModel @Inject constructor() : ViewModel() {

    private val _selectedPlotPreview = MutableLiveData<ProgressChartPreview>()
    val selectedPlotPreview: MutableLiveData<ProgressChartPreview> = _selectedPlotPreview


    fun onSelectEvent(event: ProgressEvent) {// delete exercise plan workout
        when (event) {
            is ProgressEvent.SelectPlotPreview -> {
                _selectedPlotPreview.value = event.preview
            }
        }
    }
}