package ru.subbotind.android.academy.myfirstapp.presentation.moviedetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.subbotind.android.academy.myfirstapp.data.network.exception.ServerErrorException
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.usecase.FetchMovieUseCase
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMovieUseCase
import ru.subbotind.android.academy.myfirstapp.presentation.error.ErrorState
import ru.subbotind.android.academy.myfirstapp.ui.moviedetails.MOVIE_ID_KEY
import ru.subbotind.android.academy.myfirstapp.utils.SingleLiveEvent

class MovieDetailsViewModel @ViewModelInject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val fetchMovieUseCase: FetchMovieUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var movieId: Int = savedStateHandle.get(MOVIE_ID_KEY) ?: throw IllegalArgumentException(
        "Movie ID should not be null!"
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->
        _progressState.value = false

        when (throwable) {
            is ServerErrorException ->
                _errorState.value = ErrorState.ServerError(throwable.reason)

            else -> _errorState.value = ErrorState.UnexpectedError(throwable.message ?: "")
        }
    }

    val movieState: LiveData<MovieDetailsState>
        get() = getMovieUseCase.getMovie(movieId).asLiveData().map { movie ->
            if (movie.actors.isNullOrEmpty()) {
                MovieDetailsState.MovieWithoutActors(movie)
            } else {
                MovieDetailsState.MovieWithActors(movie)
            }
        }

    private val _progressState: MutableLiveData<Boolean> = MutableLiveData()
    val progressState: LiveData<Boolean> = _progressState

    private val _errorState: SingleLiveEvent<ErrorState> = SingleLiveEvent()
    val errorState: LiveData<ErrorState> = _errorState

    init {
        loadMovie()
    }

    fun loadMovie() {
        viewModelScope.launch(exceptionHandler) {
            _progressState.value = true
            fetchMovieUseCase.fetchMovie(movieId)
            _progressState.value = false
        }
    }

    sealed class MovieDetailsState {
        class MovieWithActors(val movie: Movie) : MovieDetailsState()
        class MovieWithoutActors(val movie: Movie) : MovieDetailsState()
    }
}