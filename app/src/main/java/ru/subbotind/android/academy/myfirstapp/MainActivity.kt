package ru.subbotind.android.academy.myfirstapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.subbotind.android.academy.myfirstapp.ui.FragmentMoviesList

class MainActivity : AppCompatActivity() {

    private lateinit var movieListFragment: FragmentMoviesList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            movieListFragment = FragmentMoviesList.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, movieListFragment, FragmentMoviesList.TAG)
                .commit()
        } else {
            movieListFragment =
                supportFragmentManager.findFragmentByTag(FragmentMoviesList.TAG) as FragmentMoviesList
        }
    }
}