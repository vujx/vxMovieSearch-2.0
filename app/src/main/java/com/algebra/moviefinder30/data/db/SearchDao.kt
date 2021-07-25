package com.algebra.moviefinder30.data.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.moviefinder30.domain.model.local.Search

interface SearchDao {

    @Query("SELECT * FROM SearchHistory WHERE :searchValue LIKE searchValue ORDER BY year")
    suspend fun getSearchMoviesByYear(searchValue: String): List<Search>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchMovie(searchMovie: Search)

}