package com.algebra.moviefinder30.domain.repository.db.favorite

import com.algebra.moviefinder30.domain.model.local.FavoriteMovie

class FavoriteMovieRepository(private val dataSource: FavoriteMovieLocalDataSource) {

    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovie) = dataSource.insertFavoriteMovie(favoriteMovie)

    suspend fun getAllFavoritesMovies() = dataSource.getAllFavoritesMovies()

    suspend fun removeFavoriteMovie(id: Int) = dataSource.removeFavoriteMovie(id)

    suspend fun removeAllFavoritesMovie() = dataSource.removeAllFavoritesMovie()

    suspend fun getFavoriteMovie(id: Int) = dataSource.getFavoriteMovie(id)
}