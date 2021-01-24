package ru.subbotind.android.academy.myfirstapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
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
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.usecase.FetchMoviesUseCase
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMoviesUseCase
import ru.subbotind.android.academy.myfirstapp.presentation.movielist.MovieListViewModel
import ru.subbotind.android.academy.myfirstapp.rule.TestCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<MovieListViewModel.MovieListState>

    @Captor
    private lateinit var progressArgumentCaptor: ArgumentCaptor<Boolean>

    @Mock
    private lateinit var observer: Observer<MovieListViewModel.MovieListState>

    @Mock
    private lateinit var progressObserver: Observer<Boolean>

    private val getMoviesUseCase = mock(GetMoviesUseCase::class.java)
    private val fetchMoviesUseCase = mock(FetchMoviesUseCase::class.java)

    private lateinit var movieListViewModel: MovieListViewModel

    private val movies: List<Movie> = listOf(
        Movie(
            id = 1,
            title = "Avengers",
            ratings = 7.55F,
            numberOfRatings = 500,
            minimumAge = 13,
            genres = listOf(),
            actors = emptyList()
        ),
        Movie(
            id = 2,
            title = "Avengers 2",
            ratings = 8.55F,
            numberOfRatings = 600,
            minimumAge = 16,
            genres = listOf(
                Genre(1, "Action"),
                Genre(2, "Drama")
            ),
            actors = listOf(
                Actor(1, "Jackie Chan", "http://baseurl.com/jackie"),
                Actor(2, "Penelope Cruise")
            )
        ),
    )


    @Test
    fun `WHEN fetched empty movie list EXPECT correct state order`() = runBlockingTest {
        `when`(getMoviesUseCase.getMovies()).thenReturn(flowOf(emptyList()))
        movieListViewModel = MovieListViewModel(getMoviesUseCase, fetchMoviesUseCase)
        movieListViewModel.moviesState.observeForever(observer)
        movieListViewModel.progressState.observeForever(progressObserver)

        movieListViewModel.loadMovies()

        verify(observer).onChanged(argumentCaptor.capture())
        verify(progressObserver, times(3)).onChanged(progressArgumentCaptor.capture())

        val values = argumentCaptor.allValues
        val progressValues = progressArgumentCaptor.allValues

        assertTrue(progressValues[1])
        assertFalse(progressValues[2])
        assertTrue(values[0] is MovieListViewModel.MovieListState.EmptyMovies)
    }

    @Test
    fun `WHEN fetched non empty movie list EXPECT correct state order`() = runBlockingTest {
        `when`(getMoviesUseCase.getMovies()).thenReturn(flowOf(movies))
        movieListViewModel = MovieListViewModel(getMoviesUseCase, fetchMoviesUseCase)
        movieListViewModel.moviesState.observeForever(observer)
        movieListViewModel.progressState.observeForever(progressObserver)

        movieListViewModel.loadMovies()

        verify(observer).onChanged(argumentCaptor.capture())
        verify(progressObserver, times(3)).onChanged(progressArgumentCaptor.capture())

        val values = argumentCaptor.allValues
        val progressValues = progressArgumentCaptor.allValues

        assertTrue(progressValues[1])
        assertFalse(progressValues[2])
        assertTrue((values[0] as MovieListViewModel.MovieListState.Success).movies == movies)
    }

    @Test
    fun `check success content`() = runBlockingTest {
        `when`(getMoviesUseCase.getMovies()).thenReturn(flowOf(movies))
        movieListViewModel = MovieListViewModel(getMoviesUseCase, fetchMoviesUseCase)
        movieListViewModel.moviesState.observeForever(observer)

        verify(observer).onChanged(argumentCaptor.capture())

        val values = argumentCaptor.allValues

        assertTrue((values[0] as MovieListViewModel.MovieListState.Success).movies == movies)
    }
}