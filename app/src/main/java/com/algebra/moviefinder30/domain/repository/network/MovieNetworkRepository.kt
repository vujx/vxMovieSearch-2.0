package com.algebra.moviefinder30.domain.repository.network

class MovieNetworkRepository(private val dataSource: MovieNetworkDataSource) {

    suspend fun getMovieDetailsById() = dataSource.getMoviesDetailsById()

    suspend fun getMoviesByTitle() = dataSource.getMoviesByTitle()

    suspend fun getMoviesByYear() = dataSource.getMoviesByYear()

}