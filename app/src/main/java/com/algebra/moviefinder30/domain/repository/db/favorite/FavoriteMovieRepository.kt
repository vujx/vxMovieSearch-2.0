package com.algebra.moviefinder30.domain.repository.db.favorite

import com.algebra.moviefinder30.domain.model.local.FavoriteMovie
import com.algebra.moviefinder30.domain.model.remote.Movie

class FavoriteMovieRepository(private val dataSource: FavoriteMovieLocalDataSource) {

    suspend fun insertFavoriteMovie(favoriteMovie: Movie) = dataSource.insertFavoriteMovie(favoriteMovie)

    suspend fun getAllFavoritesMovies() = dataSource.getAllFavoritesMovies()

    suspend fun removeFavoriteMovie(id: Int) = dataSource.removeFavoriteMovie(id)

    suspend fun removeAllFavoritesMovie() = dataSource.removeAllFavoritesMovie()

    suspend fun getFavoriteMovie(id: Int) = dataSource.getFavoriteMovie(id)
}