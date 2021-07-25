package com.algebra.moviefinder30.domain.usecase.db.favorite

import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class RemoveFavoriteMovie(private val favoriteRepo: FavoriteMovieRepository): BaseUseCase<Int, String> {

    override suspend fun execute(params: Int, callback: BaseUseCase.Callback<String>) {
        try{
            favoriteRepo.removeFavoriteMovie(params)
            callback.onSuccess("Favorite movie successfully removed!")
        } catch (e: Exception){
            callback.onError(e)
        }
    }

}