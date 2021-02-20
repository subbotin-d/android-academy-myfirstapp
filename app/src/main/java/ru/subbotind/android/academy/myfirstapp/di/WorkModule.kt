package ru.subbotind.android.academy.myfirstapp.di

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.subbotind.android.academy.myfirstapp.background.SynchronizationWorker
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object WorkModule {

    private const val PERIOD_HOURS = 8L

    @Provides
    fun provideJobConstraints(): Constraints = Constraints.Builder()
        .setRequiresCharging(true)
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    @Provides
    fun provideJobRequest(jobConstraints: Constraints): WorkRequest =
        PeriodicWorkRequestBuilder<SynchronizationWorker>(
            PERIOD_HOURS,
            TimeUnit.HOURS
        )
            .setConstraints(jobConstraints)
            .build()
}