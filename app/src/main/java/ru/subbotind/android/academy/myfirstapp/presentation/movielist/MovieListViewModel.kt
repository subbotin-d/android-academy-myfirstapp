package ru.subbotind.android.academy.myfirstapp.presentation.movielist

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.subbotind.android.academy.myfirstapp.data.network.exception.ServerErrorException
import ru.subbotind.android.academy.myfirstapp.data.network.exception.UnexpectedServerErrorException
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.usecase.FetchMoviesUseCase
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMoviesUseCase
import ru.subbotind.android.academy.myfirstapp.presentation.error.ErrorState
import ru.subbotind.android.academy.myfirstapp.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val fetchMoviesUseCase: FetchMoviesUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->
        _progressState.value = false

        when (throwable) {
            is ServerErrorException ->
                _errorState.value = ErrorState.ServerError(throwable.reason)

            is UnexpectedServerErrorException -> _errorState.value =
                ErrorState.UnexpectedError(throwable.message ?: "")

            else -> _errorState.value = ErrorState.UnexpectedError(throwable.message ?: "")
        }
    }

    val moviesState: LiveData<MovieListState>
        get() = getMoviesUseCase.getMovies().asLiveData().map { movies ->
            if (movies.isEmpty()) {
                MovieListState.EmptyMovies
            } else {
                MovieListState.Success(movies)
            }
        }

    private val _progressState: MutableLiveData<Boolean> = MutableLiveData()
    val progressState: LiveData<Boolean> = _progressState

    private val _errorState: SingleLiveEvent<ErrorState> = SingleLiveEvent()
    val errorState: LiveData<ErrorState> = _errorState

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch(exceptionHandler) {
            _progressState.value = true
            fetchMoviesUseCase.fetchMovies()
            _progressState.value = false
        }
    }

    sealed class MovieListState {
        object EmptyMovies : MovieListState()
        class Success(val movies: List<Movie>) : MovieListState()
    }
}