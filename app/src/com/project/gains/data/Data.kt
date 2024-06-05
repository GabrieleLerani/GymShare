package com.project.gains.data
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector
import com.project.gains.R
import com.project.gains.presentation.navgraph.Route
import kotlin.random.Random



// DB
/*import android.content.Context
import androidx.room.*

// Step 1: Define Entity Classes
@Entity(tableName = "training_data")
data class TrainingData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: TrainingMetricType,
    val value: Int
)
@Entity(tableName = "xn")
data class TrainingData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val calories: Int, val bpm: Int, val restTime: Int, val timeOfTraining: Int
)
@Entity(tableName = "players")
data class Player(
    @PrimaryKey
    val username: String,
    var location: Location,
    var distance: Double
)

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey
    val name: String,
    val gifResId: Int?,
    val description: String,
    val type: ExerciseType,
    val training: TrainingType,
    val muscle: MuscleGroup
)

@Entity(tableName = "plans")
data class Plan(
    @PrimaryKey
    val id: Int,
    val name: String,
    val period: PeriodMetricType
)

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey
    val id: Int,
    val name: String
)

@Entity(tableName = "user_profiles")
data class UserProfileBundle(
    @PrimaryKey
    val displayName: String,
    var email: String
)

@Entity(tableName = "gym_posts")
data class GymPost(
    @PrimaryKey
    val id: String,
    val imageResourceId: Int,
    val username: String,
    val randomSocialId: Int
)

@Entity(tableName = "chart_previews")
data class ProgressChartPreview(
    @PrimaryKey
    val name: String,
    @DrawableRes
    val imageResId: Int
)

@Entity(tableName = "chart_data")
data class ChartData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val values: List<Float>
)

@Entity(tableName = "options")
data class Option(
    @PrimaryKey
    val name: String,
    var isChecked: Boolean = false
)

// Step 2: Define Data Access Object (DAO) Interfaces
@Dao
interface TrainingDataDao {
    @Insert
    suspend fun insertTrainingData(trainingData: TrainingData)

    @Query("SELECT * FROM training_data")
    suspend fun getAllTrainingData(): List<TrainingData>

    @Delete
    suspend fun deleteTrainingData(trainingData: TrainingData)

    @Update
    suspend fun updateTrainingData(trainingData: TrainingData)
}

@Dao
interface PlayerDao {
    @Insert
    suspend fun insertPlayer(player: Player)

    @Query("SELECT * FROM players")
    suspend fun getAllPlayers(): List<Player>

    @Delete
    suspend fun deletePlayer(player: Player)

    @Update
    suspend fun updatePlayer(player: Player)
}

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Query("SELECT * FROM exercises")
    suspend fun getAllExercises(): List<Exercise>

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(exercise: Exercise)
}

// Define DAO interfaces for other data classes similarly

// Step 3: Create a Room Database
@Database(entities = [TrainingData::class, Player::class, Exercise::class, Plan::class, Workout::class,
    UserProfileBundle::class, GymPost::class, ProgressChartPreview::class, ChartData::class, Option::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trainingDataDao(): TrainingDataDao
    abstract fun playerDao(): PlayerDao
    abstract fun exerciseDao(): ExerciseDao
    // Define DAO abstract functions for other data classes similarly
}

// Step 4: Initialize Room Database
object DatabaseBuilder {
    fun build(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()
    }
}

// Step 5: Perform Database Operations
suspend fun insertTrainingData(trainingData: TrainingData) {
    appDatabase.trainingDataDao().insertTrainingData(trainingData)
}

suspend fun getAllTrainingData(): List<TrainingData> {
    return appDatabase.trainingDataDao().getAllTrainingData()
}

suspend fun deleteTrainingData(trainingData: TrainingData) {
    appDatabase.trainingDataDao().deleteTrainingData(trainingData)
}

suspend fun updateTrainingData(trainingData: TrainingData) {
    appDatabase.trainingDataDao().updateTrainingData(trainingData)
}*/

// Define functions for other database operations similarly


// structures


