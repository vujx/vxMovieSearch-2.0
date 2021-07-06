package com.algebra.moviesearching.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.moviesearching.model.FavoriteMovie

@Dao
interface FavoriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: FavoriteMovie)

    @Query("SELECT * FROM FavoriteMovies")
    fun getAllFavoritesMovies(): LiveData<List<FavoriteMovie>>

    @Query("DELETE FROM FavoriteMovies WHERE :id = id")
    fun removeFavoriteMovie(id: Int)

    @Query("SELECT * FROM FavoriteMovies")
    suspend fun getAllFavMoviesCour(): List<FavoriteMovie>
}