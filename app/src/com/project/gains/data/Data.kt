package com.project.gains.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.project.gains.R
import com.project.gains.presentation.navgraph.Route
import kotlin.random.Random

// structures
enum class TrainingType {
    STRENGTH, CALISTHENICS, CROSSFIT
}

enum class Level {
    BEGINNER, INTERMEDIATE, EXPERT
}

enum class ExerciseType {
    CHEST, BACK, LEGS, ARMS, SHOULDERS, CORE
}

enum class TrainingMetricType {
    BPM, WEIGHTS, FREQUENCY, KCAL
}
enum class PeriodMetricType {
    WEEK, MONTH, YEAR
}

enum class PlotType {
    BAR, PIE
}

enum class Weekdays {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

enum class Frequency {
    TWO,THREE,FOUR
}

enum class Categories {
    User, Workout, Keyword, Social
}

enum class Socials {
    Facebook, TikTok, Instagram, X
}

data class Exercise(
    val name: String,
    val description: List<String>,  // Nullable Integer for resource ID
    val gifResId: Int?,
    val type: ExerciseType,
    val training: TrainingType,
    val sets: Int,
    val totalTime: Int,
    val warnings: List<String> = listOf(""),
    val videoId: Int
)

data class Plan(
    val id: Int,
    val name: String,
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

data class UserProfileBundle(
    var displayName: String,
    var email: String
)

data class GymPost(
    val id: String,
    val userResourceId: Int,
    val imageResourceId: Int, // Resource ID of the image
    val username: String,
    val social: String,
    val randomSocialId: Int,
    val caption: String,
    val time:String,
    val likes : String,
    val comment:String
    // Add other necessary properties
)

// Data class to hold chart data
data class TrainingData(
    val type: TrainingMetricType,
    var value: Int
)

data class Option(
    val name: String,
    var isChecked: Boolean = false
)

sealed class BottomNavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String,
    val hasNews: Boolean,
    var badgeCount: Int? = null,
    ) {
    data object Home : BottomNavItem(Route.HomeScreen.route, Icons.Filled.Home, Icons.Outlined.Home, "Home", false)
    data object Add : BottomNavItem(Route.NewPlanScreen.route, Icons.Filled.AddCircleOutline, Icons.Outlined.AddCircleOutline,"Add", false)
    data object Plan : BottomNavItem(Route.PlanScreen.route, Icons.Filled.Event, Icons.Outlined.Event,"Plan" , false)
    data object Settings : BottomNavItem(Route.SettingsScreen.route, Icons.Filled.Settings, Icons.Outlined.Settings,"Settings", false)
    data object Explore : BottomNavItem(Route.FeedScreen.route, Icons.Filled.Groups, Icons.Outlined.Groups, "Explore", false ,8)
}


// init data and functions

fun getRandomMessage(): String {
    val messages = listOf(
        "You have a new workout!",
        "Don't forget to stay hydrated!",
        "Time for a break, stretch your legs!",
        "New exercises have been added!",
        "Track your progress regularly!",
        "Push harder today!",
        "Great job! Keep it up!",
        "Remember to cool down after workouts."
    )
    return messages.random()
}

fun generateSampleExercises(): MutableList<Exercise> {
    return mutableListOf(
        Exercise(
            "Dumbell Curl",
            description = listOf(
                    "1. Stand upright with a dumbbell in each hand, arms fully extended by your sides, palms facing forward.",
                    "2. Keep your elbows close to your torso and engage your core for stability.",
                    "3. Begin the movement by flexing your elbows, curling the dumbbells towards your shoulders while keeping your upper arms stationary." ,
                    "4. Continue to curl until the dumbbells are at shoulder level, and your biceps are fully contracted." ,
                    "5. Hold the contracted position for a moment, then slowly lower the dumbbells back to the starting position with control." ,
                    "6. Repeat for the desired number of repetitions." ,),
            gifResId = R.drawable.arms2,
            type = ExerciseType.ARMS,
            training = TrainingType.STRENGTH,
            sets = 4,
            totalTime = 90,
            warnings = listOf(
                    "- Use a controlled motion throughout the exercise, avoiding swinging or momentum." ,
                    "- Keep your wrists straight and neutral to maximize bicep activation and reduce strain on the wrists." ,
                    "- Focus on squeezing the biceps at the top of the movement to maximize muscle engagement." ,
                    "- Exhale as you lift the weights and inhale as you lower them, maintaining steady breathing." ,
                    "- Choose an appropriate weight that allows you to perform the exercise with proper form and control.",),
            R.raw.chest
        ),
        Exercise(
            "Bench Press",
            description = listOf(     "1. Lie flat on a bench with your feet firmly planted on the ground." ,
                "2. Grip the barbell slightly wider than shoulder-width apart, with your palms facing forward." ,
                "3. Lower the barbell slowly towards your chest, keeping your elbows at a 45-degree angle." ,
                "4. Push the barbell back up to the starting position, fully extending your arms." ,
                "5. Repeat for the desired number of repetitions." ,),
            gifResId = R.drawable.chest,
            type = ExerciseType.CHEST,
            training = TrainingType.STRENGTH,
            sets = 4,
            totalTime = 90,
            warnings = listOf("- Keep your back flat against the bench and avoid arching excessively." ,
                "- Maintain a steady breathing pattern, exhaling as you push the barbell up." ,
                "- Ensure the barbell is balanced and controlled throughout the movement.",),
            R.raw.chest
        ),
        Exercise(
            "Squat",
            description = listOf(  "1. Stand with your feet shoulder-width apart, toes pointing slightly outward." ,
                "2. Place the barbell on your upper back, gripping it firmly with your hands." ,
                "3. Lower your body by bending your knees and hips, keeping your chest up and back straight." ,
                "4. Continue lowering until your thighs are parallel to the ground." ,
                "5. Push through your heels to return to the starting position." ,
                "6. Repeat for the desired number of repetitions." ,),
            gifResId = R.drawable.legs4,
            type = ExerciseType.LEGS,
            training = TrainingType.STRENGTH,
            sets = 4,
            totalTime = 90,
            warnings = listOf(
                        "- Keep your knees in line with your toes throughout the movement." ,
                        "- Avoid leaning forward excessively; maintain an upright posture." ,
                        "- Breathe in as you lower your body and exhale as you push back up.",),
            R.raw.chest
        ),
        Exercise(
            "Deadlift",
            description = listOf(
                    "1. Stand with your feet hip-width apart, toes pointing forward." ,
                    "2. Bend at your hips and knees to grip the barbell with your hands slightly wider than shoulder-width apart." ,
                    "3. Keep your back straight and chest up as you lift the barbell by extending your hips and knees." ,
                    "4. Stand up fully, bringing the barbell to hip level." ,
                    "5. Lower the barbell back to the ground with control." ,
                    "6. Repeat for the desired number of repetitions." ,),
            gifResId = R.drawable.backk,
            type = ExerciseType.BACK,
            training = TrainingType.STRENGTH,
            sets = 4,
            totalTime = 90,
            warnings = listOf(              "- Engage your core and keep your back straight to prevent injury." ,
                "- Use a mixed grip or overhand grip for better control." ,
                "- Breathe in as you lift the barbell and exhale as you lower it.",),
            R.raw.chest
        ),
        Exercise(
            "Shoulder Press",
            description = listOf(   "1. Sit on a bench with back support and hold a dumbbell in each hand at shoulder level." ,
                "2. Press the dumbbells upward until your arms are fully extended." ,
                "3. Lower the dumbbells back to shoulder level with control." ,
                "4. Repeat for the desired number of repetitions." ,),
            gifResId = R.drawable.shoulders,
            type = ExerciseType.SHOULDERS,
            training = TrainingType.STRENGTH,
            sets = 4,
            totalTime = 90,
            warnings = listOf(
                "- Keep your core engaged and back straight throughout the exercise." ,
                "- Avoid locking your elbows at the top of the movement." ,
                "- Breathe out as you press the dumbbells up and breathe in as you lower them.",),
            R.raw.chest
        ),
        Exercise(
            "Lunges",
            description = listOf(      "1. Stand upright with a dumbbell in each hand, arms by your sides." ,
                "2. Step forward with one leg, lowering your hips until both knees are bent at a 90-degree angle." ,
                "3. Push through the front heel to return to the starting position." ,
                "4. Repeat on the other leg." ,
                "5. Continue alternating legs for the desired number of repetitions." ,),
            gifResId = R.drawable.legs,
            type = ExerciseType.LEGS,
            training = TrainingType.STRENGTH,
            sets = 4,
            totalTime = 90,
            warnings = listOf(   "- Keep your upper body straight and core engaged." ,
                "- Ensure your front knee does not extend past your toes." ,
                "- Maintain balance by keeping your feet hip-width apart.",),
            R.raw.chest
        ),
        Exercise(
            "Plank",
            description = listOf(                    "1. Start in a push-up position, with your forearms on the ground and elbows directly below your shoulders." ,
                "2. Keep your body in a straight line from head to heels." ,
                "3. Hold this position for the desired amount of time." ,),
            gifResId = R.drawable.abs,
            type = ExerciseType.CORE,
            training = TrainingType.STRENGTH,
            sets = 4,
            totalTime = 90,
            warnings = listOf(  "- Engage your core and glutes to maintain a straight line." ,
                "- Avoid letting your hips sag or rise too high." ,
                "- Breathe steadily throughout the hold.",),
            R.raw.chest
        ),
        Exercise(
            "Pull-Ups",
            description = listOf(                    "1. Hang from a pull-up bar with your palms facing forward and hands slightly wider than shoulder-width apart." ,
                "2. Pull your body upward until your chin is above the bar." ,
                "3. Lower yourself back to the starting position with control." ,
                "4. Repeat for the desired number of repetitions." ,),
            gifResId = R.drawable.back2,
            type = ExerciseType.BACK,
            training = TrainingType.STRENGTH,
            sets = 4,
            totalTime = 90,
            warnings = listOf(
                "- Engage your back and biceps to pull yourself up." ,
                "- Avoid swinging or using momentum." ,
                "- Keep your core tight and legs slightly bent.",),
            R.raw.chest
        ),
        Exercise(
            "Tricep Push Ups",
            description = listOf(  "1. Position your hands shoulder-width apart on a stable bench or chair." ,
                    "2. Extend your legs out in front of you." ,
                    "3. Lower your body by bending your elbows until your upper arms are parallel to the ground." ,
                    "4. Push back up to the starting position." ,
                    "5. Repeat for the desired number of repetitions." ,),
            gifResId = R.drawable.arms3,
            type = ExerciseType.ARMS,
            training = TrainingType.STRENGTH,
            sets = 4,
            totalTime = 90,
            warnings = listOf("- Keep your back close to the bench." ,
                "- Avoid locking your elbows at the top of the movement." ,
                "- Maintain control throughout the exercise.",),
            R.raw.chest
        ),
        Exercise(
            "Leg Extension",
            description = listOf(  "1. Sit on the leg press machine with your feet shoulder-width apart on the platform." ,
                "2. Push the platform away by extending your legs." ,
                "3. Slowly lower the platform back to the starting position." ,
                "4. Repeat for the desired number of repetitions." ,),
            gifResId = R.drawable.legs2,
            type = ExerciseType.LEGS,
            training = TrainingType.STRENGTH,
            sets = 4,
            totalTime = 90,
            warnings = listOf(        "- Keep your knees in line with your toes." ,
                "- Avoid locking your knees at the top of the movement." ,
                "- Breathe out as you push the platform and breathe in as you lower it.",),
            R.raw.chest
        )
    )
}


fun generateSampleWorkouts(): MutableList<Workout> {
    return mutableListOf(
        Workout(
            id = 1,
            name = "Workout 1",
            workoutDay = Weekdays.WEDNESDAY,
            exercises = generateSampleExercises()
        ),
        Workout(
            id = 1,
            name = "Workout 2",
            workoutDay = Weekdays.FRIDAY,
            exercises  = generateSampleExercises()

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
        Option(TrainingMetricType.WEIGHTS.toString()),
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
            name = "My strength plan",
            workouts = generateSampleWorkouts()
        ),
        Plan(
            id = 2,
            name = "My Aerobic routine",
            workouts = generateSampleWorkouts()
        ),
        Plan(
            id = 3,
            name = "Cardio plan",
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
    val socials = listOf(
        "Facebook",
        "TikTok",
        "Instagram",
        "X"
    )


    repeat(count) {
        val randomUsername = usernames.random()
        val randomUserImageResourceId = userResourceIconsIds.random()
        val randomImageResourceId = imageResourceIds.random()
        val socialId = Random.nextInt(4)
        val randomSocial = socials[socialId]
        val randomSocialId = socialResourceIconsIds[socialId]
        val postId = "post_$it"
        val post = GymPost(postId, randomUserImageResourceId,randomImageResourceId, randomUsername, randomSocial, randomSocialId, "Good morning Fit-Family. Happy Sunday to you and your families. " +
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
    val metrics = listOf(TrainingMetricType.BPM, TrainingMetricType.WEIGHTS, TrainingMetricType.FREQUENCY, TrainingMetricType.KCAL)
    return List(months) {
        TrainingData(
            type = metrics.random(),
            value = (1..10).random()
        )
    }
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
            val exerciseDescription = listOf("Sample description for $randomExercise")
            val exerciseGif = getExerciseGif(exerciseType) // Replace with your logic to get GIFs

            workoutExercises.add(
                Exercise(
                    name = randomExercise,
                    description = exerciseDescription,
                    gifResId = exerciseGif,
                    type = exerciseType,
                    training = trainingType,
                    sets = 4,
                    totalTime = 90,
                    videoId = R.raw.chest // Assuming you have a method to map ExerciseType to R.drawable
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





