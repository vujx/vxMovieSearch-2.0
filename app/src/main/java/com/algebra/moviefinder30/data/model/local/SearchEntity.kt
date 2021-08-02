package com.algebra.moviefinder30.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.algebra.moviefinder30.domain.UnitMovieEntry

@Entity(tableName = "SearchHistory")
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title")
    override val title: String,

    @ColumnInfo(name = "year")
    override val year: String,

    @ColumnInfo(name = "pictureURL")
    override val pictureURL: String,

    @ColumnInfo(name = "imdbId")
    override val imdbId: String,

    @ColumnInfo(name = "searchValue")
    val searchValue: String
) : UnitMovieEntry
