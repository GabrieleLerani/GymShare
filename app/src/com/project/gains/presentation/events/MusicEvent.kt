package com.project.gains.presentation.events

import com.project.gains.data.Exercise
import com.project.gains.data.GymPost
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.Plan
import com.project.gains.data.Session
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.Workout



sealed class MusicEvent {
    data object Music : MusicEvent()

}