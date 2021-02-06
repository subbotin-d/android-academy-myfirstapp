package ru.subbotind.android.academy.myfirstapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import ru.subbotind.android.academy.myfirstapp.background.SynchronizationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MovieApp : Application(), Configuration.Provider {

    companion object {
        private const val PERIOD = 8L
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        val jobConstraints: Constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val jobRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<SynchronizationWorker>(
            PERIOD,
            TimeUnit.HOURS
        )
            .setConstraints(jobConstraints)
            .build()

        WorkManager.getInstance(this)
            .enqueue(jobRequest)
    }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()
}