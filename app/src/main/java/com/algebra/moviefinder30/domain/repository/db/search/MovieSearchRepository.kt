package com.algebra.moviefinder30.domain.repository.db.search

import com.algebra.moviefinder30.data.model.remote.movie.SearchNetworkEntity

class MovieSearchRepository(private val dataSource: MovieSearchLocalDataSource) {

    suspend fun getSearchMoviesByYear(searchValue: String) = dataSource.getSearchMoviesByYear(searchValue)

    suspend fun insertSearchMovie(searchMovie: SearchNetworkEntity) = dataSource.insertSearchMovie(searchMovie)

}