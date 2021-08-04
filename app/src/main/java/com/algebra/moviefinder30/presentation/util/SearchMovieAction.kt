package com.algebra.moviefinder30.presentation.util

import androidx.lifecycle.MutableLiveData
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.data.usecase.UseCaseNetwork
import com.algebra.moviefinder30.domain.db.FavoriteMovieMapper
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import com.algebra.moviefinder30.util.ResultOf

suspend fun fetchMoviesFromDbOrApi(_movies: MutableLiveData<ResultOf<List<Movie>>>, useCaseNetwork: UseCaseNetwork, searchValue: String) {
    _movies.postValue(ResultOf.Loading())
    useCaseNetwork.getMovieByYear.execute(
        searchValue,
        object : BaseUseCase.Callback<List<Movie>> {
            override fun onSuccess(result: List<Movie>) {
                _movies.postValue(ResultOf.Success(result))
            }
            override fun onError(throwable: Throwable) {
                _movies.postValue(ResultOf.Failure(throwable.message, throwable))
            }
        }
    )
}

fun checkIsFav(position: Int, listOfMovies: List<Movie>, listOfFavMovies: List<FavoriteMovieEntity>): Boolean {
    return FavoriteMovieMapper().mapFromEntity(listOfMovies[position]) in listOfFavMovies
}
