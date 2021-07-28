package com.algebra.moviefinder30.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.algebra.moviefinder30.data.usecase.UseCaseDbFavorite
import com.algebra.moviefinder30.data.usecase.UseCaseDbSearch
import com.algebra.moviefinder30.data.usecase.UseCaseNetwork
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import com.algebra.moviefinder30.presentation.util.addFavMovie
import com.algebra.moviefinder30.presentation.util.addMovieToDb
import com.algebra.moviefinder30.presentation.util.fetchMoviesFromNetwork
import com.algebra.moviefinder30.presentation.util.removeFavMovie
import com.algebra.moviefinder30.util.Event
import com.algebra.moviefinder30.util.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMoviesViewModel @Inject constructor(private val useCasesSearch: UseCaseDbSearch,
                                                private val useCasesFavorite: UseCaseDbFavorite,
                                                private val useCaseNetwork: UseCaseNetwork): ViewModel() {

    private val _notification: MutableLiveData<Event<String>> = MutableLiveData()
    val notification: LiveData<Event<String>> = _notification

    private val _movies = MutableLiveData<ResultOf<List<Movie>>>()
    val movies: LiveData<ResultOf<List<Movie>>> = _movies

    private fun onError(message: String){
        _notification.postValue(Event(message))
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable.message.toString())
    }

    fun addMovieToFavorite(movie: Movie){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler){
            addFavMovie(useCasesFavorite, movie, _notification)
            Log.d("iss", "sad123")
        }
    }

    fun removeMovieFromFavorite(imdb: String) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            removeFavMovie(useCasesFavorite, imdb, _notification)
        }


    fun fetchMovies(searchValue: String){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _movies.postValue(ResultOf.Loading())
            Log.d("sssa", "sss")
            useCasesSearch.getSearchMovieByYear.execute(searchValue, object: BaseUseCase.Callback<List<Movie>>{

                override fun onSuccess(result: List<Movie>) {
                    Log.d("sssa", result.toString())
                    if(result.isEmpty()){
                        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                            fetchMoviesFromNetwork(useCaseNetwork, searchValue, this@SearchMoviesViewModel, _movies)
                        }
                    } else _movies.postValue(ResultOf.Success(result))
                }

                override fun onError(throwable: Throwable) {
                    _movies.postValue(ResultOf.Failure(throwable.message, throwable))
                }
            })
        }
    }

    fun addMovieToSearchDb(movie: Movie){
        viewModelScope.launch {
            addMovieToDb(useCasesSearch, movie, _notification)
        }
    }
}