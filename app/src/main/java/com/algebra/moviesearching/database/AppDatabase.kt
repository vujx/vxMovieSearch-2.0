package com.algebra.moviesearching.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algebra.moviesearching.model.FavoriteMovie
import com.algebra.moviesearching.model.SearchHistory

@Database(entities = [FavoriteMovie::class, SearchHistory::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoriteDao(): FavoriteMoviesDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}