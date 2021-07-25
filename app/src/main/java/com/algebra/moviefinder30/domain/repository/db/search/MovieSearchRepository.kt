package com.algebra.moviefinder30.domain.repository.db.search

import com.algebra.moviefinder30.domain.model.local.Search
import com.algebra.moviefinder30.domain.model.remote.Movie

class MovieSearchRepository(private val dataSource: MovieSearchLocalDataSource) {

    suspend fun getSearchMoviesByYear(searchValue: String) = dataSource.getSearchMoviesByYear(searchValue)

    suspend fun insertSearchMovie(searchMovie: Movie) = dataSource.insertSearchMovie(searchMovie)

}