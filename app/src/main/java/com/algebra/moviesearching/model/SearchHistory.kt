package com.algebra.moviesearching.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SearchHistory")
data class SearchHistory(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "Title")
    val title: String,

    @ColumnInfo(name = "Year")
    val year: String,

    @ColumnInfo(name = "Poster")
    val pictureUrl: String,

    @ColumnInfo(name = "imdbID")
    val imdbID: String,

    @ColumnInfo(name = "searchValue")
    val searchValue: String

    )