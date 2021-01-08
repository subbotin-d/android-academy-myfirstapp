package ru.subbotind.android.academy.myfirstapp.presentation.movielist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.subbotind.android.academy.myfirstapp.data.Movie
import ru.subbotind.android.academy.myfirstapp.domain.MovieInteractor

class MovieListViewModel @ViewModelInject constructor(
    private val movieInteractor: MovieInteractor
) : ViewModel() {

    private val _moviesState: MutableLiveData<MovieListState> =
        MutableLiveData(MovieListState.LoadingStarted)
    val moviesState: LiveData<MovieListState> = _moviesState

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _moviesState.value = MovieListState.LoadingStarted
            val movieList = movieInteractor.getMovies()
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