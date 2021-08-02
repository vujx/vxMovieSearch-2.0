package com.algebra.moviefinder30.domain.model.remote

import com.algebra.moviefinder30.domain.UnitMovieEntry

data class MovieDetails(
    override val title: String,
    override val year: String,
    override val pictureURL: String,
    override val imdbId: String,
    val released: String,
    val duration: String,
    val imdbRating: String,
    val description: String,
    val genre: String
) : UnitMovieEntry
