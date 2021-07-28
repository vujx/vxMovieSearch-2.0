package com.algebra.moviefinder30.presentation.util

import androidx.lifecycle.MutableLiveData
import com.algebra.moviefinder30.data.usecase.UseCaseDbSearch
import com.algebra.moviefinder30.data.usecase.UseCaseNetwork
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import com.algebra.moviefinder30.presentation.viewmodel.SearchMoviesViewModel
import com.algebra.moviefinder30.util.Event
import com.algebra.moviefinder30.util.ResultOf

suspend fun addMovieToDb(useCasesSearch: UseCaseDbSearch, movie: Movie, _notification: MutableLiveData<Event<String>>){
    useCasesSearch.insertSearchMovie.execute(movie, object: BaseUseCase.Callback<String>{
        override fun onSuccess(result: String) {}

        override fun onError(throwable: Throwable) {
            _notification.postValue(Event(throwable.message))
        }
    })
}

suspend fun fetchMoviesFromNetwork(useCaseNetwork: UseCaseNetwork, searchValue: String, viewModel: SearchMoviesViewModel, _movies: MutableLiveData<ResultOf<List<Movie>>>){
    useCaseNetwork.getMovieByYear.execute(searchValue, object: BaseUseCase.Callback<List<Movie>?>{
        override fun onSuccess(result: List<Movie>?) {
            if(result != null) {
                _movies.postValue(ResultOf.Success(result))
                result.forEach {
                    viewModel.addMovieToSearchDb(it)
                }
            } else _movies.postValue(ResultOf.Failure("Something went wrong, try again!", null))
        }

        override fun onError(throwable: Throwable) {
            _movies.postValue(ResultOf.Failure(throwable.message, throwable))
        }
    })
}