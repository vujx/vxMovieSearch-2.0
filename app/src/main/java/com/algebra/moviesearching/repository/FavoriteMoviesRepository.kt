package com.algebra.moviesearching.repository

import com.algebra.moviesearching.database.AppDatabase
import com.algebra.moviesearching.di.DatabaseIOExecutor
import com.algebra.moviesearching.model.FavoriteMovie
import java.util.concurrent.Executor
import javax.inject.Inject

class FavoriteMoviesRepository @Inject constructor(private val appDatabase: AppDatabase,
                                        @DatabaseIOExecutor private val databaseExecutor: Executor){

    private val favoriteMoviesDao = appDatabase.favoriteDao()

    fun insertFavoriteMovie(movie: FavoriteMovie){
        databaseExecutor.execute {
            favoriteMoviesDao.insertFavoriteMovie(movie)
        }
    }

    fun getAllFavoriteMovies() = favoriteMoviesDao.getAllFavoritesMovies()

    fun removeMovie(id: Int) {
        databaseExecutor.execute {
            favoriteMoviesDao.removeFavoriteMovie(id)
        }
    }

    suspend fun getAllFavMovies() = favoriteMoviesDao.getAllFavMoviesCour()
}