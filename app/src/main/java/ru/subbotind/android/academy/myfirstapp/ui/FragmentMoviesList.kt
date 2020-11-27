package ru.subbotind.android.academy.myfirstapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.data.DataContainer
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentMoviesListBinding
import ru.subbotind.android.academy.myfirstapp.extension.calculateSpanCount
import ru.subbotind.android.academy.myfirstapp.ui.adapter.MovieAdapter
import ru.subbotind.android.academy.myfirstapp.ui.adapter.MovieItemListener


class FragmentMoviesList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMoviesListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movies_list, container, false)

        val movieList = DataContainer.getAllMovies()

        val likeClickListener: () -> Unit = {
            Toast.makeText(activity, "ImageTapped", Toast.LENGTH_LONG).show()
        }

        val cardClickListener: (Long) -> Unit = { movieId ->
            fragmentManager?.beginTransaction()
                ?.addToBackStack(null)
                ?.add(R.id.fragmentContainer, FragmentMoviesDetails.newInstance(movieId))
                ?.commit()
        }

        val movieAdapter = MovieAdapter(
            MovieItemListener(likeClickListener, cardClickListener)
        )

        movieAdapter.submitData(movieList)

        val spanCount =
            requireContext().calculateSpanCount(resources.getDimensionPixelSize(R.dimen.card_view_max_width))

        val gridLayoutManager = GridLayoutManager(activity, spanCount)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (position) {
                0 -> spanCount
                else -> 1
            }
        }

        binding.rvMoviesList.apply {
            layoutManager = gridLayoutManager
            adapter = movieAdapter
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()

        const val TAG = "fragmentMoviesList"
    }
}

