package com.project.gains.presentation.navgraph

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.project.gains.presentation.exercises.ExerciseDetailsScreen
import com.project.gains.presentation.HomeScreen
import com.project.gains.presentation.MainViewModel
import com.project.gains.presentation.authentication.AuthenticationViewModel
import com.project.gains.presentation.authentication.screens.ChangePasswordScreen
import com.project.gains.presentation.authentication.screens.ForgotPasswordScreen
import com.project.gains.presentation.authentication.screens.OTPScreen
import com.project.gains.presentation.authentication.screens.SignInScreen
import com.project.gains.presentation.authentication.screens.SignUpScreen
import com.project.gains.presentation.components.SearchViewModel
import com.project.gains.presentation.components.slideInToLeft
import com.project.gains.presentation.components.slideInToRight
import com.project.gains.presentation.components.slideOutToLeft
import com.project.gains.presentation.components.slideOutToRight
import com.project.gains.presentation.exercises.ExerciseViewModel

import com.project.gains.presentation.onboarding.OnBoardingScreen
import com.project.gains.presentation.onboarding.OnBoardingViewModel

import com.project.gains.presentation.settings.SettingsScreen
import com.project.gains.presentation.settings.SettingsViewModel
import com.project.gains.presentation.settings.ShareContentViewModel
import com.project.gains.presentation.exercises.SearchExercisesScreen
import com.project.gains.presentation.workout.WorkoutModeScreen
import com.project.gains.presentation.explore.FeedScreen
import com.project.gains.presentation.explore.FeedViewModel
import com.project.gains.presentation.plan.AddGeneratedPlan
import com.project.gains.presentation.plan.AddManualWorkout
import com.project.gains.presentation.plan.LastNewPlanScreen
import com.project.gains.presentation.plan.ManualWorkoutViewModel
import com.project.gains.presentation.plan.NewPlanScreen
import com.project.gains.presentation.plan.PlanScreen
import com.project.gains.presentation.plan.PlanViewModel
import com.project.gains.presentation.plan.TabScreen
import com.project.gains.presentation.settings.AccountScreen
import com.project.gains.presentation.settings.LinkedSocialSettingScreen
import com.project.gains.presentation.share.PostScreen
import com.project.gains.presentation.share.ShareScreen
import com.project.gains.presentation.share.WorkoutListScreen
import com.project.gains.presentation.workout.WorkoutScreen
import com.project.gains.presentation.workout.WorkoutViewModel

