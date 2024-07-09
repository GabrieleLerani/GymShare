package com.project.gains.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.gains.R
import com.project.gains.data.bottomNavItems
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.workout.events.ManageWorkoutEvent

@Composable
fun BottomNavigationBar(navController: NavController) {

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.height(64.dp) // Adjust the height of the BottomNavigation
    ) {
        val currentRoute = currentRoute(navController)
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute == item.route
            val iconColor = if (isSelected) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.onSurface
            val iconSize = 32.dp
            val textColor = if (isSelected) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.onSurface

            BottomNavigationItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(6.dp)) // Add spacing between icon and text
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(iconSize), // Adjust the size of the icon
                            tint = iconColor
                        )
                        //Spacer(modifier = Modifier.height(6.dp)) // Add spacing between icon and text
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp, // Set the desired text size here
                        maxLines = 1, // Limit to one line to prevent overflow
                        color = textColor
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = true // This will hide labels when not selected
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

@Composable
fun TopBar(message: String, button: @Composable () -> Unit, button1: @Composable () -> Unit) {

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
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
            button()

        }
    }
}
@Composable
fun FavoriteTopBar(message: String, button: @Composable () -> Unit, button1: @Composable () -> Unit,button2: @Composable () -> Unit) {

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
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
            button2()
            button()

        }
    }
}

@Composable
fun SearchTopBar(message: String, button: @Composable () -> Unit, button1: @Composable () -> Unit) {

    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {

    }
}



@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun DynamicTopBar(navController: NavController,addFavouriteExerciseHandler: (ExerciseEvent.AddExercise) -> Unit,addFavouriteWorkoutHandler:(ManageWorkoutEvent.AddWorkoutFavourite)->Unit) {
    val currentRoute = currentRoute(navController = navController)

    when (currentRoute) {

        Route.HomeScreen.route -> {
            TopBar(
                message = "Home",
                button = {
                    IconButton(
                        modifier = Modifier.size(45.dp),
                        onClick = { navController.navigate(Route.WorkoutModeScreen.route) }) {
                        Icon(
                            imageVector = Icons.Default.FitnessCenter,
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
                    ) { navController.navigate(Route.AccountScreen.route) }
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
                    // TODO
                    //showDialog.value = false
                    navController.popBackStack()
                }
            }
        }
        Route.WorkoutScreen.route -> {
            FavoriteTopBar(
                message = "Workout",
                button2 = {
                    IconButton(
                        modifier = Modifier.size(45.dp),
                        onClick = {
                            addFavouriteWorkoutHandler(ManageWorkoutEvent.AddWorkoutFavourite)
                        }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                        )
                    }},
                button = {
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
                }, button1 = {
                    BackButton {
                        navController.popBackStack()
                    }
                })
        }
        Route.WorkoutModeScreen.route -> {
            TopBar(
                message = "Workout Mode",
                button = { }
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close Icon",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
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

        Route.FeedScreen.route -> {
            TopBar(
                message = " Explore Feed",
                button= {
                    LogoUser(
                        modifier = Modifier.size(60.dp), R.drawable.pexels5
                    ) { navController.navigate(Route.AccountScreen.route) }
                },
                button1 = {
                    IconButton(onClick = { addFavouriteExerciseHandler(ExerciseEvent.AddExercise) }) {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite Icon",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                },
            )
        }

        Route.SearchScreen.route -> {
            TopBar(
                message = " Search on GymFeed",
                button= {},
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.PlanScreen.route -> {
            TopBar(
                message = "Your plan", //selectedPlan?.name ?: "Your Plan",
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
                            imageVector = Icons.Default.BarChart,
                            contentDescription = "Stats",
                            tint = MaterialTheme.colorScheme.surface
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
                    navController.navigate(Route.HomeScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
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


        Route.ExerciseDetailsScreen.route -> {
            FavoriteTopBar(
                message = "Exercise",
                button2 = {
                    IconButton(
                        modifier = Modifier.size(45.dp),
                        onClick = {
                            addFavouriteExerciseHandler(ExerciseEvent.AddExercise)
                        }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                        )
                }},
                button = {
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
                    }, button1 = {
                    BackButton {
                        navController.popBackStack()
                    }
                })
        }

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

        Route.NewPlanScreen.route, Route.AddManualWorkoutScreen.route, Route.AddGeneratedPlanScreen.route, Route.WorkoutModeScreen.route,Route.ShareScreen.route,  -> {
            // Empty because new plan has no bottom bar
        }

        else -> {
            BottomNavigationBar(navController = navController)
        }
    }
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