enum class TrainingType {
    RUNNING,
    STRENGTH,
    PILATES,
    CALISTHENICS,
    CROSSFIT
}

enum class ExerciseType {
    CARDIO,
    STRENGTH,
    FLEXIBILITY,
    BALANCE
}

enum class MuscleGroup {
    CHEST,
    BACK,
    LEGS,
    ARMS,
    SHOULDERS,
    CORE
}

enum class TrainingMetricType {
    DURATION, BPM, KCAL, INTENSITY, DISTANCE, REST
}
enum class PeriodMetricType {
    WEEK,MONTH, YEAR
}

data class Exercise(
    val name: String,
    val gifResId: Int?,  // Nullable Integer for resource ID
    val description: String,
    val type: ExerciseType,
    val training: TrainingType,
    val muscle: MuscleGroup
)
data class Plan(
    val id: Int,
    val name: String,
    val period: PeriodMetricType,
    val workouts: MutableList<Workout>
)

data class Workout(
    val id:Int,val name:String,val exercises:MutableList<Exercise>
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
    val imageResourceId: Int, // Resource ID of the image
    val username: String,
    val randomSocialId: Int,
    // Add other necessary properties
)

data class Plot(val preview: ProgressChartPreview, val data: List<TrainingData>)
// Data class to hold chart preview information
data class ProgressChartPreview(val name: String, @DrawableRes val imageResId: Int)

// Data class to hold chart data
data class TrainingData(val type: TrainingMetricType, val value: Int)


data class Option(val name: String, var isChecked: Boolean = false)


sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    data object Home : BottomNavItem(Route.HomeScreen.route, Icons.Default.Home, "Home")
    data object Workout : BottomNavItem(Route.WorkoutScreen.route, Icons.Default.FitnessCenter, "Workout")
    data object Progress : BottomNavItem(Route.ProgressScreen.route, Icons.Default.Analytics, "Progress")
    data object Plan : BottomNavItem(Route.PlansScreen.route, Icons.Default.Event, "Plans")
    data object Share : BottomNavItem(Route.ShareScreen.route, Icons.Default.Share, "Share")
}


// init data and functions
// TODO LOAD GIF NOT IMAGE
fun generateSampleExercises(): MutableList<Exercise> {
    return mutableListOf(
        Exercise("Exercise 1", description = "Exercise: Dumbbell Bicep Curl\n" +
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
                "- Choose an appropriate weight that allows you to perform the exercise with proper form and control.\n", gifResId = R.drawable.pexels5,
            type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
            training = TrainingType.STRENGTH
        ),
        Exercise("Exercise 2", description = "Exercise 2", gifResId = null,
            type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
            training = TrainingType.STRENGTH
        ),
        Exercise("Exercise 3", description = "Exercise 3", gifResId = null,
            type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
            training = TrainingType.STRENGTH
        ),
        Exercise("Exercise 4", description = "Exercise 4", gifResId = null,
            type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
            training = TrainingType.STRENGTH
        ),
        Exercise("Exercise 5", description = "Exercise 5", gifResId = null,
            type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
            training = TrainingType.STRENGTH
        ),
        Exercise("Exercise 6", description = "Exercise 6", gifResId = null,
            type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
            training = TrainingType.STRENGTH
        ),
        Exercise("Exercise 7", description = "Exercise 7", gifResId = null,
            type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
            training = TrainingType.STRENGTH
        ),
        Exercise("Exercise 8", description = "Exercise 8", gifResId = null,
            type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
            training = TrainingType.STRENGTH
        ),
        Exercise("Exercise 9", description = "Exercise 9", gifResId = null,
            type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
            training = TrainingType.STRENGTH
        ),
        Exercise("Exercise 10", description = "Exercise 10", gifResId = null,
            type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
            training = TrainingType.STRENGTH
        ),

        )
}

