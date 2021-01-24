package ru.subbotind.android.academy.myfirstapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertFalse
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
import ru.subbotind.android.academy.myfirstapp.domain.usecase.FetchMovieUseCase
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMovieUseCase
import ru.subbotind.android.academy.myfirstapp.presentation.moviedetails.MovieDetailsViewModel
import ru.subbotind.android.academy.myfirstapp.rule.TestCoroutineRule
import ru.subbotind.android.academy.myfirstapp.ui.moviedetails.MOVIE_ID_KEY

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<MovieDetailsViewModel.MovieDetailsState>

    @Captor
    private lateinit var progressArgumentCaptor: ArgumentCaptor<Boolean>

    @Mock
    private lateinit var observer: Observer<MovieDetailsViewModel.MovieDetailsState>

    @Mock
    private lateinit var progressObserver: Observer<Boolean>

    private val savedStateHandle: SavedStateHandle = SavedStateHandle(mapOf(MOVIE_ID_KEY to 1))
    private val getMovieUseCase = mock(GetMovieUseCase::class.java)
    private val fetchMovieUseCase = mock(FetchMovieUseCase::class.java)
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

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
    fun `check progress bar state order`() = runBlockingTest {
        movieDetailsViewModel =
            MovieDetailsViewModel(getMovieUseCase, fetchMovieUseCase, savedStateHandle)
        movieDetailsViewModel.progressState.observeForever(progressObserver)

        movieDetailsViewModel.loadMovie()

        verify(progressObserver, times(3)).onChanged(progressArgumentCaptor.capture())

        val progressValues = progressArgumentCaptor.allValues

        assertTrue(progressValues[1])
        assertFalse(progressValues[2])
    }

    @Test
    fun `check MovieWithActors content`() = runBlockingTest {
        `when`(getMovieUseCase.getMovie(1)).thenReturn(flowOf(movieWithActors))
        movieDetailsViewModel =
            MovieDetailsViewModel(getMovieUseCase, fetchMovieUseCase, savedStateHandle)

        movieDetailsViewModel.movieState.observeForever(observer)
        movieDetailsViewModel.loadMovie()

        verify(observer).onChanged(argumentCaptor.capture())

        val values = argumentCaptor.allValues

        assertTrue((values[0] as MovieDetailsViewModel.MovieDetailsState.MovieWithActors).movie == movieWithActors)
    }

    @Test
    fun `check MovieWithoutActors content`() = coroutineTestRule.dispatcher.runBlockingTest {
        `when`(getMovieUseCase.getMovie(1)).thenReturn(flowOf(movieWithoutActors))
        movieDetailsViewModel =
            MovieDetailsViewModel(getMovieUseCase, fetchMovieUseCase, savedStateHandle)

        movieDetailsViewModel.movieState.observeForever(observer)
        movieDetailsViewModel.loadMovie()

        verify(observer).onChanged(argumentCaptor.capture())

        val values = argumentCaptor.allValues

        assertTrue((values[0] as MovieDetailsViewModel.MovieDetailsState.MovieWithoutActors).movie == movieWithoutActors)
    }
}