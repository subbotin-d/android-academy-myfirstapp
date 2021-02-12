package ru.subbotind.android.academy.myfirstapp

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetNewMovieUseCase
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetNewMovieUseCaseImpl

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetNewMovieUseCaseTest {

    private val movieRepository: MovieRepository = Mockito.mock(MovieRepository::class.java)
    private val getNewMovieUseCase: GetNewMovieUseCase = GetNewMovieUseCaseImpl(movieRepository)

    private val baseUrl = "http://movie.org"

    private val genreList: List<Genre> = listOf(
        Genre(id = 1, "Action"),
        Genre(id = 2, "Drama"),
        Genre(id = 3, "Comedy")
    )

    private val movieListWithNotAdultRating: List<Movie> = listOf(
        Movie(
            id = 1,
            overview = "Avengers can do it",
            title = "Avengers",
            ratings = 7.55F,
            numberOfRatings = 500,
            minimumAge = 13,
            genres = listOf()
        ),
        Movie(
            id = 2,
            overview = "Avengers backs",
            title = "Avengers 2",
            ratings = 7.65F,
            numberOfRatings = 500,
            minimumAge = 13,
            genres = listOf()
        )
    )

    @Test
    fun `WHEN list has more than one item EXPECT sorting by rating`() =
        runBlockingTest {
            Mockito.`when`(movieRepository.getNewMovies())
                .thenReturn(flow { emit(movieListWithNotAdultRating) })

            val expectedMovie = Movie(
                id = 2,
                overview = "Avengers backs",
                title = "Avengers 2",
                ratings = 7.65F,
                numberOfRatings = 500,
                minimumAge = 13,
                genres = listOf()
            )

            val actualMovie = getNewMovieUseCase.getNewMovie().first()

            assertEquals("movie objects are not equals", actualMovie, expectedMovie)
        }

    @Test
    fun `WHEN list has no items EXPECT flow with null`() =
        runBlockingTest {
            Mockito.`when`(movieRepository.getNewMovies())
                .thenReturn(flow { emit(emptyList<Movie>()) })

            val actualMovie = getNewMovieUseCase.getNewMovie().first()

            assertNull(actualMovie)
        }

    @Test
    fun `WHEN list has only one item EXPECT flow with that item`() =
        runBlockingTest {
            Mockito.`when`(movieRepository.getNewMovies())
                .thenReturn(flow { emit(movieListWithNotAdultRating.subList(0, 1)) })

            val expectedMovie = Movie(
                id = 1,
                overview = "Avengers can do it",
                title = "Avengers",
                ratings = 7.55F,
                numberOfRatings = 500,
                minimumAge = 13,
                genres = listOf()
            )

            val actualMovie = getNewMovieUseCase.getNewMovie().first()

            assertEquals("movie objects are not equals", actualMovie, expectedMovie)
        }
}