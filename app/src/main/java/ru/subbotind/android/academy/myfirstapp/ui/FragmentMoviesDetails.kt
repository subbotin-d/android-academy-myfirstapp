package ru.subbotind.android.academy.myfirstapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.data.DataContainer
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentMoviesDetailsBinding

private const val MOVIE_ID_KEY = "MOVIE_ID_KEY"

class FragmentMoviesDetails : Fragment() {
    private var movieId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getLong(MOVIE_ID_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMoviesDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movies_details, container, false)

        val movie = movieId?.let { DataContainer.getMovie(it) }

        binding.movie = movie

        binding.backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Long) = FragmentMoviesDetails().apply {
            arguments = Bundle().apply {
                putLong(MOVIE_ID_KEY, movieId)
            }
        }
    }
}