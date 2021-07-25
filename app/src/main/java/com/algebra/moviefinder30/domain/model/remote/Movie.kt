package com.algebra.moviefinder30.domain.model.remote

import com.algebra.moviefinder30.domain.UnitMovieEntry

data class Movie(
    override val title: String,
    override val year: String,
    override val pictureURL: String,
    override val imdbId: String
) : UnitMovieEntry