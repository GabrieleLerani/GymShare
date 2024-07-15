package com.project.gains.util

import com.project.gains.R
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

fun getResByName(appName : String): Int {
    var app :Int
    if (appName=="Instagram"){
        app = R.drawable.instagram_icon
    }
    else if (appName== "X"){
        app=R.drawable.x_logo_icon
    }
    else if (appName=="Facebook"){
        app=R.drawable.facebook_icon
    }
    else if (appName=="TikTok"){
        app=    R.drawable.tiktok_logo_icon
    }
    else if (appName=="Google Drive"){
        app=    R.drawable.drive_google_icon
    }
    else{
        app = R.drawable.instagram_icon
    }

    return app

}

fun getNameByRes(app : Int): String {
    var appName :String
    if (app==R.drawable.instagram_icon){
        appName = "Instagram"
    }
    else if (app== R.drawable.x_logo_icon){
        appName="X"
    }
    else if (app==R.drawable.facebook_icon){
        appName="Facebook"
    }
    else if (app==R.drawable.tiktok_logo_icon){
        appName= "TikTok"
    }
    else if (app==R.drawable.drive_google_icon){
        appName= "Google Drive"
    }
    else{
        appName = "Instagram"
    }

    return appName

}