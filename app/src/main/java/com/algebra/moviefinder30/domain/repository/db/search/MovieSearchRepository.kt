package com.algebra.moviefinder30.domain.repository.db.search

import com.algebra.moviefinder30.domain.model.local.Search

class MovieSearchRepository(private val dataSource: MovieSearchLocalDataSource) {

    suspend fun getSearchMoviesByYear() = dataSource.getSearchMoviesByYear()

    suspend fun insertSearchMovie(searchMovie: Search) = dataSource.insertSearchMovie(searchMovie)

}