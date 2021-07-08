package com.algebra.moviesearching.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.moviesearching.model.SearchHistory

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM SearchHistory WHERE :searchValue LIKE searchValue")
    suspend fun getSearchHistory(searchValue: String): List<SearchHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchHistory(searchHistory: SearchHistory)


}