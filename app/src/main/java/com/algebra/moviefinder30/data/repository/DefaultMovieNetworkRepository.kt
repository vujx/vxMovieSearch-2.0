package com.algebra.moviefinder30.data.repository

import android.content.res.Resources
import android.util.Log
import com.algebra.moviefinder30.BuildConfig
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.data.network.MovieService
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.model.remote.MovieDetails
import com.algebra.moviefinder30.domain.network.mapper.MovieDetailsNetworkMapper
import com.algebra.moviefinder30.domain.network.mapper.MovieNetworkMapper
import com.algebra.moviefinder30.domain.repository.network.MovieNetworkDataSource
import javax.inject.Inject

class DefaultMovieNetworkRepository @Inject constructor(private val apiService: MovieService): MovieNetworkDataSource {

    private val networkMapper = MovieNetworkMapper()
    private val networkDetailsMapper = MovieDetailsNetworkMapper()

    override suspend fun getMoviesByTitle(searchValue: String): List<Movie>? {
       val response = apiService.getSearchMovies(BuildConfig.APIKey, searchValue)
        return if(response.isSuccessful)  response.body()?.let {
            networkMapper.toEntityListMovie(it.Search)
        } else null
    }

    override suspend fun getMoviesByYear(searchValue: String): List<Movie>? {
        val response = apiService.getSearchMovies(BuildConfig.APIKey, searchValue)
        return if(response.isSuccessful) response.body()?.let {
            networkMapper.toEntityListMovieByYear(it.Search)
        } else null
    }

    override suspend fun getMoviesDetailsById(imdbId: String): MovieDetails? {
        val response = apiService.getMovieDetails(BuildConfig.APIKey, imdbId)
        return if(response.isSuccessful) response.body()?.let {
            networkDetailsMapper.mapFromEntity(it)
        } else null
    }

}