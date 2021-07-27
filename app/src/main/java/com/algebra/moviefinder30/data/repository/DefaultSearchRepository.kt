package com.algebra.moviefinder30.data.repository

import com.algebra.moviefinder30.data.db.SearchDao
import com.algebra.moviefinder30.data.model.local.SearchEntity
import com.algebra.moviefinder30.data.model.remote.movie.SearchNetworkEntity
import com.algebra.moviefinder30.domain.db.SearchMovieMapper
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchLocalDataSource
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(private val searchDao: SearchDao): MovieSearchLocalDataSource {

    private val searchMapper = SearchMovieMapper()

    override suspend fun getSearchMoviesByYear(searchValue: String): List<SearchEntity> = searchDao.getSearchMoviesByYear(searchValue)
    override suspend fun insertSearchMovie(searchMovie: SearchNetworkEntity) = searchDao.insertSearchMovie(searchMapper.mapFromEntity(searchMovie))

}