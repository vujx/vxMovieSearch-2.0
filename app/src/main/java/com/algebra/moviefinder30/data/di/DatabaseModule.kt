package com.algebra.moviefinder30.data.di

import android.content.Context
import androidx.room.Room
import com.algebra.moviefinder30.data.db.AppDatabase
import com.algebra.moviefinder30.data.db.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "Movies.db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideFavoriteDao(database: AppDatabase): FavoriteMovieDao = database.favoriteDao()

    @Provides
    fun provideSearchDao(database: AppDatabase) = database.searchHistoryDao()
}
