package com.algebra.moviefinder30.domain.repository.db.search

import com.algebra.moviefinder30.data.model.local.SearchEntity
import com.algebra.moviefinder30.data.model.remote.movie.SearchNetworkEntity

interface MovieSearchLocalDataSource {

    suspend fun getSearchMoviesByYear(searchValue: String): List<SearchEntity>
    suspend fun insertSearchMovie(searchMovie: SearchNetworkEntity)

}