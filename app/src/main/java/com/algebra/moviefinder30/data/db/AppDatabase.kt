package com.algebra.moviefinder30.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algebra.moviefinder30.domain.model.local.FavoriteMovie
import com.algebra.moviefinder30.domain.model.local.Search

@Database(entities = [FavoriteMovie::class, Search::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoriteDao(): FavoriteMovieDao
    abstract fun searchHistoryDao(): SearchDao

}