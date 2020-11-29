package ru.subbotind.android.academy.myfirstapp.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.data.DataContainer
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentMoviesListBinding
import ru.subbotind.android.academy.myfirstapp.ui.moviedetails.MovieDetailsFragment
import ru.subbotind.android.academy.myfirstapp.ui.movielist.adapter.MovieAdapter


class MovieListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentMoviesListBinding.inflate(inflater, container, false)

        val movieList = DataContainer.getAllMovies()

        val movieAdapter = MovieAdapter(
            likeListener = onLikeIconClick(),
            cardListener = onMoviePromoCardClick()
        )

        movieAdapter.submitData(movieList)

        val spanCount =
            calculateSpanCount(resources.getDimensionPixelSize(R.dimen.card_view_max_width))

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

        return binding.root
    }

    private fun onLikeIconClick(): () -> Unit = {
        Toast.makeText(activity, "ImageTapped", Toast.LENGTH_LONG).show()
    }

    private fun onMoviePromoCardClick(): (Long) -> Unit = { movieId ->
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
    }

    companion object {
        fun newInstance() = MovieListFragment()

        const val TAG = "moviesListFragment"
    }
}

