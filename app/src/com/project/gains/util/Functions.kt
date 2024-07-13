package com.project.gains.util

import com.project.gains.data.Weekdays
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Locale

fun currentWeekday(): Int {
    val currentDate = LocalDate.now()
    val dayOfWeek = currentDate.dayOfWeek
    return getDayOfWeekNumber(dayOfWeek)
}

private fun getDayOfWeekNumber(dayOfWeek: DayOfWeek): Int {
    return dayOfWeek.value
}

fun Weekdays.toFormattedString(): String {
    return name.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

fun toLowerCaseString(text : String): String {
    return text.lowercase(Locale.ROOT)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}