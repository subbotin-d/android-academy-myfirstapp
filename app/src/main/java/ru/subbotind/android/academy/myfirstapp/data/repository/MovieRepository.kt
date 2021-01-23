package ru.subbotind.android.academy.myfirstapp.data.repository

import ru.subbotind.android.academy.myfirstapp.data.datasource.GenreRemoteDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.MovieRemoteDataSource
import ru.subbotind.android.academy.myfirstapp.data.model.DetailedMovieModel
import ru.subbotind.android.academy.myfirstapp.data.model.MovieModel
import ru.subbotind.android.academy.myfirstapp.domain.entity.Actor
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val baseImageUrlRepository: BaseImageUrlRepository,
    private val genresDataSource: GenreRemoteDataSource
) : MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        val movieResults: List<MovieModel> = movieRemoteDataSource.getMovies()
        val baseImageUrl: String = baseImageUrlRepository.getBaseUrl()
        val genresMap: Map<Int, Genre> = genresDataSource.getGenres().associateBy { it.id }

        return movieResults.map { movieModel ->
            Movie(
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
    }

    override suspend fun getMovie(movieId: Int): Movie {
        val movieModel: DetailedMovieModel = movieRemoteDataSource.getMovie(movieId)
        val baseImageUrl: String = baseImageUrlRepository.getBaseUrl()

        val actors: List<Actor> = getMovieActors(movieId)
        val actorsWithCorrectImageUrl: List<Actor> = updateActorImageUrls(actors, baseImageUrl)

        return Movie(
            id = movieModel.id,
            title = movieModel.title,
            overview = movieModel.overview,
            poster = movieModel.posterPath?.let { "$baseImageUrl$it" },
            backdrop = movieModel.backdropPath?.let { "$baseImageUrl$it" },
            ratings = movieModel.voteAverage,
            numberOfRatings = movieModel.voteCount,
            minimumAge = if (movieModel.adult) 16 else 13,
            genres = movieModel.genres,
            runtime = movieModel.runtime,
            actors = actorsWithCorrectImageUrl
        )
    }

    private suspend fun getMovieActors(movieId: Int): List<Actor> =
        movieRemoteDataSource.getMovieActors(movieId).cast

    private fun updateActorImageUrls(actors: List<Actor>, baseImageUrl: String): List<Actor> =
        actors.map { actor ->
            actor.copy(
                picture = actor.picture?.let { "$baseImageUrl${it}" }
            )
        }
}