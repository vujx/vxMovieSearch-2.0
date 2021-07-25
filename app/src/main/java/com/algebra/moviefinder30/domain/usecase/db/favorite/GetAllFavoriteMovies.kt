package com.algebra.moviefinder30.domain.usecase.db.favorite

import androidx.lifecycle.LiveData
import com.algebra.moviefinder30.domain.model.local.FavoriteMovie
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieLocalDataSource
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import java.lang.Exception

class GetAllFavoriteMovies(private val favoriteRepo: FavoriteMovieRepository): BaseUseCase<Int?, LiveData<List<FavoriteMovie>>> {

    override suspend fun execute(params: Int?, callback: BaseUseCase.Callback<LiveData<List<FavoriteMovie>>>) {

        try {
            val result = favoriteRepo.getAllFavoritesMovies()
            callback.onSuccess(result)
        } catch (e: Exception){
            callback.onError(e)
        }
    }

}