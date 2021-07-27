package com.algebra.moviefinder30.domain.usecase.db.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import java.lang.Exception

class GetAllFavoriteMovies(private val favoriteRepo: FavoriteMovieRepository): BaseUseCase<Int?, List<FavoriteMovieEntity>> {

    override suspend fun execute(params: Int?, callback: BaseUseCase.Callback<List<FavoriteMovieEntity>>) {
        try {
            Log.d("ispis3", "saas")
            val result = favoriteRepo.getAllFavoritesMovies()
            callback.onSuccess(result.value ?: emptyList())
        } catch (e: Exception){
            Log.d("ispis4", "sasda")
            callback.onError(e)
        }
    }

}