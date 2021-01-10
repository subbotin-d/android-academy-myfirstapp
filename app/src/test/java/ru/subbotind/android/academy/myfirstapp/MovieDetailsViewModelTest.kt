package ru.subbotind.android.academy.myfirstapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.subbotind.android.academy.myfirstapp.domain.entity.Actor
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMovieUseCase
import ru.subbotind.android.academy.myfirstapp.presentation.moviedetails.MovieDetailsViewModel
import ru.subbotind.android.academy.myfirstapp.rule.TestCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<MovieDetailsViewModel.MovieDetailsState>

    @Mock
    private lateinit var observer: Observer<MovieDetailsViewModel.MovieDetailsState>

    private val getMovieUseCase = mock(GetMovieUseCase::class.java)
    private val movieDetailsViewModel = MovieDetailsViewModel(getMovieUseCase)

    private val actors: List<Actor> = listOf(
        Actor(1, "Jackie Chan", "http://baseurl.com/jackie"),
        Actor(2, "Penelope Cruise"),
    )

    private val movieWithoutActors: Movie = Movie(
        id = 1,
        title = "Avengers",
        ratings = 7.55F,
        numberOfRatings = 500,
        minimumAge = 13,
        genres = listOf(),
        actors = emptyList()
    )

    private val movieWithActors: Movie = Movie(
        id = 1,
        title = "Avengers",
        ratings = 7.55F,
        numberOfRatings = 500,
        minimumAge = 13,
        genres = listOf(),
        actors = actors
    )

    @Test
    fun `WHEN movie without actors fetched EXPECT correct state order`() = runBlockingTest {
        `when`(getMovieUseCase.getMovie(1)).thenReturn(movieWithoutActors)

        movieDetailsViewModel.movieState.observeForever(observer)
        movieDetailsViewModel.loadMovie(1)

        verify(observer, times(3)).onChanged(argumentCaptor.capture())

        val values = argumentCaptor.allValues

        assertTrue(values[0] is MovieDetailsViewModel.MovieDetailsState.LoadingStarted)
        assertTrue(values[1] is MovieDetailsViewModel.MovieDetailsState.LoadingSuccess)
        assertTrue(values[2] is MovieDetailsViewModel.MovieDetailsState.MovieWithoutActors)
    }

    @Test
    fun `WHEN movie with actors fetched EXPECT correct state order`() = runBlockingTest {
        `when`(getMovieUseCase.getMovie(1)).thenReturn(movieWithActors)

        movieDetailsViewModel.movieState.observeForever(observer)
        movieDetailsViewModel.loadMovie(1)

        verify(observer, times(3)).onChanged(argumentCaptor.capture())

        val values = argumentCaptor.allValues

        assertTrue(values[0] is MovieDetailsViewModel.MovieDetailsState.LoadingStarted)
        assertTrue(values[1] is MovieDetailsViewModel.MovieDetailsState.LoadingSuccess)
        assertTrue(values[2] is MovieDetailsViewModel.MovieDetailsState.MovieWithActors)
    }

    @Test
    fun `check MovieWithActors content`() = runBlockingTest {
        `when`(getMovieUseCase.getMovie(1)).thenReturn(movieWithActors)

        movieDetailsViewModel.movieState.observeForever(observer)
        movieDetailsViewModel.loadMovie(1)

        verify(observer, times(3)).onChanged(argumentCaptor.capture())

        val values = argumentCaptor.allValues

        assertTrue((values[2] as MovieDetailsViewModel.MovieDetailsState.MovieWithActors).movie == movieWithActors)
    }

    @Test
    fun `check MovieWithoutActors content`() = runBlockingTest {
        `when`(getMovieUseCase.getMovie(1)).thenReturn(movieWithoutActors)

        movieDetailsViewModel.movieState.observeForever(observer)
        movieDetailsViewModel.loadMovie(1)

        verify(observer, times(3)).onChanged(argumentCaptor.capture())

        val values = argumentCaptor.allValues

        assertTrue((values[2] as MovieDetailsViewModel.MovieDetailsState.MovieWithoutActors).movie == movieWithoutActors)
    }
}