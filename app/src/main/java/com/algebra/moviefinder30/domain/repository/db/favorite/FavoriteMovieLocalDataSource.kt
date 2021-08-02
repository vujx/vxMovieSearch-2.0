package com.algebra.moviefinder30.domain.repository.db.favorite

import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.domain.model.remote.Movie

interface FavoriteMovieLocalDataSource {

    suspend fun insertFavoriteMovie(movie: Movie)
    suspend fun getAllFavoritesMovies(): List<FavoriteMovieEntity>
    suspend fun removeFavoriteMovie(imdb: String)
    suspend fun removeAllFavoritesMovie()
    suspend fun getFavoriteMovie(id: Int): FavoriteMovieEntity?
}
