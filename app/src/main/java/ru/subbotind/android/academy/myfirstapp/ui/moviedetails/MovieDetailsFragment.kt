package ru.subbotind.android.academy.myfirstapp.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.data.DataContainer
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentMoviesDetailsBinding
import ru.subbotind.android.academy.myfirstapp.ui.extensions.setOnDebouncedClickListener
import ru.subbotind.android.academy.myfirstapp.ui.moviedetails.adapter.ActorAdapter

private const val MOVIE_ID_KEY = "MOVIE_ID_KEY"

class MovieDetailsFragment : Fragment() {

    private var movieId: Long? = null
    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getLong(MOVIE_ID_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)

        val movie = movieId?.let { DataContainer.getMovie(it) }

        movie?.let {
            binding.apply {
                background.setImageResource(movie.mainImageBackground)
                pgRating.text = movie.pgRating
                movieTitleText.text = movie.title
                tagText.text = movie.tags
                ratingBar.setCurrentRating(movie.userRating)
                totalReviewsText.text =
                    getString(R.string.total_reviews, movie.reviewsCount.toString())
                storyLineText.text = movie.storyLine

                val actorAdapter = ActorAdapter()
                actorList.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = actorAdapter
                }

                if (movie.cast.isNotEmpty()) {
                    actorAdapter.submitList(movie.cast)
                } else {
                    hideCastSection()
                }
            }
        }

        binding.backButton.setOnDebouncedClickListener {
            fragmentManager?.popBackStack()
        }

        return binding.root
    }

    private fun hideCastSection() {
        binding.apply {
            castLineLabel.visibility = View.GONE
            actorList.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(movieId: Long) = MovieDetailsFragment().apply {
            arguments = bundleOf(MOVIE_ID_KEY to movieId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}