package ru.subbotind.android.academy.myfirstapp.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.data.Actor
import ru.subbotind.android.academy.myfirstapp.data.Movie
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentMoviesDetailsBinding
import ru.subbotind.android.academy.myfirstapp.presentation.moviedetails.MovieDetailsViewModel
import ru.subbotind.android.academy.myfirstapp.presentation.moviedetails.MovieDetailsViewModel.MovieDetailsState
import ru.subbotind.android.academy.myfirstapp.ui.extensions.setOnDebouncedClickListener
import ru.subbotind.android.academy.myfirstapp.ui.moviedetails.adapter.ActorAdapter

private const val MOVIE_ID_KEY = "MOVIE_ID_KEY"

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    companion object {
        fun newInstance(movieId: Int) = MovieDetailsFragment().apply {
            arguments = bundleOf(MOVIE_ID_KEY to movieId)
        }
    }

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    private var actorAdapter: ActorAdapter? = null
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

        initRecycler()
        initListener()

        movieDetailsViewModel.movieState.observe(viewLifecycleOwner, ::renderMovie)

        movieId?.let { movieDetailsViewModel.loadMovie(it) }
            ?: throw IllegalArgumentException("movieId is null, but should not")

        return binding.root
    }

    private fun renderMovie(state: MovieDetailsState) {
        when (state) {
            MovieDetailsState.LoadingStarted -> setLoading(true)
            MovieDetailsState.LoadingSuccess -> setLoading(false)

            is MovieDetailsState.MovieWithActors -> {
                showCastSection()
                showMovieData(state.movie)
                showActors(state.movie.actors)
            }

            is MovieDetailsState.MovieWithoutActors -> {
                hideCastSection()
                showMovieData(state.movie)
            }
        }
    }

    private fun showMovieData(movie: Movie) {
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
        }
    }

    private fun showActors(actors: List<Actor>) {
        actorAdapter?.submitList(actors)
    }

    private fun setLoading(inProgress: Boolean) {
        binding.progressBar.visibility = if (inProgress) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun initRecycler() {
        actorAdapter = ActorAdapter()
        binding.actorList.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            adapter = actorAdapter
        }
    }

    private fun initListener() {
        binding.backButton.setOnDebouncedClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun hideCastSection() {
        binding.apply {
            castLineLabel.visibility = View.GONE
            actorList.visibility = View.GONE
        }
    }

    private fun showCastSection() {
        binding.apply {
            castLineLabel.visibility = View.VISIBLE
            actorList.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        actorAdapter = null
    }
}