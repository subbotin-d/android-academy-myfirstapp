package ru.subbotind.android.academy.myfirstapp.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentMoviesDetailsBinding
import ru.subbotind.android.academy.myfirstapp.domain.MovieInteractor
import ru.subbotind.android.academy.myfirstapp.domain.MovieInteractorImpl
import ru.subbotind.android.academy.myfirstapp.ui.extensions.setOnDebouncedClickListener
import ru.subbotind.android.academy.myfirstapp.ui.moviedetails.adapter.ActorAdapter

private const val MOVIE_ID_KEY = "MOVIE_ID_KEY"

class MovieDetailsFragment : Fragment() {

    companion object {
        fun newInstance(movieId: Int) = MovieDetailsFragment().apply {
            arguments = bundleOf(MOVIE_ID_KEY to movieId)
        }
    }

    private var movieInteractor: MovieInteractor? = null
    private var movieId: Int? = null
    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)

        movieInteractor = MovieInteractorImpl(viewLifecycleOwner.lifecycleScope, requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            val movie = movieId?.let { movieInteractor?.getMovie(it) }
                ?: throw IllegalStateException("Movie with id = $movieId does not exist")

            binding.apply {
                Glide.with(requireContext())
                    .load(movie.backdrop)
                    .fitCenter()
                    .into(background)

                pgRating.text = requireContext().getString(
                    R.string.pg_rating,
                    movie.minimumAge.toString()
                )

                movieTitleText.text = movie.title

                tagText.text = movie.genres.joinToString { it.name }

                ratingBar.setCurrentRating(movie.ratings / 2)

                totalReviewsText.text = getString(
                    R.string.total_reviews,
                    movie.numberOfRatings.toString()
                )

                storyLineText.text = movie.overview

                val actorAdapter = ActorAdapter()
                actorList.apply {
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                    adapter = actorAdapter
                }

                if (movie.actors.isNotEmpty()) {
                    actorAdapter.submitList(movie.actors)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        movieInteractor = null
    }
}