package com.project.gains.data
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.project.gains.R
import com.project.gains.presentation.navgraph.Route


// structures


enum class TrainingType {
    STRENGTH,
    CALISTHENICS,
    CROSSFIT
}

enum class Level {
    BEGINNER,
    INTERMEDIATE,
    EXPERT
}

enum class ExerciseType {
    CHEST,
    BACK,
    LEGS,
    ARMS,
    SHOULDERS,
    CORE
}

enum class TrainingMetricType {
    DURATION, BPM, KCAL, INTENSITY, DISTANCE,
}
enum class PeriodMetricType {
    WEEK,MONTH, YEAR
}

enum class Weekdays {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

data class Exercise(
    val name: String,
    val gifResId: Int?,  // Nullable Integer for resource ID
    val description: String,
    val type: ExerciseType,
    val training: TrainingType,
    val sets: Int,
    val totalTime: Int
)

data class Plan(
    val id: Int,
    val name: String,
    val period: PeriodMetricType,
    val workouts: MutableList<Workout>
)

data class Song(
    val singer: String,
    val album: String,
    val title: String,
)

data class Workout(
    val id: Int,
    val name: String,
    val workoutDay: Weekdays,
    val exercises: MutableList<Exercise>
)

data class Session(
    val kcal: Int,
    val bpm: Int,  // Nullable Integer for resource ID
    val restTime: Int,
    val duration: Int,
    val distance: Int,
    val intensity: Int,
)

data class UserProfileBundle(
    var displayName: String,
    var email: String
)

data class GymPost(
    val id: String,
    val userResourceId: Int,
    val imageResourceId: Int, // Resource ID of the image
    val username: String,
    val randomSocialId: Int,
    val caption: String,
    val time:String,
    val likes : String,
    val comment:String
    // Add other necessary properties
)

data class Plot(val preview: ProgressChartPreview, val data: List<TrainingData>)
// Data class to hold chart preview information
data class ProgressChartPreview(val name: String, @DrawableRes val imageResId: Int)

// Data class to hold chart data
data class TrainingData(val type: TrainingMetricType, var value: Int)


data class Option(val name: String, var isChecked: Boolean = false)


sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    data object Home : BottomNavItem(Route.HomeScreen.route, Icons.Default.Home, "Home")
    data object Add : BottomNavItem(Route.NewPlanScreen.route, Icons.Default.AddCircleOutline, "Add")
    data object Plan : BottomNavItem(Route.PlanScreen.route, Icons.Default.Event, "Plan")
    data object Settings : BottomNavItem(Route.SettingsScreen.route, Icons.Default.Settings, "Settings")
    data object Explore : BottomNavItem(Route.FeedScreen.route, Icons.Default.Explore, "Explore")
}


// init data and functions

fun generateSampleExercises(exerciseType: ExerciseType,res:Int): MutableList<Exercise> {
    return mutableListOf(
        Exercise(
            "Exercise 1", gifResId = res, description = "Exercise: Dumbbell Bicep Curl\n" +
                    "Execution:\n" +
                    "1. Stand upright with a dumbbell in each hand, arms fully extended by your sides, palms facing forward.\n" +
                    "2. Keep your elbows close to your torso and engage your core for stability.\n" +
                    "3. Begin the movement by flexing your elbows, curling the dumbbells towards your shoulders while keeping your upper arms stationary.\n" +
                    "4. Continue to curl until the dumbbells are at shoulder level, and your biceps are fully contracted.\n" +
                    "5. Hold the contracted position for a moment, then slowly lower the dumbbells back to the starting position with control.\n" +
                    "6. Repeat for the desired number of repetitions.\n" +
                    "\n" +
                    "Tips:\n" +
                    "- Use a controlled motion throughout the exercise, avoiding swinging or momentum.\n" +
                    "- Keep your wrists straight and neutral to maximize bicep activation and reduce strain on the wrists.\n" +
                    "- Focus on squeezing the biceps at the top of the movement to maximize muscle engagement.\n" +
                    "- Exhale as you lift the weights and inhale as you lower them, maintaining steady breathing.\n" +
                    "- Choose an appropriate weight that allows you to perform the exercise with proper form and control.\n",
            type = exerciseType, training = TrainingType.STRENGTH, 4, 90
        ),
        Exercise(
            "Exercise 2", gifResId = res, description = "Exercise 2",
            type = exerciseType, training = TrainingType.STRENGTH, 4, 90
        ),
        Exercise(
            "Exercise 3", gifResId = res, description = "Exercise 3",
            type = exerciseType, training = TrainingType.STRENGTH, 4, 90
        ),
        Exercise(
            "Exercise 4", gifResId = res, description = "Exercise 4",
            type = exerciseType, training = TrainingType.STRENGTH, 4, 90
        ),
        Exercise(
            "Exercise 5", gifResId = res, description = "Exercise 5",
            type = exerciseType, training = TrainingType.STRENGTH, 4, 90
        ),
        Exercise(
            "Exercise 6", gifResId = res, description = "Exercise 6",
            type = exerciseType, training = TrainingType.STRENGTH, 4, 90
        ),
        Exercise(
            "Exercise 7", gifResId = res, description = "Exercise 7",
            type = exerciseType, training = TrainingType.STRENGTH, 4, 90
        ),

        )
}

