package com.algebra.moviesearching.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algebra.moviesearching.model.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoriteDao(): FavoriteMoviesDao
}