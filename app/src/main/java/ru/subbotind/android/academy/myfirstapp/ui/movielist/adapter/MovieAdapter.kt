package ru.subbotind.android.academy.myfirstapp.ui.movielist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.data.Movie
import ru.subbotind.android.academy.myfirstapp.databinding.MovieItemBinding
import ru.subbotind.android.academy.myfirstapp.ui.extensions.setOnDebouncedClickListener

class MovieAdapter(
    private val likeListener: () -> Unit,
    private val cardListener: (Int) -> Unit
) : ListAdapter<DataItem, RecyclerView.ViewHolder>(MovieDiffUtilCallback()) {

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
            is MovieViewHolder -> holder.bind(
                (item as DataItem.MovieItem).movie,
                likeListener,
                cardListener
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.MovieItem -> MOVIE_ITEM_ID
            is DataItem.MovieHeaderItem -> MOVIE_HEADER_ITEM_ID
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
        override val id: Int
            get() = movie.id
    }

    object MovieHeaderItem : DataItem() {
        override val id: Int
            get() = Int.MIN_VALUE
    }

    abstract val id: Int
}

class MovieViewHolder private constructor(
    private val binding: MovieItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding =
                MovieItemBinding.inflate(inflater, parent, false)
            return MovieViewHolder(binding)
        }
    }

    fun bind(movie: Movie, likeListener: () -> Unit, cardListener: (Int) -> Unit) {
        binding.apply {
            moviePromoCard.setOnDebouncedClickListener {
                cardListener(movie.id)
            }

            Glide
                .with(itemView.context)
                .load(movie.poster)
                .centerCrop()
                .placeholder(R.drawable.ic_image_download_placeholder)
                .error(R.drawable.ic_image_download_placeholder)
                .into(movieMainImage)

            likeImage.setImageResource(R.drawable.ic_inactive_like)

            likeImage.setOnDebouncedClickListener {
                likeListener()
            }

            pgRatingText.text = itemView.context.getString(
                R.string.pg_rating,
                movie.minimumAge.toString()
            )

            movieRatingBar.setCurrentRating(movie.ratings / 2)

            movieTotalReviewText.text = itemView.context.getString(
                R.string.total_reviews,
                movie.numberOfRatings.toString()
            )

            movieTagsText.text = movie.genres.joinToString { it.name }

            movieTitle.text = movie.title

            movieDurationText.text = itemView.resources.getString(
                R.string.movie_duration,
                movie.runtime.toString()
            )
        }
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