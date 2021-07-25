package com.algebra.moviefinder30.data.network

import com.algebra.moviefinder30.data.model.remote.details.MovieDetailsNetworkEntity
import com.algebra.moviefinder30.data.model.remote.movie.MovieNetworkEntity
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(".")
    suspend fun getSearchMovies(
        @Query("apikey") apikey: String,
        @Query("s") s: String
    ): Response<MovieNetworkEntity>

    @GET(".")
    suspend fun getMovieDetails(
        @Query("apikey") apikey: String,
        @Query("i") i: String
    ): Response<MovieDetailsNetworkEntity>
}