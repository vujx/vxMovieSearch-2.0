package com.algebra.moviefinder30.domain.repository.network

class MovieNetworkRepository(private val dataSource: MovieNetworkDataSource) {

    suspend fun getMovieDetailsById(imdbId: String) = dataSource.getMoviesDetailsById(imdbId)

    suspend fun getMoviesByYear(searchValue: String) = dataSource.getMoviesByYear(searchValue)
}
