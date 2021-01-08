package ru.subbotind.android.academy.myfirstapp.presentation.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMovieUseCase

class MovieDetailsViewModel @ViewModelInject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {

    private val _movieState: MutableLiveData<MovieDetailsState> = MutableLiveData()
    val movieState: LiveData<MovieDetailsState> = _movieState

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
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