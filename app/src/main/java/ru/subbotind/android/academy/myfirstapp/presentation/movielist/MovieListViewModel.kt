package ru.subbotind.android.academy.myfirstapp.presentation.movielist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.subbotind.android.academy.myfirstapp.data.network.exception.ServerErrorException
import ru.subbotind.android.academy.myfirstapp.data.network.exception.UnexpectedServerErrorException
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMoviesUseCase
import ru.subbotind.android.academy.myfirstapp.presentation.error.ErrorState
import ru.subbotind.android.academy.myfirstapp.utils.SingleLiveEvent
import java.io.IOException

class MovieListViewModel @ViewModelInject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->
        _moviesState.value = MovieListState.LoadingSuccess

        when (throwable) {
            is ServerErrorException ->
                _errorState.value = ErrorState.ServerError(throwable.reason)

            is UnexpectedServerErrorException -> _errorState.value =
                ErrorState.UnexpectedError(throwable.message ?: "")

            is IOException -> _errorState.value = ErrorState.InternetError

            else -> _errorState.value = ErrorState.UnexpectedError(throwable.message ?: "")
        }
    }

    private val _moviesState: MutableLiveData<MovieListState> = MutableLiveData()
    val moviesState: LiveData<MovieListState> = _moviesState

    private val _errorState: SingleLiveEvent<ErrorState> = SingleLiveEvent()
    val errorState: LiveData<ErrorState> = _errorState

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch(exceptionHandler) {
            _moviesState.value = MovieListState.LoadingStarted
            val movieList = getMoviesUseCase.getMovies()
            _moviesState.value = MovieListState.LoadingSuccess

            if (movieList.isEmpty()) {
                _moviesState.value = MovieListState.EmptyMovies
            } else {
                _moviesState.value = MovieListState.Success(movieList)
            }
        }
    }

    sealed class MovieListState {
        object EmptyMovies : MovieListState()
        object LoadingStarted : MovieListState()
        object LoadingSuccess : MovieListState()
        class Success(val movies: List<Movie>) : MovieListState()
    }
}