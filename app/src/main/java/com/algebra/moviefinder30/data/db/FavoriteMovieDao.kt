package com.algebra.moviefinder30.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM FavoriteMovies LIMIT 1")
    suspend fun getFavoriteMovie(): FavoriteMovieEntity

    @Query("SELECT * FROM FavoriteMovies")
    suspend fun getAllFavoritesMovies(): List<FavoriteMovieEntity>

    @Query("DELETE FROM FavoriteMovies WHERE :imdb LIKE imdbId")
    suspend fun removeFavoriteMovie(imdb: String)

    @Query("DELETE FROM FavoriteMovies")
    suspend fun removeAllFavoritesMovie()
}