fun generateSampleWorkouts(): MutableList<Workout> {
    return mutableListOf(
        Workout(
            id = 1,
            name = "Workout 1",
            workoutDay = Weekdays.WEDNESDAY,
            exercises = mutableListOf(
                Exercise(
                    "Exercise 1", gifResId = R.drawable.arms, description = "Exercise 1",
                    type = ExerciseType.ARMS, training = TrainingType.STRENGTH, 4, 90
                ),
                Exercise(
                    "Exercise 2", gifResId = R.drawable.arms2, description = "Exercise 2",
                    type = ExerciseType.ARMS, training = TrainingType.STRENGTH, 4, 90
                ),
                Exercise(
                    "Exercise 3", gifResId = R.drawable.arms3, description = "Exercise 3",
                    type = ExerciseType.ARMS, training = TrainingType.STRENGTH, 4, 90
                ),
            )
        ),
        Workout(
            id = 1,
            name = "Workout 2",
            workoutDay = Weekdays.FRIDAY,
            exercises = mutableListOf(
                Exercise(
                    "Exercise 1", gifResId = R.drawable.legs, description = "Exercise 1",
                    type = ExerciseType.LEGS, training = TrainingType.STRENGTH, 4, 90
                ),
                Exercise(
                    "Exercise 2", gifResId = R.drawable.legs2, description = "Exercise 2",
                    type = ExerciseType.LEGS, training = TrainingType.STRENGTH, 4, 90
                ),
                Exercise(
                    "Exercise 3", gifResId = R.drawable.legs3, description = "Exercise 3",
                    type =ExerciseType.LEGS, training = TrainingType.STRENGTH, 4, 90
                ),
            )
        ),
        // Define more workouts as needed
    )
}
fun generateOptions(): List<Option> {
    return listOf(
        Option("MUSIC"),
        Option("BACKUP"),
        Option(TrainingMetricType.BPM.toString()),
        Option(TrainingMetricType.KCAL.toString()),
        Option(TrainingMetricType.DURATION.toString()),
        Option(ExerciseType.CHEST.toString()),
        Option(ExerciseType.BACK.toString()),
        Option(ExerciseType.SHOULDERS.toString()),
        Option(ExerciseType.ARMS.toString()),
        Option(ExerciseType.LEGS.toString()),
        Option(ExerciseType.CORE.toString()),

        )
}

fun generateSamplePlans(): MutableList<Plan> {
    return mutableListOf(
        Plan(
            id = 1,
            name = "Plan 1",
            period = PeriodMetricType.WEEK,
            workouts = generateSampleWorkouts()
        ),
        Plan(
            id = 2,
            name = "Plan 2",
            period = PeriodMetricType.MONTH,
            workouts = generateSampleWorkouts()
        )
        // Add more sample plans as needed
    )
}

