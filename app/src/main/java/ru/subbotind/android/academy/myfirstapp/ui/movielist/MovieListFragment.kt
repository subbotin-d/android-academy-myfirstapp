package ru.subbotind.android.academy.myfirstapp.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentMoviesListBinding
import ru.subbotind.android.academy.myfirstapp.presentation.error.ErrorState
import ru.subbotind.android.academy.myfirstapp.presentation.movielist.MovieListViewModel
import ru.subbotind.android.academy.myfirstapp.presentation.movielist.MovieListViewModel.MovieListState
import ru.subbotind.android.academy.myfirstapp.ui.error.OnCancelButtonClickListener
import ru.subbotind.android.academy.myfirstapp.ui.error.OnRetryButtonClickListener
import ru.subbotind.android.academy.myfirstapp.ui.extensions.showRetryErrorDialog
import ru.subbotind.android.academy.myfirstapp.ui.moviedetails.MovieDetailsFragment
import ru.subbotind.android.academy.myfirstapp.ui.movielist.adapter.MovieAdapter

@AndroidEntryPoint
class MovieListFragment : Fragment(), OnRetryButtonClickListener, OnCancelButtonClickListener {

    companion object {
        fun newInstance() = MovieListFragment()

        const val TAG = "moviesListFragment"
        const val ANIMATION_DURATION_MILLIS = 400L
    }

    private val movieListViewModel: MovieListViewModel by viewModels()

    private var movieAdapter: MovieAdapter? = null
    private var _binding: FragmentMoviesListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        postponeEnterTransition()

        setUpRecycler()
        movieListViewModel.moviesState.observe(viewLifecycleOwner, ::render)
        movieListViewModel.progressState.observe(viewLifecycleOwner, ::showLoading)
        movieListViewModel.errorState.observe(viewLifecycleOwner, ::handleError)

        return binding.root
    }

    private fun setUpRecycler() {
        movieAdapter = MovieAdapter(
            likeListener = onLikeIconClick(),
            cardListener = onMoviePromoCardClick()
        )

        val spanCount =
            calculateSpanCount(resources.getDimensionPixelSize(R.dimen.card_view_min_width))

        val gridLayoutManager = GridLayoutManager(activity, spanCount)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (position) {
                0 -> spanCount
                else -> 1
            }
        }

        binding.movieListRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = movieAdapter
        }
    }

    private fun render(state: MovieListState) {
        when (state) {
            MovieListState.EmptyMovies -> showEmptyStub()
            is MovieListState.Success -> {
                showMovies()
                movieAdapter?.submitData(state.movies)

                (view?.parent as? ViewGroup)?.doOnPreDraw {
                    startPostponedEnterTransition()
                }
            }
        }
    }

    private fun handleError(errorState: ErrorState) {
        when (errorState) {
            is ErrorState.ServerError -> showRetryErrorDialog(errorState.cause)
            is ErrorState.UnexpectedError -> showRetryErrorDialog(errorState.cause)
        }
    }

    private fun showLoading(inProgress: Boolean) {
        binding.progressBar.visibility = if (inProgress) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showEmptyStub() {
        binding.apply {
            noMoviesImage.visibility = View.VISIBLE
            noMoviesText.visibility = View.VISIBLE
            movieListRecyclerView.visibility = View.GONE
        }
    }

    private fun showMovies() {
        binding.apply {
            movieListRecyclerView.visibility = View.VISIBLE
            noMoviesImage.visibility = View.GONE
            noMoviesText.visibility = View.GONE
        }
    }

    private fun onLikeIconClick(): () -> Unit = {
        Toast.makeText(activity, "ImageTapped", Toast.LENGTH_LONG).show()
    }

    private fun onMoviePromoCardClick(): (Int, View) -> Unit = { movieId, promoCardView ->
        exitTransition = MaterialElevationScale(false).apply {
            duration = ANIMATION_DURATION_MILLIS
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = ANIMATION_DURATION_MILLIS
        }

        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .addSharedElement(promoCardView, MovieDetailsFragment.MOVIE_SCREEN_TRANSITION_KEY)
            .replace(R.id.fragmentContainer, MovieDetailsFragment.newInstance(movieId))
            .addToBackStack(null)
            .commit()
    }

    private fun calculateSpanCount(spanWidthPixels: Int): Int {
        val screenWidthPixels = requireContext().resources.displayMetrics.widthPixels
        return (screenWidthPixels / spanWidthPixels + 0.5).toInt()
    }

    override fun onRetryButtonClick() {
        loadMovies()
    }

    private fun loadMovies() {
        movieListViewModel.loadMovies()
    }

    override fun onCancelButtonClick() {
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        movieAdapter = null
    }
}