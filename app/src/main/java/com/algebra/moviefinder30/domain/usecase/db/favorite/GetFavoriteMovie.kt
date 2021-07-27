package com.algebra.moviefinder30.domain.usecase.db.favorite

import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import java.lang.Exception

class GetFavoriteMovie(private val favoriteRepo: FavoriteMovieRepository): BaseUseCase<Int, FavoriteMovieEntity?> {

    override suspend fun execute(params: Int, callback: BaseUseCase.Callback<FavoriteMovieEntity?>) {
        try{
            val result = favoriteRepo.getFavoriteMovie(params)
            callback.onSuccess(result)
        } catch (e: Exception){
            callback.onError(e)
        }
    }

}