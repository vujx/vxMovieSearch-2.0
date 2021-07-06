package com.algebra.moviesearching.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteMovies")
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "year")
    val year: String,

    @ColumnInfo(name = "picture")
    val picture: String,

    @ColumnInfo(name = "imdbId")
    val imdbId: String
)