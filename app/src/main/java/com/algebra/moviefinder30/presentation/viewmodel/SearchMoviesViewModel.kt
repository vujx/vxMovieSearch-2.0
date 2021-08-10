package com.algebra.moviefinder30.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.data.usecase.UseCaseDbFavorite
import com.algebra.moviefinder30.data.usecase.UseCaseNetwork
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.presentation.util.addFavMovie
import com.algebra.moviefinder30.presentation.util.fetchMoviesFromDbOrApi
import com.algebra.moviefinder30.presentation.util.getAllFavMovie
import com.algebra.moviefinder30.presentation.util.removeFavMovie
import com.algebra.moviefinder30.util.Event
import com.algebra.moviefinder30.util.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    private val useCasesFavorite: UseCaseDbFavorite,
    private val useCaseNetwork: UseCaseNetwork
) : ViewModel() {

    private val _favorites = MutableLiveData<ResultOf<List<FavoriteMovieEntity>>>()
    val favorites: LiveData<ResultOf<List<FavoriteMovieEntity>>> = _favorites

    private val _notification: MutableLiveData<Event<String>> = MutableLiveData()
    val notification: LiveData<Event<String>> = _notification

    private val _movies = MutableLiveData<ResultOf<List<Movie>>>()
    val movies: LiveData<ResultOf<List<Movie>>> = _movies

    private fun onError(message: String) {
        _notification.postValue(Event(message))
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable.message.toString())
    }

    fun getAllFavoriteMovies() = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        getAllFavMovie(useCasesFavorite, _favorites)
    }

    fun addMovieToFavorite(movie: Movie) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            addFavMovie(useCasesFavorite, movie, _notification)
            getAllFavoriteMovies()
        }

    fun removeMovieFromFavorite(imdb: String) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            removeFavMovie(useCasesFavorite, imdb, _notification)
            getAllFavoriteMovies()
        }

    fun fetchMovies(searchValue: String) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            fetchMoviesFromDbOrApi(_movies, useCaseNetwork, searchValue)
        }
}
