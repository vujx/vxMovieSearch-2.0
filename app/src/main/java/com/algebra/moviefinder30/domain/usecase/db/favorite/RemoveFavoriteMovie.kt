package com.algebra.moviefinder30.domain.usecase.db.favorite

import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieLocalDataSource
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class RemoveFavoriteMovie(private val dataSource: FavoriteMovieLocalDataSource): BaseUseCase<Int, String> {

    override suspend fun execute(params: Int, callback: BaseUseCase.Callback<String>) {
        try{
            dataSource.removeFavoriteMovie(params)
            callback.onSuccess("Favorite movie successfully removed!")
        } catch (e: Exception){
            callback.onError(e)
        }
    }

}