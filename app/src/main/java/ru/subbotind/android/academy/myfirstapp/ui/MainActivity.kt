package ru.subbotind.android.academy.myfirstapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkRequest
import dagger.hilt.android.AndroidEntryPoint
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.background.WorkController
import ru.subbotind.android.academy.myfirstapp.ui.movielist.MovieListFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var movieListFragment: MovieListFragment

    @Inject
    lateinit var movieSynchronizationRequest: WorkRequest

    @Inject
    lateinit var workController: WorkController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            movieListFragment = MovieListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, movieListFragment, MovieListFragment.TAG)
                .commit()
        }

        workController.startWork(movieSynchronizationRequest)
    }
}