package com.algebra.moviefinder30.domain.usecase.db.favorite

import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class GetAllFavoriteMovies(private val favoriteRepo: FavoriteMovieRepository) : BaseUseCase<Int?, List<FavoriteMovieEntity>> {

    override suspend fun execute(
        params: Int?,
        callback: BaseUseCase.Callback<List<FavoriteMovieEntity>>
    ) {
        try {
            val result = favoriteRepo.getAllFavoritesMovies()
            callback.onSuccess(result)
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
}
