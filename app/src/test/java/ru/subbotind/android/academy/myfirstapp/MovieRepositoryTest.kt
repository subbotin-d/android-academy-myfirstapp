package ru.subbotind.android.academy.myfirstapp

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import ru.subbotind.android.academy.myfirstapp.data.datasource.GenreRemoteDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.MovieRemoteDataSource
import ru.subbotind.android.academy.myfirstapp.data.model.DetailedMovieModel
import ru.subbotind.android.academy.myfirstapp.data.model.MovieCreditsModel
import ru.subbotind.android.academy.myfirstapp.data.model.MovieModel
import ru.subbotind.android.academy.myfirstapp.data.repository.BaseImageUrlRepository
import ru.subbotind.android.academy.myfirstapp.data.repository.MovieRepositoryImpl
import ru.subbotind.android.academy.myfirstapp.domain.entity.Actor
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    private val baseImageUrlRepository: BaseImageUrlRepository =
        mock(BaseImageUrlRepository::class.java)

    private val movieRemoteDataSource: MovieRemoteDataSource =
        mock(MovieRemoteDataSource::class.java)

    private val genresRemoteDataSource: GenreRemoteDataSource =
        mock(GenreRemoteDataSource::class.java)

    private val movieRepository = MovieRepositoryImpl(
        movieRemoteDataSource,
        baseImageUrlRepository,
        genresRemoteDataSource
    )

    private val baseUrl = "http://movie.org"

    private val genreList: List<Genre> = listOf(
        Genre(id = 1, "Action"),
        Genre(id = 2, "Drama"),
        Genre(id = 3, "Comedy")
    )

    private val movieModelListWithMaximumInfo: List<MovieModel> = listOf(
        MovieModel(
            id = 1,
            title = "Avengers",
            overview = "Avengers can do it",
            adult = true,
            backdropPath = "/hi",
            genreIDS = listOf(1, 2),
            popularity = 2.50,
            posterPath = "/poster/hi",
            voteAverage = 7.55F,
            voteCount = 500
        )
    )

    private val movieModelListWithWithMinimumInfo: List<MovieModel> = listOf(
        MovieModel(
            id = 1,
            title = "Avengers",
            adult = true,
            overview = "Avengers can do it",
            genreIDS = listOf(),
            popularity = 2.50,
            voteAverage = 7.55F,
            voteCount = 500
        )
    )

    private val movieModelListWithUnexpectedGenreId: List<MovieModel> = listOf(
        MovieModel(
            id = 1,
            title = "Avengers",
            adult = true,
            overview = "Avengers can do it",
            genreIDS = listOf(1, 2, 5),
            popularity = 2.50,
            voteAverage = 7.55F,
            voteCount = 500
        )
    )

    private val movieModelListWithNotAdultRating: List<MovieModel> = listOf(
        MovieModel(
            id = 1,
            title = "Avengers",
            adult = false,
            overview = "Avengers can do it",
            genreIDS = listOf(),
            popularity = 2.50,
            voteAverage = 7.55F,
            voteCount = 500
        )
    )

    private val detailedMovieModelWithMinimumInfo: DetailedMovieModel =
        DetailedMovieModel(
            id = 1,
            title = "Avengers",
            adult = false,
            genres = listOf(),
            popularity = 2.50F,
            voteAverage = 7.55F,
            voteCount = 500
        )

    private val detailedMovieModelWithMaximumInfo: DetailedMovieModel =
        DetailedMovieModel(
            id = 1,
            title = "Avengers",
            adult = true,
            overview = "Avengers can do it",
            genres = genreList,
            popularity = 2.50F,
            voteAverage = 7.55F,
            voteCount = 500,
            backdropPath = "/backdrop/path",
            posterPath = "/poster/path",
            runtime = 100,
            tagline = "Tag1, Tag2"
        )

    private val actors: List<Actor> = listOf(
        Actor(1, "Johny Kish"),
        Actor(2, "Orlando Bloom", "/picture/orlando")
    )

    private val movieCreditsModelWithActors: MovieCreditsModel = MovieCreditsModel(
        id = 1L,
        cast = actors
    )

    private val movieCreditsModelWithoutActors = MovieCreditsModel(
        id = 1,
        cast = emptyList()
    )

    @Test
    fun `WHEN list of movie model has poster and backdrop EXPECT correct url into movie object`() =
        runBlockingTest {
            `when`(baseImageUrlRepository.getBaseUrl()).thenReturn(baseUrl)
            `when`(genresRemoteDataSource.getGenres()).thenReturn(genreList)
            `when`(movieRemoteDataSource.getMovies()).thenReturn(movieModelListWithMaximumInfo)

            val actualMovieList = movieRepository.getMovies()

            val expectedMovieList = listOf(
                Movie(
                    id = 1,
                    overview = "Avengers can do it",
                    title = "Avengers",
                    poster = "http://movie.org/poster/hi",
                    backdrop = "http://movie.org/hi",
                    ratings = 7.55F,
                    numberOfRatings = 500,
                    minimumAge = 16,
                    genres = genreList.subList(0, 2)
                )
            )

            assertEquals(expectedMovieList, actualMovieList)
        }

    @Test
    fun `WHEN list of movie model has minimum info EXPECT success convertation into movie object`() =
        runBlockingTest {
            `when`(baseImageUrlRepository.getBaseUrl()).thenReturn(baseUrl)
            `when`(genresRemoteDataSource.getGenres()).thenReturn(genreList)
            `when`(movieRemoteDataSource.getMovies()).thenReturn(movieModelListWithWithMinimumInfo)

            val actualMovieList = movieRepository.getMovies()

            val expectedMovieList = listOf(
                Movie(
                    id = 1,
                    overview = "Avengers can do it",
                    title = "Avengers",
                    ratings = 7.55F,
                    numberOfRatings = 500,
                    minimumAge = 16,
                    genres = listOf()
                )
            )

            assertEquals(expectedMovieList, actualMovieList)
        }

    @Test(expected = IllegalArgumentException::class)
    fun `WHEN there is no genre with id EXPECT throw exception`() = runBlockingTest {
        `when`(baseImageUrlRepository.getBaseUrl()).thenReturn(baseUrl)
        `when`(genresRemoteDataSource.getGenres()).thenReturn(genreList)
        `when`(movieRemoteDataSource.getMovies()).thenReturn(movieModelListWithUnexpectedGenreId)

        movieRepository.getMovies()
    }

    @Test
    fun `WHEN list of movie model has not adult EXPECT movie with 13 pg rating`() =
        runBlockingTest {
            `when`(baseImageUrlRepository.getBaseUrl()).thenReturn(baseUrl)
            `when`(genresRemoteDataSource.getGenres()).thenReturn(genreList)
            `when`(movieRemoteDataSource.getMovies()).thenReturn(movieModelListWithNotAdultRating)

            val actualMovieList = movieRepository.getMovies()

            val expectedMovieList = listOf(
                Movie(
                    id = 1,
                    overview = "Avengers can do it",
                    title = "Avengers",
                    ratings = 7.55F,
                    numberOfRatings = 500,
                    minimumAge = 13,
                    genres = listOf()
                )
            )

            assertEquals(expectedMovieList, actualMovieList)
        }

    @Test
    fun `WHEN movie model has not adult EXPECT movie with 13 pg rating`() =
        runBlockingTest {
            `when`(baseImageUrlRepository.getBaseUrl()).thenReturn(baseUrl)
            `when`(genresRemoteDataSource.getGenres()).thenReturn(genreList)
            `when`(movieRemoteDataSource.getMovies()).thenReturn(movieModelListWithNotAdultRating)

            val actualMovieList = movieRepository.getMovies()

            val expectedMovieList = listOf(
                Movie(
                    id = 1,
                    overview = "Avengers can do it",
                    title = "Avengers",
                    ratings = 7.55F,
                    numberOfRatings = 500,
                    minimumAge = 13,
                    genres = listOf()
                )
            )

            assertEquals(expectedMovieList, actualMovieList)
        }

    @Test
    fun `WHEN actor has no picture EXPECT movie object with actor`() =
        runBlockingTest {
            `when`(baseImageUrlRepository.getBaseUrl()).thenReturn(baseUrl)
            `when`(movieRemoteDataSource.getMovie(1)).thenReturn(
                detailedMovieModelWithMinimumInfo
            )
            `when`(movieRemoteDataSource.getMovieActors(1)).thenReturn(movieCreditsModelWithActors)

            val actualMovie = movieRepository.getMovie(1)

            val expectedActorsList: List<Actor> = listOf(
                Actor(1, "Johny Kish"),
                Actor(2, "Orlando Bloom", "http://movie.org/picture/orlando")
            )

            val expectedMovie = Movie(
                id = 1,
                title = "Avengers",
                ratings = 7.55F,
                numberOfRatings = 500,
                minimumAge = 13,
                genres = listOf(),
                actors = expectedActorsList
            )

            assertEquals(expectedMovie, actualMovie)
        }

    @Test
    fun `WHEN actor list is empty EXPECT movie without actors`() =
        runBlockingTest {
            `when`(baseImageUrlRepository.getBaseUrl()).thenReturn(baseUrl)
            `when`(movieRemoteDataSource.getMovie(1)).thenReturn(
                detailedMovieModelWithMinimumInfo
            )
            `when`(movieRemoteDataSource.getMovieActors(1)).thenReturn(
                movieCreditsModelWithoutActors
            )

            val actualMovie = movieRepository.getMovie(1)

            val expectedMovie = Movie(
                id = 1,
                title = "Avengers",
                ratings = 7.55F,
                numberOfRatings = 500,
                minimumAge = 13,
                genres = listOf(),
                actors = emptyList()
            )

            assertEquals(expectedMovie, actualMovie)
        }

    @Test
    fun `WHEN movie is adult and has images EXPECT movie with 16 pg rating and correct urls`() =
        runBlockingTest {
            `when`(baseImageUrlRepository.getBaseUrl()).thenReturn(baseUrl)
            `when`(movieRemoteDataSource.getMovie(1)).thenReturn(
                detailedMovieModelWithMaximumInfo
            )
            `when`(movieRemoteDataSource.getMovieActors(1)).thenReturn(
                movieCreditsModelWithoutActors
            )

            val actualMovie = movieRepository.getMovie(1)

            val expectedMovie = Movie(
                id = 1,
                title = "Avengers",
                overview = "Avengers can do it",
                backdrop = "http://movie.org/backdrop/path",
                poster = "http://movie.org/poster/path",
                minimumAge = 16,
                ratings = 7.55F,
                numberOfRatings = 500,
                genres = genreList,
                actors = emptyList(),
                runtime = 100
            )

            assertEquals(expectedMovie, actualMovie)
        }
}