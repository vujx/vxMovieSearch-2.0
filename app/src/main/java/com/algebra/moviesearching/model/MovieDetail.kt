package com.algebra.moviesearching.model

import com.squareup.moshi.Json

data class MovieDetail (
    @field: Json(name = "Title")
    val title: String,

    @field: Json(name = "Released")
    val released: String,

    @field: Json(name = "Runtime")
    val duration: String,

    @field: Json(name = "imdbRating")
    val imdbRating: String,

    @field: Json(name = "Plot")
    val description: String,

    @field: Json(name = "Genre")
    val genre: String,

    @field: Json(name = "Poster")
    val pictureURL: String

)