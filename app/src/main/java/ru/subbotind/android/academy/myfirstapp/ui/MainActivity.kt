package ru.subbotind.android.academy.myfirstapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.ui.movielist.MovieListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var movieListFragment: MovieListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            movieListFragment = MovieListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, movieListFragment, MovieListFragment.TAG)
                .commit()
        } else {
            movieListFragment =
                supportFragmentManager.findFragmentByTag(MovieListFragment.TAG) as MovieListFragment
        }
    }
}