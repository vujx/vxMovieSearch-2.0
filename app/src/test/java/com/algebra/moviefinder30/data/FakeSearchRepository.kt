package com.algebra.moviefinder30.data

import com.algebra.moviefinder30.data.model.local.SearchEntity
import com.algebra.moviefinder30.domain.db.SearchMovieMapper
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchLocalDataSource

class FakeSearchRepository : MovieSearchLocalDataSource {

    private val searchMovies = mutableListOf<SearchEntity>()

    override suspend fun getSearchMoviesByYear(searchValue: String): List<Movie> = SearchMovieMapper().toEntityListMovie(searchMovies)

    override suspend fun insertSearchMovie(searchMovie: Movie) {
        searchMovies.add(SearchEntity(0, searchMovie.title, searchMovie.year, searchMovie.pictureURL, searchMovie.imdbId, "titanic"))
    }
}
