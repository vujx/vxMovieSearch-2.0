package com.algebra.moviefinder30.domain.repository.db.search

import com.algebra.moviefinder30.domain.model.local.Search

interface MovieSearchLocalDataSource {

    suspend fun getSearchMoviesByYear(): List<Search>
    suspend fun insertSearchMovie(searchMovie: Search)

}