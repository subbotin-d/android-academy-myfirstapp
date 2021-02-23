package ru.subbotind.android.academy.myfirstapp.ui.movielist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.MovieItemBinding
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.ui.extensions.setOnDebouncedClickListener

class MovieAdapter(
    private val likeListener: () -> Unit,
    private val cardListener: (Int, View) -> Unit
) : ListAdapter<DataItem, RecyclerView.ViewHolder>(MovieDiffUtilCallback()) {

    companion object {
        const val MOVIE_HEADER_ITEM_ID = 0
        const val MOVIE_ITEM_ID = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            MOVIE_ITEM_ID -> MovieViewHolder.from(parent, cardListener, likeListener)
            MOVIE_HEADER_ITEM_ID -> MovieHeaderViewHolder.from(parent)
            else -> throw NoSuchElementException("Adapter does not know this view type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MovieViewHolder -> holder.bind((item as DataItem.MovieItem).movie)
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
    private val binding: MovieItemBinding,
    cardListener: (Int, View) -> Unit,
    likeListener: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(
            parent: ViewGroup,
            cardListener: (Int, View) -> Unit,
            likeListener: () -> Unit
        ): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding =
                MovieItemBinding.inflate(inflater, parent, false)
            return MovieViewHolder(binding, cardListener, likeListener)
        }
    }

    private var movieId: Int? = null

    init {
        binding.moviePromoCard.setOnDebouncedClickListener { promoCardView ->
            movieId?.let { id ->
                cardListener(id, promoCardView)
            }
        }

        binding.likeImage.setOnDebouncedClickListener {
            likeListener()
        }
    }

    fun bind(movie: Movie) {
        this.movieId = movie.id

        ViewCompat.setTransitionName(binding.moviePromoCard, "MOVIE_WITH_ID_${movie.id}")

        binding.apply {
            Glide
                .with(itemView.context)
                .load(movie.poster)
                .centerCrop()
                .placeholder(R.drawable.ic_image_download_placeholder)
                .error(R.drawable.ic_image_download_placeholder)
                .into(movieMainImage)

            likeImage.setImageResource(R.drawable.ic_inactive_like)

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