@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController,
    paddingValues: PaddingValues,
    completionMessage: MutableState<String>,
    mainViewModel: MainViewModel
) {

    val authenticationViewModel : AuthenticationViewModel = init()
    val shareContentViewModel : ShareContentViewModel = hiltViewModel()
    val planViewModel : PlanViewModel = hiltViewModel()
    val manualWorkoutViewModel: ManualWorkoutViewModel = hiltViewModel()
    val exerciseViewModel: ExerciseViewModel = hiltViewModel()
    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    val feedViewModel : FeedViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(paddingValues),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
        ){
        // construct a nested nav graph
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = startDestination
        ) {
            composable(
                route = Route.SignUpScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,

            ) {
                // set screen as the node state
                SignUpScreen(signInHandler = authenticationViewModel::onSignUpEvent, viewModel = authenticationViewModel, navController = navController)
            }
            composable(
                route = Route.AccountScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                val viewModel : SettingsViewModel = hiltViewModel()
                AccountScreen(
                    settingsHandler = viewModel::onUpdateEvent,
                    signOutHandler =viewModel::onSignOutEvent ,
                    viewModel = viewModel,
                    navController = navController,
                    completionMessage=completionMessage
                )
            }
            composable(
                route = Route.SettingsScreen.route,
                popEnterTransition = ::slideInToRight,
            ) {
                // set screen as the node state
                SettingsScreen(navController = navController)
            }
            composable(
                route = Route.LinkedSocialSettingScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                LinkedSocialSettingScreen(
                    navController = navController,
                    linkHandler = shareContentViewModel::onLinkAppEvent,
                    saveLinkHandler = shareContentViewModel::onSaveSharingPreferencesEvent,
                    shareContentViewModel = shareContentViewModel,
                    completionMessage=completionMessage
                )
            }
            composable(
                route = Route.WorkoutScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                WorkoutScreen(
                    navController = navController,
                    shareHandler = shareContentViewModel::onManageDialogEvent,
                    exerciseHandler = exerciseViewModel::onExerciseEvent,
                    workoutViewModel = workoutViewModel,
                    shareContentViewModel = shareContentViewModel,
                    addFavouriteWorkoutHandler = workoutViewModel::onManageWorkoutEvent,
                    removeFavouriteWorkoutHandler = workoutViewModel::onManageWorkoutEvent,
                    completionMessage=completionMessage
                )
            }
            composable(
                route = Route.WorkoutListScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                WorkoutListScreen(
                    navController = navController,
                    planViewModel = planViewModel
                )
            }

            composable(
                route = Route.FeedScreen.route,
                popEnterTransition = ::slideInToRight,

            ) {
                // set screen as the node state
                val searchViewModel : SearchViewModel = hiltViewModel()
                FeedScreen(
                    navController = navController,
                    feedViewModel =feedViewModel,
                    searchViewModel = searchViewModel,
                    searchGymPostHandler = feedViewModel::onSearchEvent,
                    assignCategoryHandler = searchViewModel::onCategoriesEvent,
                    resetGymPostHandler = feedViewModel::onSearchEvent

                )
            }
            composable(
                route = Route.PlansProgressesScreen.route,
                popEnterTransition = ::slideInToRight,
            ) {
                // set screen as the node state
                TabScreen(
                    planViewModel = planViewModel,
                    onPlanClicked = {
                        navController.navigate("planScreen/$it")
                    }
                )
            }
            composable(
                route = "planScreen/{planId}",
                arguments = listOf(navArgument("planId") { type = NavType.IntType })
            ) { backStackEntry ->
                val planId: Int = backStackEntry.arguments?.getInt("planId") ?: 1

                // set screen as the node state
                PlanScreen(
                    navController = navController,
                    planViewModel = planViewModel,
                    planId = planId,
                    selectPlanHandler = planViewModel::onCreatePlanEvent
                )
            }
            composable(
                route = Route.NewPlanScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight)
            {
                // set screen as the node state
                NewPlanScreen(
                    navController = navController
                )
            }
            composable(
                route = Route.LastNewPlanScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                LastNewPlanScreen(
                    navController = navController,
                    createPlanHandler = planViewModel::onCreatePlanEvent,
                    addWorkoutHandler = workoutViewModel::onManageWorkoutEvent,
                    planViewModel = planViewModel,
                    completionMessage = completionMessage)
            }
            composable(
                route = Route.AddGeneratedPlanScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                AddGeneratedPlan(navController = navController,
                    planOptionsHandler = planViewModel::onCreatePlanEvent
                )
            }
            composable(
                route = Route.AddManualWorkoutScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                AddManualWorkout(
                    navController = navController,
                    manualWorkoutViewModel = manualWorkoutViewModel,
                    addNameHandler =  manualWorkoutViewModel::onManageExercisesEvent,
                    selectExerciseHandler = exerciseViewModel::onExerciseEvent,
                    deleteExerciseHandler = manualWorkoutViewModel::onManageExercisesEvent,
                    deleteAllExerciseHandler = manualWorkoutViewModel::onManageExercisesEvent,
                    createWorkoutHandler = workoutViewModel::onManageWorkoutEvent
                )
            }
            composable(
                route = Route.ShareScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                ShareScreen(
                    navController =navController ,
                    shareContentViewModel = shareContentViewModel,
                    shareHandler = feedViewModel::onSearchEvent,
                    workoutViewModel = workoutViewModel,completionMessage=completionMessage,
                    mainViewModel = mainViewModel,
                    shareHandlerExercise =feedViewModel::onSearchEvent,
                    shareHandlerProgress = feedViewModel::onSearchEvent,
                    exerciseViewModel = exerciseViewModel
                )
            }
            composable(
                route = Route.PostScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                PostScreen(
                    navController = navController,
                    planViewModel = planViewModel,
                    onPost = {navController.navigate(Route.FeedScreen.route)},
                    onExit = {navController.popBackStack()},
                    generalPostHandler = feedViewModel::onSearchEvent,
                    workoutPostHandler = feedViewModel::onSearchEvent
                    )
            }
            composable(
                route = Route.ExerciseDetailsScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                ExerciseDetailsScreen(
                    navController=navController,
                    addFavouriteExerciseHandler = exerciseViewModel::onExerciseEvent,
                    exerciseViewModel = exerciseViewModel,
                    removeFavouriteExerciseHandler = exerciseViewModel::onExerciseEvent,completionMessage=completionMessage
                )
            }
            composable(
                route = Route.WorkoutModeScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                WorkoutModeScreen(
                    navController = navController,
                    musicHandler = workoutViewModel::onMusicEvent,
                    workoutViewModel = workoutViewModel,
                    videoDialogHandler = workoutViewModel::onVideoEvent,
                    selectExerciseHandler = exerciseViewModel::onExerciseEvent,
                    shareContentViewModel = shareContentViewModel
                )
            }
            composable(
                route = Route.SearchExerciseScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                val searchViewModel : SearchViewModel = hiltViewModel()
                // set screen as the node state
                SearchExercisesScreen(
                    navController = navController,
                    selectExerciseHandler = exerciseViewModel::onExerciseEvent,
                    workoutViewModel = workoutViewModel,
                    exerciseViewModel = exerciseViewModel,
                    addExerciseHandler = manualWorkoutViewModel::onManageExercisesEvent,
                    assignCategoryHandler = searchViewModel::onCategoriesEvent,
                    searchViewModel = searchViewModel
                )
            }

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
            route = Route.SignInScreen.route,
            enterTransition = ::slideInToRight,
            exitTransition = ::slideOutToLeft,
        ) {
            SignInScreen(signInHandler = authenticationViewModel::onSignInEvent, viewModel = authenticationViewModel,
                navController = navController)
        }
        composable(
            route = Route.ForgotPasswordScreen.route
        ) {
            ForgotPasswordScreen (
                onSendClicked = { navController.navigate(Route.OTPScreen.route) },
                generateOTPHandler = authenticationViewModel::onOTPEvent
            )
        }
        composable(
            route = Route.OTPScreen.route
        ) {
            OTPScreen(
                authenticationViewModel = authenticationViewModel,
                onVerifyClicked = { navController.navigate(Route.ChangePasswordScreen.route) },
                onBackClicked = { navController.navigate(Route.ForgotPasswordScreen.route) }
            )
        }
        composable(
            route = Route.ChangePasswordScreen.route
        ) {
            ChangePasswordScreen( onChangePassword =  {navController.navigate(Route.HomeScreen.route) },completionMessage=completionMessage)
        }
        composable(
            route = Route.HomeScreen.route,
            popEnterTransition = ::slideInToRight,
            //popExitTransition = ::slideOutToRight

        ) {
            HomeScreen(
                navController = navController,
                workoutViewModel = workoutViewModel,
                exerciseViewModel = exerciseViewModel,
                selectWorkoutHandler = workoutViewModel::onManageWorkoutEvent,
                selectExerciseHandler = exerciseViewModel::onExerciseEvent
            )
        }

    }
}

@Composable
private fun init(): AuthenticationViewModel {
    return hiltViewModel()
}