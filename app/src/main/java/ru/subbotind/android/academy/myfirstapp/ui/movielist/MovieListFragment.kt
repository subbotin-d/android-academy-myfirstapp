package ru.subbotind.android.academy.myfirstapp.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentMoviesListBinding
import ru.subbotind.android.academy.myfirstapp.presentation.movielist.MovieListViewModel
import ru.subbotind.android.academy.myfirstapp.presentation.movielist.MovieListViewModel.MovieListState
import ru.subbotind.android.academy.myfirstapp.ui.moviedetails.MovieDetailsFragment
import ru.subbotind.android.academy.myfirstapp.ui.movielist.adapter.MovieAdapter

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    companion object {
        fun newInstance() = MovieListFragment()

        const val TAG = "moviesListFragment"
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

        setUpRecycler()

        movieListViewModel.moviesState.observe(viewLifecycleOwner, ::render)

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
            MovieListState.LoadingStarted -> setLoading(true)
            MovieListState.LoadingSuccess -> setLoading(false)
            is MovieListState.Success -> {
                showMovies()
                movieAdapter?.submitData(state.movies)
            }
        }
    }

    private fun setLoading(inProgress: Boolean) {
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

    private fun onMoviePromoCardClick(): (Int) -> Unit = { movieId ->
        fragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.add(R.id.fragmentContainer, MovieDetailsFragment.newInstance(movieId))
            ?.commit()
    }

    private fun calculateSpanCount(spanWidthPixels: Int): Int {
        val screenWidthPixels = requireContext().resources.displayMetrics.widthPixels
        return (screenWidthPixels / spanWidthPixels + 0.5).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        movieAdapter = null
    }
}