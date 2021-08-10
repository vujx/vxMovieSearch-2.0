package com.algebra.moviefinder30.domain.repository.db.favorite

import com.algebra.moviefinder30.domain.model.remote.Movie

class FavoriteMovieRepository(private val dataSource: FavoriteMovieLocalDataSource) {

    suspend fun insertFavoriteMovie(favoriteMovie: Movie) = dataSource.insertFavoriteMovie(favoriteMovie)

    suspend fun getAllFavoritesMovies() = dataSource.getAllFavoritesMovies()

    suspend fun removeFavoriteMovie(imdb: String) = dataSource.removeFavoriteMovie(imdb)

    suspend fun removeAllFavoritesMovie() = dataSource.removeAllFavoritesMovie()
}