fun generateSampleWorkouts(): MutableList<Workout> {
    return mutableListOf(
        Workout(
            id = 1,
            name = "Workout 1",
            exercises = mutableListOf(
                Exercise("Exercise 1", description = "Exercise 1", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 2", description = "Exercise 2", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 3", description = "Exercise 3", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
            )
        ),
        Workout(
            id = 1,
            name = "Workout 2",
            exercises = mutableListOf(
                Exercise("Exercise 1", description = "Exercise 1", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 2", description = "Exercise 2", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 3", description = "Exercise 3", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
            )
        ),  Workout(
            id = 1,
            name = "Workout 3",
            exercises = mutableListOf(
                Exercise("Exercise 1", description = "Exercise 1", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 2", description = "Exercise 2", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 3", description = "Exercise 3", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
            )
        ),
        Workout(
            id = 1,
            name = "Workout 4",
            exercises = mutableListOf(
                Exercise("Exercise 1", description = "Exercise 1", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 2", description = "Exercise 2", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 3", description = "Exercise 3", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
            )
        ),
        Workout(
            id = 1,
            name = "Workout 5",
            exercises = mutableListOf(
                Exercise("Exercise 1", description = "Exercise 1", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 2", description = "Exercise 2", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 3", description = "Exercise 3", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
            )
        ),
        Workout(
            id = 1,
            name = "Workout 6",
            exercises = mutableListOf(
                Exercise("Exercise 1", description = "Exercise 1", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 2", description = "Exercise 2", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 3", description = "Exercise 3", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
            )
        ),
        Workout(
            id = 1,
            name = "Workout 7",
            exercises = mutableListOf(
                Exercise("Exercise 1", description = "Exercise 1", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 2", description = "Exercise 2", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
                Exercise("Exercise 3", description = "Exercise 3", gifResId = null,
                    type = ExerciseType.STRENGTH, muscle = MuscleGroup.ARMS,
                    training = TrainingType.STRENGTH
                ),
            )
        ),
        // Define more workouts as needed
    )
}
fun generateOptions(): List<Option> {
    return listOf(
        Option("MUSIC"),
        Option("BACKUP") ,
        Option(ExerciseType.STRENGTH.toString()),
        Option(ExerciseType.FLEXIBILITY.toString()),
        Option(ExerciseType.CARDIO.toString()),
        Option(ExerciseType.BALANCE.toString()),
        Option(TrainingMetricType.BPM.toString()),
        Option(TrainingMetricType.KCAL.toString()),
        Option(TrainingMetricType.INTENSITY.toString()),
        Option(TrainingMetricType.DURATION.toString()),
        Option(TrainingMetricType.DISTANCE.toString())
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
        R.drawable.logo,
        R.drawable.logo,
        R.drawable.logo
    )
    val socialResourceIconsIds = listOf(
        R.drawable.facebook_icon,
        R.drawable.tiktok_logo_icon,
        R.drawable.instagram_icon,
        R.drawable.x_logo_icon
    )

    repeat(count) {
        val randomUsername = usernames.random()
        val randomImageResourceId = imageResourceIds.random()
        val randomSocialId = socialResourceIconsIds.random()
        val postId = "post_$it"
        val post = GymPost(postId, randomImageResourceId, randomUsername, randomSocialId)
        posts.add(post)
    }

    return posts
}
fun generateRandomSongTitle(): String {
    val adjectives = listOf("Happy", "Sad", "Lonely", "Joyful", "Mysterious", "Crazy", "Calm", "Exciting", "Melodic", "Harmonic")
    val nouns = listOf("Love", "Dream", "Night", "Day", "Star", "Heart", "Journey", "Memory", "World", "Light")

    val randomAdjective = adjectives[Random.nextInt(adjectives.size)]
    val randomNoun = nouns[Random.nextInt(nouns.size)]

    return "$randomAdjective $randomNoun"
}
val bottomNavItems = listOf(
    BottomNavItem.Share,
    BottomNavItem.Plan,
    BottomNavItem.Workout,
    BottomNavItem.Home,
    BottomNavItem.Progress
)



fun generateRandomPlan(
    trainingType: TrainingType,
    exerciseType: MutableList<ExerciseType>,
    numberOfWorkouts: Int,
    numberOfMuscleGroups: Int
): List<Workout> {
    return (1..numberOfWorkouts).map { workoutId ->
        val exercises = mutableListOf<Exercise>()
        var availableMuscleGroups = MuscleGroup.entries.toList()

        repeat(numberOfMuscleGroups) { muscle ->
            val muscleGroup = availableMuscleGroups[muscle]
            val exerciseList = trainingTypeExercises[trainingType]?.get(trainingType)?.get(exerciseType.random())!![muscle]

            val exerciseName = exerciseList.second.randomOrNull()

            exercises.add(
                Exercise(
                    name = exerciseName ?: "Unknown",
                    description = "$exerciseType exercise targeting $muscleGroup",
                    type = exerciseType.random(),
                    gifResId = R.drawable.gi,
                    muscle = muscleGroup,
                    training = trainingType
                )
            )

            // Remove the selected muscle group to avoid duplication
            availableMuscleGroups = availableMuscleGroups.filterNot { it == muscleGroup }
        }

        Workout(id = workoutId, name = "Workout $workoutId", exercises = exercises)
    }
}


val runningExercises = mapOf(
    TrainingType.RUNNING to mapOf(
        ExerciseType.CARDIO to listOf(
            MuscleGroup.LEGS to listOf("Running", "Cycling", "Jump Rope"),
            MuscleGroup.ARMS to listOf("Boxing", "Swimming", "Rowing"),
            MuscleGroup.CORE to listOf("Mountain Climbers", "Burpees", "Jumping Jacks")
        ),
        ExerciseType.STRENGTH to listOf(
            MuscleGroup.CHEST to listOf("Push Up", "Bench Press", "Dumbbell Fly"),
            MuscleGroup.BACK to listOf("Pull Up", "Deadlift", "Bent Over Row"),
            MuscleGroup.LEGS to listOf("Squat", "Lunges", "Leg Press"),
            MuscleGroup.ARMS to listOf("Bicep Curl", "Tricep Dip", "Hammer Curl"),
            MuscleGroup.SHOULDERS to listOf("Shoulder Press", "Lateral Raise", "Front Raise"),
            MuscleGroup.CORE to listOf("Plank", "Russian Twist", "Crunches")
        ),
        ExerciseType.FLEXIBILITY to listOf(
            MuscleGroup.CORE to listOf("Yoga", "Pilates", "Stretching"),
            MuscleGroup.LEGS to listOf("Hamstring Stretch", "Quadriceps Stretch", "Calf Stretch"),
            MuscleGroup.ARMS to listOf("Triceps Stretch", "Biceps Stretch", "Shoulder Stretch")
        ),
        ExerciseType.BALANCE to listOf(
            MuscleGroup.LEGS to listOf("Single Leg Balance", "Bosu Ball Exercises", "Tai Chi"),
            MuscleGroup.CORE to listOf("Balance Board Exercises", "Plank Variations", "Standing Yoga Poses"),
            MuscleGroup.SHOULDERS to listOf("Overhead Reach and Balance", "Yoga Tree Pose", "Pilates Side Leg Lift")
        )
    )
)

val strengthExercises = mapOf(
    TrainingType.STRENGTH to mapOf(
        ExerciseType.CARDIO to listOf(
            MuscleGroup.LEGS to listOf("Running", "Cycling", "Jump Rope"),
            MuscleGroup.ARMS to listOf("Boxing", "Swimming", "Rowing"),
            MuscleGroup.CORE to listOf("Mountain Climbers", "Burpees", "Jumping Jacks")
        ),
        ExerciseType.STRENGTH to listOf(
            MuscleGroup.CHEST to listOf("Push Up", "Bench Press", "Dumbbell Fly"),
            MuscleGroup.BACK to listOf("Pull Up", "Deadlift", "Bent Over Row"),
            MuscleGroup.LEGS to listOf("Squat", "Lunges", "Leg Press"),
            MuscleGroup.ARMS to listOf("Bicep Curl", "Tricep Dip", "Hammer Curl"),
            MuscleGroup.SHOULDERS to listOf("Shoulder Press", "Lateral Raise", "Front Raise"),
            MuscleGroup.CORE to listOf("Plank", "Russian Twist", "Crunches")
        ),
        ExerciseType.FLEXIBILITY to listOf(
            MuscleGroup.CORE to listOf("Yoga", "Pilates", "Stretching"),
            MuscleGroup.LEGS to listOf("Hamstring Stretch", "Quadriceps Stretch", "Calf Stretch"),
            MuscleGroup.ARMS to listOf("Triceps Stretch", "Biceps Stretch", "Shoulder Stretch")
        ),
        ExerciseType.BALANCE to listOf(
            MuscleGroup.LEGS to listOf("Single Leg Balance", "Bosu Ball Exercises", "Tai Chi"),
            MuscleGroup.CORE to listOf("Balance Board Exercises", "Plank Variations", "Standing Yoga Poses"),
            MuscleGroup.SHOULDERS to listOf("Overhead Reach and Balance", "Yoga Tree Pose", "Pilates Side Leg Lift")
        )
    )
)

val pilatesExercises = mapOf(
    TrainingType.PILATES to mapOf(
        ExerciseType.CARDIO to listOf(
            MuscleGroup.LEGS to listOf("Running", "Cycling", "Jump Rope"),
            MuscleGroup.ARMS to listOf("Boxing", "Swimming", "Rowing"),
            MuscleGroup.CORE to listOf("Mountain Climbers", "Burpees", "Jumping Jacks")
        ),
        ExerciseType.STRENGTH to listOf(
            MuscleGroup.CHEST to listOf("Push Up", "Bench Press", "Dumbbell Fly"),
            MuscleGroup.BACK to listOf("Pull Up", "Deadlift", "Bent Over Row"),
            MuscleGroup.LEGS to listOf("Squat", "Lunges", "Leg Press"),
            MuscleGroup.ARMS to listOf("Bicep Curl", "Tricep Dip", "Hammer Curl"),
            MuscleGroup.SHOULDERS to listOf("Shoulder Press", "Lateral Raise", "Front Raise"),
            MuscleGroup.CORE to listOf("Plank", "Russian Twist", "Crunches")
        ),
        ExerciseType.FLEXIBILITY to listOf(
            MuscleGroup.CORE to listOf("Yoga", "Pilates", "Stretching"),
            MuscleGroup.LEGS to listOf("Hamstring Stretch", "Quadriceps Stretch", "Calf Stretch"),
            MuscleGroup.ARMS to listOf("Triceps Stretch", "Biceps Stretch", "Shoulder Stretch")
        ),
        ExerciseType.BALANCE to listOf(
            MuscleGroup.LEGS to listOf("Single Leg Balance", "Bosu Ball Exercises", "Tai Chi"),
            MuscleGroup.CORE to listOf("Balance Board Exercises", "Plank Variations", "Standing Yoga Poses"),
            MuscleGroup.SHOULDERS to listOf("Overhead Reach and Balance", "Yoga Tree Pose", "Pilates Side Leg Lift")
        )
    )
)

val calisthenicsExercises = mapOf(
    TrainingType.CALISTHENICS to mapOf(
        ExerciseType.CARDIO to listOf(
            MuscleGroup.LEGS to listOf("Running", "Cycling", "Jump Rope"),
            MuscleGroup.ARMS to listOf("Boxing", "Swimming", "Rowing"),
            MuscleGroup.CORE to listOf("Mountain Climbers", "Burpees", "Jumping Jacks")
        ),
        ExerciseType.STRENGTH to listOf(
            MuscleGroup.CHEST to listOf("Push Up", "Bench Press", "Dumbbell Fly"),
            MuscleGroup.BACK to listOf("Pull Up", "Deadlift", "Bent Over Row"),
            MuscleGroup.LEGS to listOf("Squat", "Lunges", "Leg Press"),
            MuscleGroup.ARMS to listOf("Bicep Curl", "Tricep Dip", "Hammer Curl"),
            MuscleGroup.SHOULDERS to listOf("Shoulder Press", "Lateral Raise", "Front Raise"),
            MuscleGroup.CORE to listOf("Plank", "Russian Twist", "Crunches")
        ),
        ExerciseType.FLEXIBILITY to listOf(
            MuscleGroup.CORE to listOf("Yoga", "Pilates", "Stretching"),
            MuscleGroup.LEGS to listOf("Hamstring Stretch", "Quadriceps Stretch", "Calf Stretch"),
            MuscleGroup.ARMS to listOf("Triceps Stretch", "Biceps Stretch", "Shoulder Stretch")
        ),
        ExerciseType.BALANCE to listOf(
            MuscleGroup.LEGS to listOf("Single Leg Balance", "Bosu Ball Exercises", "Tai Chi"),
            MuscleGroup.CORE to listOf("Balance Board Exercises", "Plank Variations", "Standing Yoga Poses"),
            MuscleGroup.SHOULDERS to listOf("Overhead Reach and Balance", "Yoga Tree Pose", "Pilates Side Leg Lift")
        )
    )
)

val crossfitExercises = mapOf(
    TrainingType.CROSSFIT to mapOf(
        ExerciseType.CARDIO to listOf(
            MuscleGroup.LEGS to listOf("Running", "Cycling", "Jump Rope"),
            MuscleGroup.ARMS to listOf("Boxing", "Swimming", "Rowing"),
            MuscleGroup.CORE to listOf("Mountain Climbers", "Burpees", "Jumping Jacks")
        ),
        ExerciseType.STRENGTH to listOf(
            MuscleGroup.CHEST to listOf("Push Up", "Bench Press", "Dumbbell Fly"),
            MuscleGroup.BACK to listOf("Pull Up", "Deadlift", "Bent Over Row"),
            MuscleGroup.LEGS to listOf("Squat", "Lunges", "Leg Press"),
            MuscleGroup.ARMS to listOf("Bicep Curl", "Tricep Dip", "Hammer Curl"),
            MuscleGroup.SHOULDERS to listOf("Shoulder Press", "Lateral Raise", "Front Raise"),
            MuscleGroup.CORE to listOf("Plank", "Russian Twist", "Crunches")
        ),
        ExerciseType.FLEXIBILITY to listOf(
            MuscleGroup.CORE to listOf("Yoga", "Pilates", "Stretching"),
            MuscleGroup.LEGS to listOf("Hamstring Stretch", "Quadriceps Stretch", "Calf Stretch"),
            MuscleGroup.ARMS to listOf("Triceps Stretch", "Biceps Stretch", "Shoulder Stretch")
        ),
        ExerciseType.BALANCE to listOf(
            MuscleGroup.LEGS to listOf("Single Leg Balance", "Bosu Ball Exercises", "Tai Chi"),
            MuscleGroup.CORE to listOf("Balance Board Exercises", "Plank Variations", "Standing Yoga Poses"),
            MuscleGroup.SHOULDERS to listOf("Overhead Reach and Balance", "Yoga Tree Pose", "Pilates Side Leg Lift")
        )
    )
)


val trainingTypeExercises = mapOf(
    TrainingType.RUNNING to runningExercises,
    TrainingType.STRENGTH to strengthExercises,
    TrainingType.PILATES to pilatesExercises,
    TrainingType.CALISTHENICS to calisthenicsExercises,
    TrainingType.CROSSFIT to crossfitExercises
)

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
        R.drawable.plo1,
        R.drawable.plot2,
        R.drawable.plot3,
    )

    val preview = ProgressChartPreview(
        name = names.random(),
        imageResId = drawableResources.random()  // Assuming drawable resources are in the range 1 to 100
    )

    val trainingTypes = TrainingMetricType.entries.toTypedArray()
    val trainingData = generateRandomTrainingData(4)

    return Plot(preview, trainingData)
}

// Function to generate a list of random Plot objects
fun generateRandomPlots(): MutableList<Plot> {
    return MutableList(2) { generateRandomPlot() }
}









