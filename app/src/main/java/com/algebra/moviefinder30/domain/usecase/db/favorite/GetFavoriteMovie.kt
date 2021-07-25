package com.algebra.moviefinder30.domain.usecase.db.favorite

import com.algebra.moviefinder30.domain.model.local.FavoriteMovie
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieLocalDataSource
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import java.lang.Exception

class GetFavoriteMovie(private val dataSource: FavoriteMovieLocalDataSource): BaseUseCase<Int, FavoriteMovie?> {

    override suspend fun execute(params: Int, callback: BaseUseCase.Callback<FavoriteMovie?>) {
        try{
            val result = dataSource.getFavoriteMovie(params)
            callback.onSuccess(result)
        } catch (e: Exception){
            callback.onError(e)
        }
    }

}