package com.algebra.moviefinder30.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.algebra.moviefinder30.data.db.FavoriteMovieDao
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.domain.db.FavoriteMovieMapper
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieLocalDataSource
import javax.inject.Inject

class DefaultFavoriteRepository @Inject constructor(private val favoriteMovieDao: FavoriteMovieDao): FavoriteMovieLocalDataSource{

    private val favoriteMapper = FavoriteMovieMapper()

    override suspend fun insertFavoriteMovie(movie: Movie) {
        favoriteMovieDao.insertFavoriteMovie(favoriteMapper.mapFromEntity(movie))
        Log.d("ipsiiv", movie.toString())
        Log.d("sss", favoriteMapper.mapFromEntity(movie).toString())
    }

    override suspend fun getAllFavoritesMovies(): List<FavoriteMovieEntity> = favoriteMovieDao.getAllFavoritesMovies()

    override suspend fun removeFavoriteMovie(imdb: String) = favoriteMovieDao.removeFavoriteMovie(imdb)

    override suspend fun removeAllFavoritesMovie() = favoriteMovieDao.removeAllFavoritesMovie()

    override suspend fun getFavoriteMovie(id: Int): FavoriteMovieEntity? = favoriteMovieDao.getMovieEntity(id)

}