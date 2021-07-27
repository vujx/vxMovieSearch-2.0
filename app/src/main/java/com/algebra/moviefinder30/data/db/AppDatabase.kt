package com.algebra.moviefinder30.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.data.model.local.SearchEntity

@Database(entities = [FavoriteMovieEntity::class, SearchEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoriteDao(): FavoriteMovieDao
    abstract fun searchHistoryDao(): SearchDao

}