package com.algebra.moviefinder30.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.algebra.moviefinder30.domain.UnitMovieEntry

@Entity(tableName = "FavoriteMovies")
data class FavoriteMovieEntity(

    @ColumnInfo(name = "title")
    override val title: String,

    @ColumnInfo(name = "year")
    override val year: String,

    @ColumnInfo(name = "pictureURL")
    override val pictureURL: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "imdbId")
    override val imdbId: String
) : UnitMovieEntry