fun generateRandomGymPost(count: Int): List<GymPost> {
    val posts = mutableListOf<GymPost>()
    val usernames = listOf("user1", "user2", "user3", "user4", "user5")
    val imageResourceIds = listOf(
        R.drawable.pexels1,
        R.drawable.pexels4,
        R.drawable.pexels3,
        R.drawable.pexels2

    )
    val socialResourceIconsIds = listOf(
        R.drawable.facebook_icon,
        R.drawable.tiktok_logo_icon,
        R.drawable.instagram_icon,
        R.drawable.x_logo_icon
    )

    val userResourceIconsIds = listOf(
        R.drawable.pexels1,
        R.drawable.pexels2,
        R.drawable.pexels3,
        R.drawable.pexels4,
        R.drawable.pexels5
    )


    repeat(count) {
        val randomUsername = usernames.random()
        val randomUserImageResourceId = userResourceIconsIds.random()
        val randomImageResourceId = imageResourceIds.random()
        val randomSocialId = socialResourceIconsIds.random()
        val postId = "post_$it"
        val post = GymPost(postId, randomUserImageResourceId,randomImageResourceId, randomUsername, randomSocialId, "Good morning Fit-Family. Happy Sunday to you and your families. " +
                "I hope you have the best day and the most amazing week ahead. " +
                "I love you and stay blessed 🏋🏽‍♂️🤸🏽‍♀️🏋🏽‍♀️🤸🏽‍♂️, now let’s get MOVING 😁😁😃😄🙂😊😌🤗👍🏽👏🏽👊🏽✊🏽✌🏽🙏🏽","Today 13:13","125 Likes","25 Comments")
        posts.add(post)
    }

    return posts
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Explore,
    BottomNavItem.Add,
    BottomNavItem.Plan,
    BottomNavItem.Settings,
)

fun generateRandomSongs(count: Int): MutableList<Song> {
    val singers = listOf("Singer A", "Singer B", "Singer C", "Singer D")
    val albums = listOf("Album X", "Album Y", "Album Z", "Album W")
    val titles = listOf("Title 1", "Title 2", "Title 3", "Title 4")

    val songs = mutableListOf<Song>()

    repeat(count) {
        val randomSinger = singers.random()
        val randomAlbum = albums.random()
        val randomTitle = titles.random()
        songs.add(Song(randomSinger, randomAlbum, randomTitle))
    }

    return songs
}

fun generateRandomTrainingData(months: Int): List<TrainingData> {
    val metrics = listOf(TrainingMetricType.DURATION, TrainingMetricType.INTENSITY, TrainingMetricType.DISTANCE)
    return List(months) {
        TrainingData(
            type = metrics.random(),
            value = (1..10).random()
        )
    }
}

// Function to generate a random Plot object
fun generateRandomPlot(): Plot {
    val names = mutableListOf(PeriodMetricType.WEEK.toString(),PeriodMetricType.MONTH.toString(),PeriodMetricType.YEAR.toString())
    // List of drawable resource IDs
    val drawableResources = listOf(
        R.drawable.plot2,
        R.drawable.plot3,
    )

    val preview = ProgressChartPreview(
        name = names.random(),
        imageResId = drawableResources.random()  // Assuming drawable resources are in the range 1 to 100
    )

    val trainingData = generateRandomTrainingData(4)

    return Plot(preview, trainingData)
}

// Function to generate a list of random Plot objects
fun generateRandomPlots(): MutableList<Plot> {
    return MutableList(2) { generateRandomPlot() }
}




// Define exercise maps and types
val calisthenicsExercises = mapOf(
    ExerciseType.ARMS to listOf(
        "Push-ups", "Dips", "Pull-ups", "Chin-ups"
    ),
    ExerciseType.LEGS to listOf(
        "Squats", "Lunges", "Step-ups", "Calf Raises"
    ),
    ExerciseType.CHEST to listOf(
        "Incline Push-ups", "Chest Dips", "Planche Push-ups"
    ),
    ExerciseType.CORE to listOf(
        "Plank", "Leg Raises", "Mountain Climbers", "Russian Twists"
    ),
    ExerciseType.SHOULDERS to listOf(
        "Pike Push-ups", "Handstand Push-ups", "Lateral Raises"
    ),
    ExerciseType.BACK to listOf(
        "Pull-ups", "Inverted Rows", "Supermans", "Australian Pull-ups"
    )
)

