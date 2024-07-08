package com.project.gains.presentation.navgraph

sealed class Route(
    val route: String
){
    data object OnBoardingScreen : Route(route = "onBoardingScreen")

    data object HomeScreen : Route(route = "homeScreen")

    data object SignInScreen : Route(route = "signInScreen")
    data object SignUpScreen : Route(route = "signUpScreen")

    data object SettingsScreen : Route(route = "settingsScreen")
    data object LinkedSocialSettingScreen : Route(route = "linkedSocialSettingScreen")
    data object AccountScreen : Route(route = "accountScreen")

    data object WorkoutScreen : Route(route = "workoutScreen")
    data object TypedExerciseScreen : Route(route = "typedExerciseScreen")
    data object WorkoutModeScreen : Route(route = "workoutModeScreen")

    data object FeedScreen : Route(route = "feedScreen")

    data object PlanScreen : Route(route = "planScreen")
    data object ProgressScreen : Route(route = "progressScreen")
    data object ProgressDetailsScreen : Route(route = "progressDetailsScreen")

    data object NewPlanScreen : Route(route = "newPlanScreen")
    data object AddGeneratedPlanScreen : Route(route = "addGeneratedPlan")
    data object AddManualWorkoutScreen : Route(route = "addManualWorkout")

    data object ExerciseDetailsScreen : Route(route = "exerciseDetailsScreen")
    data object LastNewPlanScreen : Route(route = "lastNewPlanScreen")


    data object AppStartNavigation : Route(route = "appStartNavigation")

}
