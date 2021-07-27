package com.algebra.moviefinder30.domain.usecase.db.favorite

import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieLocalDataSource
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import java.lang.Exception

class RemoveAllFavoriteMovie(private val favoriteRepo: FavoriteMovieRepository): BaseUseCase<Int?, String> {

    override suspend fun execute(params: Int?, callback: BaseUseCase.Callback<String>) {
        try{
            favoriteRepo.removeAllFavoritesMovie()
            callback.onSuccess("Successfully clear favorite movies!")
        }catch (e: Exception){
            callback.onError(e)
        }
    }
}