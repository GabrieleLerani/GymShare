package com.project.gains.presentation.navgraph

sealed class Route(
    val route: String
){
    data object OnBoardingScreen : Route(route = "onBoardingScreen")

    data object HomeScreen : Route(route = "homeScreen")

    data object SignInScreen : Route(route = "signInScreen")
    data object SignUpScreen : Route(route = "signUpScreen")
    data object ForgotPasswordScreen : Route(route = "forgotPasswordScreen")
    data object OTPScreen : Route(route = "OTPScreen")
    data object ChangePasswordScreen : Route(route = "changePasswordScreen")

    data object SettingsScreen : Route(route = "settingsScreen")
    data object LinkedSocialSettingScreen : Route(route = "linkedSocialSettingScreen")
    data object AccountScreen : Route(route = "accountScreen")

    data object WorkoutScreen : Route(route = "workoutScreen")
    data object SearchExerciseScreen : Route(route = "typedExerciseScreen")
    data object WorkoutModeScreen : Route(route = "workoutModeScreen")

    data object WorkoutListScreen : Route(route = "workoutListScreen")

    data object FeedScreen : Route(route = "feedScreen")

    data object PlansProgressesScreen : Route(route = "plansProgressesScreen")

    data object NewPlanScreen : Route(route = "newPlanScreen")
    data object AddGeneratedPlanScreen : Route(route = "addGeneratedPlan")
    data object AddManualWorkoutScreen : Route(route = "addManualWorkout")

    data object ShareScreen : Route(route = "shareScreen")
    data object PostScreen : Route(route = "postScreen")

    data object ExerciseDetailsScreen : Route(route = "exerciseDetailsScreen")
    data object LastNewPlanScreen : Route(route = "lastNewPlanScreen")

    data object AppStartNavigation : Route(route = "appStartNavigation")
}