val crossfitExercises = mapOf(
    ExerciseType.ARMS to listOf(
        "Kettlebell Swings", "Dumbbell Snatches", "Rope Climbs"
    ),
    ExerciseType.LEGS to listOf(
        "Box Jumps", "Wall Balls", "Deadlifts", "Overhead Squats"
    ),
    ExerciseType.CHEST to listOf(
        "Burpees", "Chest-to-Bar Pull-ups", "Bench Press"
    ),
    ExerciseType.CORE to listOf(
        "Toes-to-Bar", "Sit-ups", "GHD Sit-ups", "Hollow Rocks"
    ),
    ExerciseType.SHOULDERS to listOf(
        "Handstand Walks", "Push Press", "Thrusters"
    ),
    ExerciseType.BACK to listOf(
        "Deadlifts", "Barbell Rows", "Ring Rows", "Snatches"
    )
)

val strengthExercises = mapOf(
    ExerciseType.ARMS to listOf(
        "Bicep Curls", "Tricep Extensions", "Hammer Curls", "Skull Crushers"
    ),
    ExerciseType.LEGS to listOf(
        "Squats", "Leg Press", "Deadlifts", "Hamstring Curls"
    ),
    ExerciseType.CHEST to listOf(
        "Bench Press", "Incline Bench Press", "Chest Flyes"
    ),
    ExerciseType.CORE to listOf(
        "Plank", "Hanging Leg Raises", "Cable Crunches", "Ab Rollouts"
    ),
    ExerciseType.SHOULDERS to listOf(
        "Overhead Press", "Arnold Press", "Lateral Raises", "Front Raises"
    ),
    ExerciseType.BACK to listOf(
        "Deadlifts", "Pull-ups", "Lat Pulldowns", "Seated Rows"
    )
)

val trainingTypeExercises = mapOf(
    TrainingType.STRENGTH to strengthExercises,
    TrainingType.CALISTHENICS to calisthenicsExercises,
    TrainingType.CROSSFIT to crossfitExercises
)

// Function to generate random workout plan with Exercise objects including GIFs and descriptions
fun generateRandomPlan(
    trainingType: TrainingType,
    muscleGroups: MutableList<ExerciseType>,
    numberOfWorkouts: Int
): List<Workout> {
    val exerciseMap = trainingTypeExercises[trainingType] ?: return emptyList()
    val workouts = mutableListOf<Workout>()
    val exerciseTypes: List<ExerciseType> = muscleGroups

    repeat(numberOfWorkouts) { index ->
        val workoutExercises = mutableListOf<Exercise>()
        for (exerciseType in exerciseTypes) {
            val exercises = exerciseMap[exerciseType] ?: continue
            val randomExercise = exercises.random()
            val exerciseDescription = "Sample description for $randomExercise"
            val exerciseGif = getExerciseGif(exerciseType) // Replace with your logic to get GIFs

            workoutExercises.add(
                Exercise(
                    name = randomExercise,
                    gifResId = exerciseGif,
                    description = exerciseDescription,
                    type = exerciseType,
                    training = trainingType,
                    sets = 4,
                    totalTime = 90 // Assuming you have a method to map ExerciseType to R.drawable
                )
            )
        }
        val workout = Workout(index,"Workout ${index + 1}", Weekdays.WEDNESDAY,workoutExercises)
        workouts.add(workout)
    }

    return workouts
}

// Dummy function to get exercise GIFs based on ExerciseType (replace with your logic)
fun getExerciseGif(exerciseType: ExerciseType): Int {
    return when (exerciseType) {
        ExerciseType.ARMS -> R.drawable.arms2
        ExerciseType.LEGS -> R.drawable.legs4
        ExerciseType.CHEST -> R.drawable.chest
        ExerciseType.CORE -> R.drawable.abs2
        ExerciseType.SHOULDERS -> R.drawable.shoulders
        ExerciseType.BACK -> R.drawable.back2
    }
}





