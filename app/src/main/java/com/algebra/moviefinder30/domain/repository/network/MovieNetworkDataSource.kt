package com.algebra.moviefinder30.domain.repository.network

import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.model.remote.MovieDetails

interface MovieNetworkDataSource {

    suspend fun getMoviesByTitle(searchValue: String): List<Movie>?
    suspend fun getMoviesByYear(searchValue: String): List<Movie>?
    suspend fun getMoviesDetailsById(imdbId: String): MovieDetails?

}