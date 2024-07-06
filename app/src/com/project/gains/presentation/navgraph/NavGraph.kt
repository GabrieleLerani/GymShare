package com.project.gains.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.presentation.exercises.ExerciseDetailsScreen
import com.project.gains.presentation.HomeScreen

import com.project.gains.presentation.authentication.AuthenticationViewModel
import com.project.gains.presentation.authentication.screens.SignInScreen
import com.project.gains.presentation.authentication.screens.SignUpScreen

import com.project.gains.presentation.onboarding.OnBoardingScreen
import com.project.gains.presentation.onboarding.OnBoardingViewModel

import com.project.gains.presentation.settings.SettingsScreen
import com.project.gains.presentation.settings.SettingsViewModel
import com.project.gains.presentation.settings.ShareContentViewModel
import com.project.gains.presentation.exercises.TypedExerciseScreen
import com.project.gains.presentation.workout.WorkoutModeScreen
import com.project.gains.presentation.explore.FeedScreen
import com.project.gains.presentation.explore.FeedViewModel
import com.project.gains.presentation.plan.AddGeneratedPlan
import com.project.gains.presentation.plan.AddManualWorkout
import com.project.gains.presentation.plan.NewPlanScreen
import com.project.gains.presentation.plan.PlanScreen
import com.project.gains.presentation.plan.PlanViewModel
import com.project.gains.presentation.progress.ProgressDetailsScreen
import com.project.gains.presentation.progress.ProgressScreen
import com.project.gains.presentation.settings.AccountScreen
import com.project.gains.presentation.settings.LinkedSocialSettingScreen
import com.project.gains.presentation.workout.WorkoutScreen
import com.project.gains.presentation.workout.WorkoutViewModel


@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    val authenticationViewModel : AuthenticationViewModel = init()
    val generalViewModel : GeneralViewModel = hiltViewModel()
    val workoutViewModel : WorkoutViewModel = hiltViewModel()
    val shareContentViewModel : ShareContentViewModel = hiltViewModel()
    val planViewModel : PlanViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = startDestination){
        // construct a nested nav graph
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = startDestination
        ) {
            composable(
                route = Route.SignUpScreen.route
            ) {
                // set screen as the node state
                SignUpScreen(signInHandler = authenticationViewModel::onSignUpEvent, viewModel = authenticationViewModel, navController = navController)
            }
            composable(
                route = Route.AccountScreen.route
            ) {
                // set screen as the node state
                val viewModel : SettingsViewModel = hiltViewModel()
                AccountScreen(
                    settingsHandler = viewModel::onUpdateEvent,
                    signOutHandler =viewModel::onSignOutEvent ,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable(
                route = Route.SettingsScreen.route
            ) {
                // set screen as the node state
                SettingsScreen(navController = navController)
            }
            composable(
                route = Route.LinkedSocialSettingScreen.route
            ) {
                // set screen as the node state
                LinkedSocialSettingScreen(
                    navController = navController,
                    linkHandler = shareContentViewModel::onLinkAppEvent,
                    saveLinkHandler = shareContentViewModel::onSaveSharingPreferencesEvent,
                    shareContentViewModel = shareContentViewModel
                )
            }
            composable(
                route = Route.WorkoutScreen.route
            ) {
                // set screen as the node state
                WorkoutScreen(
                    navController = navController,
                    selectHandler = workoutViewModel::onSelectEvent,
                    workoutViewModel = workoutViewModel,
                    shareContentViewModel = shareContentViewModel
                )
            }

            composable(
                route = Route.FeedScreen.route
            ) {
                val feedViewModel : FeedViewModel = hiltViewModel()
                // set screen as the node state
                FeedScreen(
                    navController = navController,
                    feedViewModel =feedViewModel
                )
            }
            composable(
                route = Route.PlanScreen.route
            ) {
                // set screen as the node state
                PlanScreen(navController = navController,
                    planViewModel = planViewModel,
                    workoutViewModel = workoutViewModel,
                    shareContentViewModel = shareContentViewModel
                )
            }

            composable(
                route = Route.NewPlanScreen.route
            ) {
                // set screen as the node state
                NewPlanScreen(navController = navController)
            }
            composable(
                route = Route.AddGeneratedPlan.route
            ) {
                // set screen as the node state
                AddGeneratedPlan(navController = navController)
            }
            composable(
                route = Route.AddManualWorkout.route
            ) {
                // set screen as the node state
                AddManualWorkout(navController = navController)
            }

            composable(
                route = Route.ProgressScreen.route
            ) {
                // set screen as the node state
                ProgressScreen(
                    navController =navController,
                    selectHandler = generalViewModel::onSelectEvent
                )
            }
            composable(
                route = Route.ProgressDetailsScreen.route
            ) {
                // set screen as the node state
                ProgressDetailsScreen(navController = navController, shareHandler = generalViewModel::onShareContentEvent, generalViewModel,shareContentViewModel = shareContentViewModel,generalViewModel::onSelectEvent)
            }
            composable(
                route = Route.ExerciseDetailsScreen.route
            ) {
                // set screen as the node state
                ExerciseDetailsScreen(navController = navController, shareContentViewModel = shareContentViewModel, selectHandler = generalViewModel::onSelectEvent)
            }
            composable(
                route = Route.WorkoutModeScreen.route
            ) {
                // set screen as the node state
                WorkoutModeScreen(
                    navController, workoutViewModel::onMusicEvent, workoutViewModel = workoutViewModel
                )
            }
            composable(
                route = Route.TypedExerciseScreen.route
            ) {
                // set screen as the node state
                TypedExerciseScreen(
                    navController = navController,
                    selectHandler = generalViewModel::onSelectEvent,
                    generalViewModel = generalViewModel
                )            }




            // more nodes...
        }
        // a node of the graph
        composable(
            route = Route.OnBoardingScreen.route
        ) {
            // set screen as the node state
            val viewModel : OnBoardingViewModel = hiltViewModel()
            OnBoardingScreen(
                event = viewModel::onBoardingEvent,navController
            )
        }
        composable(
            route = Route.SignInScreen.route
        ) {
            SignInScreen(signInHandler = authenticationViewModel::onSignInEvent, viewModel = authenticationViewModel,
                navController = navController)
        }
        composable(
            route = Route.HomeScreen.route
        ) {
            HomeScreen(
                navController = navController,
                workoutViewModel = workoutViewModel,
            )
        }
    }
}

@Composable
private fun init(): AuthenticationViewModel {
    return hiltViewModel()
}