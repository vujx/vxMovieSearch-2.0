package com.algebra.moviesearching.networking

import com.algebra.moviesearching.model.Movie
import com.algebra.moviesearching.model.MovieDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchingMoviesService {

    @GET(".")
    fun getSearchMovies(
        @Query("apikey") apikey: String,
        @Query("s") s: String
    ): Call<Movie>

    @GET(".")
    fun getMovieDetails(
        @Query("apikey") apikey: String,
        @Query("i") i: String
    ): Call<MovieDetail>
}