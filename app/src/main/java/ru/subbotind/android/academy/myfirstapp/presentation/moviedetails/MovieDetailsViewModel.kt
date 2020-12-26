package ru.subbotind.android.academy.myfirstapp.presentation.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.subbotind.android.academy.myfirstapp.data.Movie
import ru.subbotind.android.academy.myfirstapp.domain.MovieInteractor

class MovieDetailsViewModel @ViewModelInject constructor(
    private val movieInteractor: MovieInteractor
) : ViewModel() {

    private val _movieState: MutableLiveData<MovieDetailsState> = MutableLiveData()
    val movieState: LiveData<MovieDetailsState> = _movieState

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _movieState.value = MovieDetailsState.LoadingStarted
            val movie = movieInteractor.getMovie(movieId)
                ?: throw IllegalArgumentException("Movie with $movieId is null, nut should not")
            _movieState.value = MovieDetailsState.LoadingSuccess

            _movieState.value = if (movie.actors.isEmpty()) {
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