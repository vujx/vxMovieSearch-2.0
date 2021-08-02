package com.algebra.moviefinder30.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.data.usecase.UseCaseDbFavorite
import com.algebra.moviefinder30.presentation.util.getAllFavMovie
import com.algebra.moviefinder30.presentation.util.removeAllFavMovie
import com.algebra.moviefinder30.presentation.util.removeFavMovie
import com.algebra.moviefinder30.util.Event
import com.algebra.moviefinder30.util.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(private val useCases: UseCaseDbFavorite) : ViewModel() {

    private val _favorites = MutableLiveData<ResultOf<List<FavoriteMovieEntity>>>()
    val favorites: LiveData<ResultOf<List<FavoriteMovieEntity>>> = _favorites

    private val _notification: MutableLiveData<Event<String>> = MutableLiveData()
    val notification: LiveData<Event<String>> = _notification

    fun getAllFavoriteMovies() = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        getAllFavMovie(useCases, _favorites)
    }

    fun removeAllFavoriteMovies() = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        removeAllFavMovie(useCases, _notification)
        getAllFavoriteMovies()
    }

    fun removeFavoriteMovie(imdb: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        removeFavMovie(useCases, imdb, _notification)
        getAllFavoriteMovies()
    }

    private fun onError(message: String) {
        _notification.postValue(Event(message))
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable.message.toString())
    }
}
