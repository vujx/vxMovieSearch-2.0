package com.algebra.moviefinder30.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.algebra.moviefinder30.data.usecase.UseCaseDbFavorite
import com.algebra.moviefinder30.data.usecase.UseCaseNetwork
import com.algebra.moviefinder30.domain.model.remote.MovieDetails
import com.algebra.moviefinder30.domain.network.mapper.MovieDetailsNetworkMapper
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import com.algebra.moviefinder30.presentation.util.addFavMovie
import com.algebra.moviefinder30.presentation.util.removeFavMovie
import com.algebra.moviefinder30.util.Event
import com.algebra.moviefinder30.util.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsMoviesViewModel @Inject constructor(private val useCaseNetwork: UseCaseNetwork, private val useCasesFavorite: UseCaseDbFavorite): ViewModel() {

    private val _movieDetail = MutableLiveData<ResultOf<MovieDetails>>()
    val movieDetail : LiveData<ResultOf<MovieDetails>> = _movieDetail

    private val _notification: MutableLiveData<Event<String>> = MutableLiveData()
    val notification: LiveData<Event<String>> = _notification

    private fun onError(message: String){
        _notification.postValue(Event(message))
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable.message.toString())
    }

    fun fetchMovieDetails(imdb: String){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _movieDetail.postValue(ResultOf.Loading())
            useCaseNetwork.getMovieDetailsById.execute(imdb, object: BaseUseCase.Callback<MovieDetails>{
                override fun onSuccess(result: MovieDetails) {
                    _movieDetail.postValue(ResultOf.Success(result))
                }

                override fun onError(throwable: Throwable) {
                    _movieDetail.postValue(ResultOf.Failure(throwable.message, throwable))
                }
            })
        }
    }

    fun addMovieToFavorite(movieDetails: MovieDetails) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler){
            addFavMovie(useCasesFavorite, MovieDetailsNetworkMapper().mapToMovie(movieDetails), _notification)
        }


    fun removeMovieFromFavorite(imdb: String) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            removeFavMovie(useCasesFavorite, imdb, _notification)
        }
}