package com.algebra.moviefinder30.domain.usecase.db.favorite

import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class InsertFavoriteMovie(private val favoriteRepo: FavoriteMovieRepository): BaseUseCase<Movie, String> {

    override suspend fun execute(params: Movie, callback: BaseUseCase.Callback<String>) {
        try{
            favoriteRepo.insertFavoriteMovie(params)
            callback.onSuccess("Favorite movie successfully added!")
        } catch (e: Exception){
            callback.onError(e)
        }
    }

}