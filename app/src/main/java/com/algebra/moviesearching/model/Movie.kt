package com.algebra.moviesearching.model

import com.squareup.moshi.Json

data class Movie(
    @field: Json(name = "Search")
    val movieDetails: List<MovieDetails>

)

data class MovieDetails(
    @field: Json(name = "Title")
    val title: String,

    @field: Json(name = "Year")
    val year: String,

    @field: Json(name = "Poster")
    val pictureUrl: String,

    @field: Json(name = "imdbID")
    val imdbId: String
)