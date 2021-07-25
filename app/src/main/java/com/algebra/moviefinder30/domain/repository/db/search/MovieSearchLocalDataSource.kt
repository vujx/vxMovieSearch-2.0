package com.algebra.moviefinder30.domain.repository.db.search

import com.algebra.moviefinder30.domain.model.local.Search
import com.algebra.moviefinder30.domain.model.remote.Movie

interface MovieSearchLocalDataSource {

    suspend fun getSearchMoviesByYear(searchValue: String): List<Search>
    suspend fun insertSearchMovie(searchMovie: Movie)

}