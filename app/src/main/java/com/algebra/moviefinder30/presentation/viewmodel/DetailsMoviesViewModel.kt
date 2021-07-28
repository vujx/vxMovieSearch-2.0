package com.algebra.moviefinder30.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.algebra.moviefinder30.data.usecase.UseCaseNetwork
import com.algebra.moviefinder30.domain.model.remote.MovieDetails
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import com.algebra.moviefinder30.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsMoviesViewModel @Inject constructor(private val useCaseNetwork: UseCaseNetwork): ViewModel() {

    private val _notification: MutableLiveData<Event<String>> = MutableLiveData()
    val notification: LiveData<Event<String>> = _notification

    private val _movieDetail = MutableLiveData<MovieDetails>()
    val movieDetail: LiveData<MovieDetails> = _movieDetail

    private fun onError(message: String){
        _notification.postValue(Event(message))
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable.message.toString())
    }

    fun fetchMovieDetails(imdb: String){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            useCaseNetwork.getMovieDetailsById.execute(imdb, object: BaseUseCase.Callback<MovieDetails?>{
                override fun onSuccess(result: MovieDetails?) {
                    if(result == null) _notification.postValue(Event("Something went wrong, try again!"))
                    else _movieDetail.postValue(result!!)
                }

                override fun onError(throwable: Throwable) {
                    _notification.postValue(Event(throwable.message))
                }
            })
        }
    }
}