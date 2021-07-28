package com.algebra.moviefinder30.domain.repository.db.search

import com.algebra.moviefinder30.data.model.remote.movie.SearchNetworkEntity
import com.algebra.moviefinder30.domain.model.remote.Movie

interface MovieSearchLocalDataSource {

    suspend fun getSearchMoviesByYear(searchValue: String): List<Movie>
    suspend fun insertSearchMovie(searchMovie: Movie)

}