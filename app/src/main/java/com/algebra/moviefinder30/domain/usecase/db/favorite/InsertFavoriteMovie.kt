package com.algebra.moviefinder30.domain.usecase.db.favorite

import com.algebra.moviefinder30.domain.model.local.FavoriteMovie
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieLocalDataSource
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import java.lang.Exception

class InsertFavoriteMovie(private val dataSource: FavoriteMovieLocalDataSource): BaseUseCase<FavoriteMovie, String> {

    override suspend fun execute(params: FavoriteMovie, callback: BaseUseCase.Callback<String>) {
        try{
            dataSource.insertFavoriteMovie(params)
            callback.onSuccess("Movie successfully added!")
        } catch (e: Exception){
            callback.onError(e)
        }
    }

}