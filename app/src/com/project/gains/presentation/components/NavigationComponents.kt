package com.project.gains.presentation.components

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.gains.R
import com.project.gains.data.bottomNavItems
import com.project.gains.presentation.navgraph.Route

@Composable
fun BottomNavigationBar(navController: NavController) {

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        val currentRoute = currentRoute(navController)
        bottomNavItems.forEachIndexed { index, item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                alwaysShowLabel = true,
                onClick = {
                    if(item.badgeCount != null) {
                        item.badgeCount = null
                    }

                    selectedItemIndex = index
                    navController.navigate(item.route) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                } },
                label = {
                        Text(text = item.title)
                },
                icon = { BadgedBox(
                    badge = {
                        if(item.badgeCount != null) {
                            Badge {
                                Text(text = item.badgeCount.toString())
                            }
                        } else if(item.hasNews) {
                            Badge()
                        }
                    })
                    {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun WorkoutBottomBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(64.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                navController.navigate(Route.WorkoutModeScreen.route)
            },
            modifier = Modifier.size(50.dp),
            colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Start Icon",
                modifier = Modifier
                    .size(50.dp)
                    .padding(10.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(message: String, button: @Composable () -> Unit, button1: @Composable () -> Unit) {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = message,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )
        },
        navigationIcon = {button1()},
        actions = {button()},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        /*
        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.tertiary,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)*/
    )/* {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            button1()

            Text(
                text = message,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
            button()

        }

    }*/
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteTopBar(message: String, button: @Composable () -> Unit, button1: @Composable () -> Unit,button2: @Composable () -> Unit) {

    TopAppBar(
        title = {
            Text(
                text = message,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        },
        navigationIcon = {button1()},
        actions = {
            button2()
            button()
                  },
        /*
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),

        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.tertiary,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
        */
    )

    /*
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            button1()

            Text(
                text = message,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
            button2()
            button()

        }
    }*/
}





@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun DynamicTopBar(
    navController: NavController,
) {
    val currentRoute = currentRoute(navController = navController)
    val showNotification = remember { mutableStateOf(false) }
    val notificationMessage = remember { mutableStateOf("") }


    when (currentRoute) {

        Route.HomeScreen.route -> {
            TopBar(
                message = "Home",
                button = {
                    IconButton(
                        modifier = Modifier.size(45.dp),
                        onClick = { navController.navigate(Route.WorkoutModeScreen.route) }) {
                        Icon(
                            imageVector = Icons.Filled.FitnessCenter,
                            contentDescription = "Workout Mode",
                        )

                    }
                },

            ) {}
        }

        Route.SettingsScreen.route -> {
            TopBar(
                message = " General Settings",
                button= {
                    LogoUser(
                        modifier = Modifier.size(60.dp), R.drawable.pexels5
                    ) {
                        navController.navigate(Route.AccountScreen.route)
                    }
                },

            ) {}
        }

        Route.LinkedSocialSettingScreen.route -> {
            TopBar(
                message = "Sharing Preferences",
                button= {
                    LogoUser(
                        modifier = Modifier.size(60.dp), R.drawable.pexels5
                    ) { navController.navigate(Route.AccountScreen.route) }
                }
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.AccountScreen.route -> {
            TopBar(
                message = "Account Settings",
                button = {
                }
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.WorkoutScreen.route -> {
        }

        Route.WorkoutModeScreen.route -> {
        }

        Route.TypedExerciseScreen.route -> {
            TopBar(
                message = "Exercises",
                button = {}
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.HomeSearchScreen.route -> {
            TopBar(
                message = "Look for exercises",
                button = {}
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.FeedScreen.route -> {
            TopBar(
                message = " Explore Feed",
                button = {
                    LogoUser(
                        modifier = Modifier.size(60.dp), R.drawable.pexels5
                    ) { navController.navigate(Route.AccountScreen.route) }
                },
                button1 = {
                },
            )
        }

        Route.SearchScreen.route -> {
            TopBar(
                message = " Search on GymFeed",
                button = {},
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.PlanScreen.route -> {
            TopBar(
                message = "Your plan",
                button = {
                    IconButton(
                        modifier = Modifier.size(45.dp),
                        onClick = {
                            navController.navigate(Route.ShareScreen.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Share",
                            modifier = Modifier.graphicsLayer {
                                rotationZ = -45f // Rotate 45 degrees counterclockwise
                            }
                        )
                    }
                },
                button1 = {
                    IconButton(
                        modifier = Modifier.size(45.dp),
                        onClick = {
                            navController.navigate(Route.ProgressDetailsScreen.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.BarChart,
                            contentDescription = "Stats",
                        )
                    }
                }
            )
        }

        Route.NewPlanScreen.route -> {
            TopBar(
                message = "",
                button = {}
            ) {
                IconButton(onClick = {
                    navController.popBackStack()

                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        }

        Route.AddGeneratedPlanScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.AddManualWorkoutScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                BackButton {
                    navController.currentBackStackEntry
                    navController.popBackStack()

                }
            }
        }

        Route.LastNewPlanScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.ProgressDetailsScreen.route -> {
            TopBar(
                message = "Progress Details",
                button= {
                    IconButton(
                        modifier = Modifier.size(45.dp),
                        onClick = {
                            navController.navigate(Route.ShareScreen.route)
                        }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Share",
                            modifier = Modifier.graphicsLayer {
                                rotationZ = -45f // Rotate 45 degrees counterclockwise
                            }
                        )
                    }
                }
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.ShareScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        }


        Route.ExerciseDetailsScreen.route -> {
        }

        Route.ForgotPasswordScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                IconButton(onClick = {
                    navController.navigate(Route.SignInScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        }

        Route.OTPScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                IconButton(onClick = {
                    navController.navigate(Route.SignInScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        }
    }
    if (showNotification.value) {
        NotificationCard(
            message = notificationMessage.value,
            onClose = {showNotification.value=false}
        )

    }
}

@Composable
fun DynamicBottomBar(navController: NavController) {
    val currentRoute = currentRoute(navController = navController)

    // Show the bottom bar only for specific routes
    when (currentRoute) {
        Route.WorkoutScreen.route -> {
            WorkoutBottomBar(navController)
        }

        // Empty because new plan has no bottom bar
        Route.NewPlanScreen.route -> {}
        Route.AddManualWorkoutScreen.route -> {}
        Route.AddGeneratedPlanScreen.route -> {}
        Route.LastNewPlanScreen.route -> {}
        Route.WorkoutModeScreen.route -> {}
        Route.SearchScreen.route -> {}
        Route.ShareScreen.route -> {}
        Route.ExerciseDetailsScreen.route -> {}
        Route.TypedExerciseScreen.route -> {}
        Route.ProgressDetailsScreen.route -> {}
        Route.AccountScreen.route -> {}
        Route.LinkedSocialSettingScreen.route -> {}
        Route.SignInScreen.route -> {}
        Route.SignUpScreen.route -> {}
        Route.OnBoardingScreen.route -> {}
        Route.ForgotPasswordScreen.route -> {}
        Route.OTPScreen.route -> {}
        Route.ChangePasswordScreen.route -> {}
        Route.HomeSearchScreen.route -> {}

        else -> {
            BottomNavigationBar(navController = navController)
        }
    }
}

@Composable
fun getPreviousDestination(navController: NavController): String? {
    val previousBackStackEntry = navController.previousBackStackEntry

    return previousBackStackEntry?.destination?.route
}


@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(45.dp),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Share Icon",
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp),
        )
    }
}