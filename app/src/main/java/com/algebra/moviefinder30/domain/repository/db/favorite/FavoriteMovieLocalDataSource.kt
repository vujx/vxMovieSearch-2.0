package com.algebra.moviefinder30.domain.repository.db.favorite

import androidx.lifecycle.LiveData
import com.algebra.moviefinder30.domain.model.local.FavoriteMovie
import com.algebra.moviefinder30.domain.model.remote.Movie

interface FavoriteMovieLocalDataSource {

    suspend fun insertFavoriteMovie(movie: Movie)
    suspend fun getAllFavoritesMovies(): LiveData<List<FavoriteMovie>>
    suspend fun removeFavoriteMovie(id: Int)
    suspend fun removeAllFavoritesMovie()
    suspend fun getFavoriteMovie(id : Int) : FavoriteMovie?

}