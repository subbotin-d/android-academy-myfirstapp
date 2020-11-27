package ru.subbotind.android.academy.myfirstapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.data.Movie
import ru.subbotind.android.academy.myfirstapp.databinding.MovieItemBinding

class MovieAdapter(private val movieListener: MovieItemListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(MovieCallBack()) {

    companion object {
        const val MOVIE_HEADER_ITEM_ID = 0
        const val MOVIE_ITEM_ID = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            MOVIE_ITEM_ID -> MovieViewHolder.from(parent)
            MOVIE_HEADER_ITEM_ID -> MovieHeaderViewHolder.from(parent)
            else -> throw NoSuchElementException("Adapter does not know this view type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MovieViewHolder -> holder.bind((item as DataItem.MovieItem).movie, movieListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.MovieItem -> MOVIE_ITEM_ID
            is DataItem.MovieHeaderItem -> MOVIE_HEADER_ITEM_ID
        }
    }

    class MovieViewHolder private constructor(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding =
                    MovieItemBinding.inflate(inflater, parent, false)
                return MovieViewHolder(binding)
            }
        }

        fun bind(movie: Movie, movieListener: MovieItemListener) {
            binding.movie = movie
            binding.movieListener = movieListener
            binding.executePendingBindings()
        }
    }

    class MovieHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun from(parent: ViewGroup): MovieHeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.movie_list_header_item, parent, false)
                return MovieHeaderViewHolder(view)
            }
        }
    }

    fun submitData(movieList: List<Movie>?) {
        val resultList = when (movieList) {
            null -> listOf(DataItem.MovieHeaderItem)
            else -> listOf(DataItem.MovieHeaderItem) + movieList.map { DataItem.MovieItem(it) }
        }
        submitList(resultList)
    }

}

sealed class DataItem {
    data class MovieItem(val movie: Movie) : DataItem() {
        override val id: Long
            get() = movie.id

    }

    object MovieHeaderItem : DataItem() {
        override val id: Long
            get() = Long.MIN_VALUE
    }

    abstract val id: Long
}