package ru.subbotind.android.academy.myfirstapp.data.repository.converter

import ru.subbotind.android.academy.myfirstapp.data.entity.MovieEntity
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import javax.inject.Inject

class MovieEntityConverter @Inject constructor() {

    fun fromMovieEntity(movieEntity: MovieEntity): Movie = Movie(
        id = movieEntity.id,
        title = movieEntity.title,
        overview = movieEntity.overview,
        poster = movieEntity.posterPath,
        backdrop = movieEntity.backdropPath,
        ratings = movieEntity.voteAverage,
        numberOfRatings = movieEntity.voteCount,
        minimumAge = movieEntity.adult,
        genres = movieEntity.genres,
        runtime = movieEntity.runtime,
        actors = movieEntity.actors,
    )

    fun toMovieEntity(movie: Movie): MovieEntity = MovieEntity(
        id = movie.id,
        title = movie.title,
        overview = movie.overview,
        backdropPath = movie.backdrop,
        posterPath = movie.poster,
        adult = movie.minimumAge,
        genres = movie.genres,
        runtime = movie.runtime,
        voteAverage = movie.ratings,
        voteCount = movie.numberOfRatings,
        actors = movie.actors,
    )
}