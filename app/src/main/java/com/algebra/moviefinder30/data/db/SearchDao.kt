package com.algebra.moviefinder30.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.moviefinder30.data.model.local.SearchEntity

@Dao
interface SearchDao {

    @Query("SELECT * FROM SearchHistory WHERE :searchValue LIKE searchValue ORDER BY year")
    suspend fun getSearchMoviesByYear(searchValue: String): List<SearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchMovie(searchMovie: SearchEntity)

    @Query("DELETE FROM SearchHistory")
    suspend fun removeAllSearchMovies()
}
