package ru.subbotind.android.academy.myfirstapp.presentation.moviedetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.subbotind.android.academy.myfirstapp.data.network.exception.ServerErrorException
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMovieUseCase
import ru.subbotind.android.academy.myfirstapp.presentation.error.ErrorState
import ru.subbotind.android.academy.myfirstapp.ui.moviedetails.MOVIE_ID_KEY
import ru.subbotind.android.academy.myfirstapp.utils.SingleLiveEvent
import java.io.IOException

class MovieDetailsViewModel @ViewModelInject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var movieId: Int = savedStateHandle.get(MOVIE_ID_KEY) ?: throw IllegalArgumentException(
        "Movie ID should not be null!"
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->
        _movieState.value = MovieDetailsState.LoadingSuccess

        when (throwable) {
            is ServerErrorException ->
                _errorState.value = ErrorState.ServerError(throwable.reason)

            is IOException -> _errorState.value = ErrorState.InternetError

            else -> _errorState.value = ErrorState.UnexpectedError(throwable.message ?: "")
        }
    }

    private val _movieState: MutableLiveData<MovieDetailsState> = MutableLiveData()
    val movieState: LiveData<MovieDetailsState> = _movieState

    private val _errorState: SingleLiveEvent<ErrorState> = SingleLiveEvent()
    val errorState: LiveData<ErrorState> = _errorState

    init {
        loadMovie()
    }

    fun loadMovie() {
        viewModelScope.launch(exceptionHandler) {
            _movieState.value = MovieDetailsState.LoadingStarted
            val movie = getMovieUseCase.getMovie(movieId)
            _movieState.value = MovieDetailsState.LoadingSuccess

            _movieState.value = if (movie.actors.isNullOrEmpty()) {
                MovieDetailsState.MovieWithoutActors(movie)
            } else {
                MovieDetailsState.MovieWithActors(movie)
            }
        }
    }

    sealed class MovieDetailsState {
        object LoadingStarted : MovieDetailsState()
        object LoadingSuccess : MovieDetailsState()
        class MovieWithActors(val movie: Movie) : MovieDetailsState()
        class MovieWithoutActors(val movie: Movie) : MovieDetailsState()
    }
}