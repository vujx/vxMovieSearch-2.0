package com.algebra.moviefinder30.data.repository

import com.algebra.moviefinder30.data.db.SearchDao
import com.algebra.moviefinder30.domain.db.SearchMovieMapper
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchLocalDataSource
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(private val searchDao: SearchDao): MovieSearchLocalDataSource {

    private val searchMapper = SearchMovieMapper()

    override suspend fun getSearchMoviesByYear(searchValue: String): List<Movie> = searchMapper.toEntityListMovie(searchDao.getSearchMoviesByYear(searchValue))
    override suspend fun insertSearchMovie(searchMovie: Movie) = searchDao.insertSearchMovie(searchMapper.mapFromEntity(searchMovie))

}