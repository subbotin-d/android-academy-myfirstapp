package ru.subbotind.android.academy.myfirstapp.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.work.WorkRequest
import dagger.hilt.android.AndroidEntryPoint
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.background.WorkController
import ru.subbotind.android.academy.myfirstapp.di.ChannelIdQualifier
import ru.subbotind.android.academy.myfirstapp.ui.moviedetails.MovieDetailsFragment
import ru.subbotind.android.academy.myfirstapp.ui.movielist.MovieListFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ChannelIdQualifier
    @Inject
    lateinit var channelId: String

    @Inject
    lateinit var movieSynchronizationRequest: WorkRequest

    @Inject
    lateinit var workController: WorkController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            openMoviesScreen()
            intent?.let(::handleIntent)
        }

        workController.startWork(movieSynchronizationRequest)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val movieId = intent.data?.lastPathSegment?.toIntOrNull()
                movieId?.let {
                    openMovieScreen(movieId)
                }
            }
        }
    }

    private fun openMoviesScreen() {
        val movieListFragment = MovieListFragment.newInstance()

        supportFragmentManager.commit {
            add(R.id.fragmentContainer, movieListFragment, MovieListFragment.TAG)
        }
    }

    private fun openMovieScreen(movieId: Int) {
        supportFragmentManager.popBackStack(
            MovieListFragment.TAG,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )

        val movieDetailsFragment = MovieDetailsFragment.newInstance(movieId)

        supportFragmentManager.commit {
            add(R.id.fragmentContainer, movieDetailsFragment, MovieListFragment.TAG)
            addToBackStack(MovieDetailsFragment.TAG)
        }
    }

    override fun onStart() {
        super.onStart()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = getString(R.string.channel_name_text)
            val channelDescription = getString(R.string.channel_description_text)
            val importance = NotificationManager.IMPORTANCE_DEFAULT


            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            NotificationManagerCompat.from(applicationContext).createNotificationChannel(channel)
        }
    }
}