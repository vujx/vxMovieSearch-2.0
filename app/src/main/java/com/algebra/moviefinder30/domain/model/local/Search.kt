package com.algebra.moviefinder30.domain.model.local

import com.algebra.moviefinder30.domain.UnitMovieEntry

data class Search(
    val id: Int,
    override val title: String,
    override val year: String,
    override val pictureURL: String,
    override val imdbId: String,
    val searchValue: String
): UnitMovieEntry