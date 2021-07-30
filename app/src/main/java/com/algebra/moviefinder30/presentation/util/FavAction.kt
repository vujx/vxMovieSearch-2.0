package com.algebra.moviefinder30.presentation.util

import androidx.lifecycle.MutableLiveData
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.data.usecase.UseCaseDbFavorite
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import com.algebra.moviefinder30.util.Event
import com.algebra.moviefinder30.util.ResultOf
import java.util.*

suspend fun removeFavMovie(useCasesFavorite: UseCaseDbFavorite, imdb: String, _notification: MutableLiveData<Event<String>>){
    useCasesFavorite.removeFavoriteMovie.execute(imdb, object: BaseUseCase.Callback<String>{
        override fun onSuccess(result: String) {
            _notification.postValue(Event(result))
        }

        override fun onError(throwable: Throwable) {
            _notification.postValue(Event(throwable.message))
        }
    })
}

suspend fun addFavMovie(useCasesFavorite: UseCaseDbFavorite, movie: Movie, _notification: MutableLiveData<Event<String>>){
    useCasesFavorite.insertFavoriteMovie.execute(movie, object: BaseUseCase.Callback<String>{
        override fun onSuccess(result: String) {
            _notification.postValue(Event(result))
        }

        override fun onError(throwable: Throwable) {
            _notification.postValue(Event(throwable.message))
        }
    })
}

suspend fun removeAllFavMovie(useCasesFavorite: UseCaseDbFavorite, _notification: MutableLiveData<Event<String>>){
    useCasesFavorite.removeAllFavoriteMovie.execute(null, object: BaseUseCase.Callback<String>{
        override fun onSuccess(result: String) {
            _notification.postValue(Event(result))
        }

        override fun onError(throwable: Throwable) {
            _notification.postValue(Event(throwable.message))
        }
    })
}

suspend fun getAllFavMovie(useCasesFavorite: UseCaseDbFavorite, _favorites: MutableLiveData<ResultOf<List<FavoriteMovieEntity>>>){
    _favorites.postValue(ResultOf.Loading())
    useCasesFavorite.getAllFavoriteMovies.execute(null, object: BaseUseCase.Callback<List<FavoriteMovieEntity>>{
        override fun onSuccess(result: List<FavoriteMovieEntity>) {
            _favorites.postValue(ResultOf.Success(result.sortedWith(compareBy ({ it.title }, {it.year}))))
        }

        override fun onError(throwable: Throwable) {
            _favorites.postValue(ResultOf.Failure(throwable.message, throwable))
        }
    })
}