package com.project.gains.presentation.plan

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.project.gains.R
import com.project.gains.data.Frequency
import com.project.gains.data.Level
import com.project.gains.data.TrainingType

@Immutable
data class PlanPages(
    val title: String,
    val pages: List<PlanPage>
)

data class PlanPage(
    val title: String,
    @DrawableRes val image: Int,
    val level: Level = Level.BEGINNER,
    val trainingType: TrainingType = TrainingType.STRENGTH,
    val frequency: Frequency=Frequency.THREE
)

val pages = listOf(
    PlanPages(
        "Select your level",
        listOf(
            PlanPage(title = Level.BEGINNER.toString(),
                image = R.drawable.pexels3,
                level = Level.BEGINNER),
            PlanPage(title = Level.INTERMEDIATE.toString(),
                image = R.drawable.pexels1,
                level = Level.INTERMEDIATE),
            PlanPage(title = Level.EXPERT.toString(),
                image = R.drawable.pexels2,
                level = Level.EXPERT)
        )
    ),
    PlanPages(
        "Select the training type",
        mutableListOf(
            PlanPage( title = TrainingType.STRENGTH.toString(),
                image = R.drawable.pexels4,
                trainingType = TrainingType.STRENGTH),
            PlanPage( title = TrainingType.CALISTHENICS.toString(),
                image = R.drawable.pexels3,
                trainingType = TrainingType.CALISTHENICS),
            PlanPage( title = TrainingType.CROSSFIT.toString(),
                image = R.drawable.pexels2,
                trainingType = TrainingType.CROSSFIT)
        )
    ),
    PlanPages(
            "Select workouts per week",
    mutableListOf(
        PlanPage( title = Frequency.TWO.toString(),
            image = R.drawable.pexels1,
            frequency = Frequency.TWO),
        PlanPage( title = Frequency.THREE.toString(),
            image = R.drawable.pexels2,
            frequency = Frequency.THREE),
        PlanPage( title = Frequency.FOUR.toString(),
            image = R.drawable.pexels3,
            frequency = Frequency.FOUR),
    )
)
)