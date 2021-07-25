package com.algebra.moviefinder30.data.repository

import androidx.lifecycle.LiveData
import com.algebra.moviefinder30.data.db.FavoriteMovieDao
import com.algebra.moviefinder30.domain.db.FavoriteMovieMapper
import com.algebra.moviefinder30.domain.model.local.FavoriteMovie
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieLocalDataSource
import javax.inject.Inject

class DefaultFavoriteRepository @Inject constructor(private val favoriteMovieDao: FavoriteMovieDao): FavoriteMovieLocalDataSource{

    private val favoriteMapper = FavoriteMovieMapper()

    override suspend fun insertFavoriteMovie(movie: Movie) = favoriteMovieDao.insertFavoriteMovie(favoriteMapper.mapFromEntity(movie))

    override suspend fun getAllFavoritesMovies(): LiveData<List<FavoriteMovie>> = favoriteMovieDao.getAllFavoritesMovies()

    override suspend fun removeFavoriteMovie(id: Int) = favoriteMovieDao.removeFavoriteMovie(id)

    override suspend fun removeAllFavoritesMovie() = favoriteMovieDao.removeAllFavoritesMovie()

    override suspend fun getFavoriteMovie(id: Int): FavoriteMovie? = favoriteMovieDao.getMovieEntity(id)

}