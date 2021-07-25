package com.algebra.moviefinder30.data.db

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.moviefinder30.domain.model.local.FavoriteMovie

interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovie)

    @Query("SELECT * FROM FavoriteMovies")
    suspend fun getAllFavoritesMovies(): LiveData<List<FavoriteMovie>>

    @Query("DELETE FROM FavoriteMovies WHERE :id LIKE imdbId")
    suspend fun removeFavoriteMovie(id: String)

    @Query("DELETE FROM FavoriteMovies")
    suspend fun removeAllFavoritesMovie()

    @Query("SELECT * FROM FavoriteMovies WHERE id = :id")
    suspend fun getMovieEntity(id : Int) : FavoriteMovie?
}