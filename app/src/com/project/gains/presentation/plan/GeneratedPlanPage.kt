package com.project.gains.presentation.plan

import androidx.annotation.DrawableRes
import com.project.gains.R
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.TrainingType

data class PlanPages(
    val title: String,
    val pages:MutableList<PlanPage>
)

data class PlanPage(
    val title: String,
    @DrawableRes val image: Int,
    val level: Level = Level.BEGINNER,
    val trainingType: TrainingType = TrainingType.STRENGTH,
    val periodMetricType: PeriodMetricType = PeriodMetricType.WEEK
)

val pages = listOf(
    PlanPages(
        "Select what you think is your level",
        mutableListOf<PlanPage>(
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
        mutableListOf<PlanPage>(
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
        "Select the training plan period",
        mutableListOf<PlanPage>(
            PlanPage( title = PeriodMetricType.WEEK.toString(),
                image = R.drawable.pexels1,
                periodMetricType = PeriodMetricType.WEEK),
            PlanPage( title = PeriodMetricType.MONTH.toString(),
                image = R.drawable.pexels2,
                periodMetricType = PeriodMetricType.MONTH),
            PlanPage( title = PeriodMetricType.YEAR.toString(),
                image = R.drawable.pexels3,
                periodMetricType = PeriodMetricType.YEAR)
        )
    )
)