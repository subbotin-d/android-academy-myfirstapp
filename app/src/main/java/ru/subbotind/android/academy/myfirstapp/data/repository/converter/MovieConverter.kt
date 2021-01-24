package ru.subbotind.android.academy.myfirstapp.data.repository.converter

import ru.subbotind.android.academy.myfirstapp.data.model.DetailedMovieModel
import ru.subbotind.android.academy.myfirstapp.data.model.MovieModel
import ru.subbotind.android.academy.myfirstapp.domain.entity.Actor
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import javax.inject.Inject

class MovieConverter @Inject constructor() {

    fun toMovie(
        detailedMovieModel: DetailedMovieModel,
        baseImageUrl: String,
        actors: List<Actor>
    ): Movie = Movie(
        id = detailedMovieModel.id,
        title = detailedMovieModel.title,
        overview = detailedMovieModel.overview,
        poster = detailedMovieModel.posterPath?.let { "$baseImageUrl$it" },
        backdrop = detailedMovieModel.backdropPath?.let { "$baseImageUrl$it" },
        ratings = detailedMovieModel.voteAverage,
        numberOfRatings = detailedMovieModel.voteCount,
        minimumAge = if (detailedMovieModel.adult) 16 else 13,
        genres = detailedMovieModel.genres,
        runtime = detailedMovieModel.runtime,
        actors = actors
    )


    fun toMovie(
        movieModel: MovieModel,
        baseImageUrl: String,
        genresMap: Map<Int, Genre>
    ): Movie = Movie(
        id = movieModel.id,
        title = movieModel.title,
        overview = movieModel.overview,
        poster = movieModel.posterPath?.let { "$baseImageUrl$it" },
        backdrop = movieModel.backdropPath?.let { "$baseImageUrl$it" },
        ratings = movieModel.voteAverage,
        numberOfRatings = movieModel.voteCount,
        minimumAge = if (movieModel.adult) 16 else 13,
        genres = movieModel.genreIDS.map {
            genresMap[it] ?: throw IllegalArgumentException("Genre not found")
        }
    )
}