package com.project.gains

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.project.gains.presentation.MainScreen
import com.project.gains.presentation.MainViewModel
import com.project.gains.presentation.notification.NotificationWorker
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.Constants.NOTIFICATION_CHANNEL_ID
import com.project.gains.util.Constants.NOTIFICATION_DESCRIPTION
import com.project.gains.util.Constants.NOTIFICATION_NAME
import com.project.gains.util.Constants.WORK_NAME
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.orientation.observe(this, Observer { orientation ->
            requestedOrientation = orientation
        })

        setContent {

            GainsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val startDestination = viewModel.startDestination
                    MainScreen(
                        mainViewModel = viewModel,
                        startDestination = startDestination,
                    )
                }
                Log.d("DEBUG","viewModel check ${viewModel.isAuth()}")

                if (viewModel.isAuth()) {
                    LaunchedEffect(key1 = Unit) {
                        Log.d("DEBUG","recomposed")
                        createNotificationChannel()
                        scheduleNotificationWorker(applicationContext)
                    }
                }

            }
        }
    }

    private fun createNotificationChannel() {

        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        notificationChannel.description = NOTIFICATION_DESCRIPTION

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun scheduleNotificationWorker(context: Context) {

        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
            .build()
        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

    }


}

