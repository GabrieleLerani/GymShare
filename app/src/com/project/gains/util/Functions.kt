package com.project.gains.util

import java.time.DayOfWeek
import java.time.LocalDate

fun currentWeekday(): Int {
    val currentDate = LocalDate.now()
    val dayOfWeek = currentDate.dayOfWeek
    return getDayOfWeekNumber(dayOfWeek)
}

private fun getDayOfWeekNumber(dayOfWeek: DayOfWeek): Int {
    return dayOfWeek.value
}