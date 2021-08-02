package com.algebra.moviefinder30.domain.usecase.db.favorite

import com.algebra.moviefinder30.App
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class RemoveFavoriteMovie(private val favoriteRepo: FavoriteMovieRepository) : BaseUseCase<String, String> {

    override suspend fun execute(params: String, callback: BaseUseCase.Callback<String>) {
        try {
            favoriteRepo.removeFavoriteMovie(params)
            callback.onSuccess(App.getResource().getString(R.string.remove_fav_move_from_db))
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
}
