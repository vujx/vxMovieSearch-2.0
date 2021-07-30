package com.algebra.moviefinder30.domain.usecase.db.favorite

import com.algebra.moviefinder30.App
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class InsertFavoriteMovie(private val favoriteRepo: FavoriteMovieRepository): BaseUseCase<Movie, String> {

    override suspend fun execute(params: Movie, callback: BaseUseCase.Callback<String>) {
        try{
            favoriteRepo.insertFavoriteMovie(params)
            callback.onSuccess(App.getResource().getString(R.string.added_fav_movie))
        } catch (e: Exception){
            callback.onError(e)
        }
    }

}