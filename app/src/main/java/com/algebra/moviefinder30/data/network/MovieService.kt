package com.algebra.moviefinder30.data.network

import androidx.annotation.Keep
import com.algebra.moviefinder30.data.model.remote.details.MovieDetailsNetworkEntity
import com.algebra.moviefinder30.data.model.remote.movie.MovieNetworkEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(".")
    @Keep
    suspend fun getSearchMovies(
        @Query("apikey") apikey: String,
        @Query("s") s: String
    ): Response<MovieNetworkEntity>

    @GET(".")
    @Keep
    suspend fun getMovieDetails(
        @Query("apikey") apikey: String,
        @Query("i") i: String
    ): Response<MovieDetailsNetworkEntity>
}
