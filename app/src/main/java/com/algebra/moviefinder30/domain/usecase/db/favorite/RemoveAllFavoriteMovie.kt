package com.algebra.moviefinder30.domain.usecase.db.favorite

import com.algebra.moviefinder30.App
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class RemoveAllFavoriteMovie(private val favoriteRepo: FavoriteMovieRepository): BaseUseCase<Int?, String> {

    override suspend fun execute(params: Int?, callback: BaseUseCase.Callback<String>) {
        try{
            favoriteRepo.removeAllFavoritesMovie()
            callback.onSuccess(App.getResource().getString(R.string.remove_all_from_fav))
        }catch (e: Exception){
            callback.onError(e)
        }
    }
}