package ru.subbotind.android.academy.myfirstapp.background

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import ru.subbotind.android.academy.myfirstapp.DeepLinks
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.di.ChannelIdQualifier
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.usecase.FetchMoviesUseCase
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetNewMovieUseCase
import ru.subbotind.android.academy.myfirstapp.ui.MainActivity

@HiltWorker
class SynchronizationWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters,
    private val fetchMoviesUseCase: FetchMoviesUseCase,
    private val getNewMovieUseCase: GetNewMovieUseCase,
    @ChannelIdQualifier private val channelId: String
) : CoroutineWorker(appContext, params) {

    private companion object {
        const val REQUEST_CONTENT = 1
    }

    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(appContext)

    override suspend fun doWork(): Result = try {
        fetchMoviesUseCase.fetchMovies()
        getNewMovieUseCase.getNewMovie().collectLatest { movie ->
            movie?.let {
                showNotification(movie)
            }
        }
        Result.success()
    } catch (e: Throwable) {
        Result.failure()
    }

    private fun showNotification(movie: Movie) {
        notificationManager.notify(movie.id, createNotification(movie))
    }

    private fun createNotification(movie: Movie): Notification {
        val intent = createPendingIntent(movie.id)

        return NotificationCompat.Builder(appContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(movie.title)
            .setContentText(movie.overview)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(intent)
            .build()
    }

    private fun createPendingIntent(movieId: Int): PendingIntent {
        val movieDetailsDeepLink: Uri = DeepLinks.getMovieDetailsDeepLink(movieId)

        return PendingIntent.getActivity(
            appContext,
            REQUEST_CONTENT,
            Intent(appContext, MainActivity::class.java)
                .setAction(Intent.ACTION_VIEW)
                .setData(movieDetailsDeepLink),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}