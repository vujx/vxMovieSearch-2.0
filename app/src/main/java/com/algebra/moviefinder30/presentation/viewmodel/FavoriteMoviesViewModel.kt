package com.algebra.moviefinder30.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.data.usecase.UseCaseDbFavorite
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import com.algebra.moviefinder30.util.Event
import com.algebra.moviefinder30.util.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(private val useCases: UseCaseDbFavorite): ViewModel() {

    private val _favorites = MutableLiveData<ResultOf<List<FavoriteMovieEntity>>>()
    val favorites : LiveData<ResultOf<List<FavoriteMovieEntity>>> = _favorites

    private val _notification: MutableLiveData<Event<String>> = MutableLiveData()
    val notification: LiveData<Event<String>> = _notification

    fun getAllFavoriteMovies() = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        _favorites.postValue(ResultOf.Loading())
        Log.d("ispis", "sasa")
        useCases.getAllFavoriteMovies.execute(null, object: BaseUseCase.Callback<List<FavoriteMovieEntity>>{
            override fun onSuccess(result: List<FavoriteMovieEntity>) {
                Log.d("ispis", "sasa1")
                _favorites.postValue(ResultOf.Success(result.sortedBy { it.title.toLowerCase(Locale.ROOT) }))
            }

            override fun onError(throwable: Throwable) {
                Log.d("ispis", "sasa2")
                _favorites.postValue(ResultOf.Failure(throwable.message, throwable))
            }
        })
    }

    fun removeAllFavoriteMovies() = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        useCases.removeAllFavoriteMovie.execute(null, object: BaseUseCase.Callback<String>{
            override fun onSuccess(result: String) {
                _notification.postValue(Event(result))
            }

            override fun onError(throwable: Throwable) {
                _notification.postValue(Event(throwable.message))
            }
        })
        getAllFavoriteMovies()
    }

    fun removeFavoriteMovie(imdb: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        useCases.removeFavoriteMovie.execute(imdb, object: BaseUseCase.Callback<String>{
            override fun onSuccess(result: String) {
                _notification.postValue(Event(result))
            }

            override fun onError(throwable: Throwable) {
                _notification.postValue(Event(throwable.message))
            }
        })
        getAllFavoriteMovies()
    }

    private fun onError(message: String){
        _notification.postValue(Event(message))
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable.message.toString())
    }
}