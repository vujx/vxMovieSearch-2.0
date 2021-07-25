package com.algebra.moviefinder30.domain.repository.network

import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.model.remote.MovieDetails

interface MovieNetworkDataSource {

    suspend fun getMoviesByTitle(): List<Movie>
    suspend fun getMoviesByYear(): List<Movie>
    suspend fun getMoviesDetailsById(): MovieDetails

}