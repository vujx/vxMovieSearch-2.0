package com.algebra.moviefinder30.data.model.remote.movie

data class MovieNetworkEntity(
    val Response: String,
    val Search: List<SearchNetworkEntity>,
    val totalResults: String
)
