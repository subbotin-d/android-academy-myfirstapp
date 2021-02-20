package ru.subbotind.android.academy.myfirstapp.background

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.subbotind.android.academy.myfirstapp.domain.usecase.FetchMoviesUseCase

@HiltWorker
class SynchronizationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val fetchMoviesUseCase: FetchMoviesUseCase
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = try {
        fetchMoviesUseCase.fetchMovies()
        Result.success()
    } catch (e: Throwable) {
        Result.failure()
    }
}