package com.algebra.moviesearching.repository

import com.algebra.moviesearching.constants.Constants
import com.algebra.moviesearching.model.Movie
import com.algebra.moviesearching.model.MovieDetail
import com.algebra.moviesearching.networking.SearchingMoviesService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(private val apiSearchingMoviesService: SearchingMoviesService) {

    interface Listener{
        fun onSuccess(movieDetail: MovieDetail)
        fun onFailure(error: String)
    }

    fun getMovieDetail(imdbId: String, listener: Listener){
        apiSearchingMoviesService.getMovieDetails(Constants.API_KEY, imdbId).enqueue(object: retrofit2.Callback<MovieDetail>{
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                listener.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if(!response.isSuccessful) listener.onFailure("")
                else response.body()?.let {
                    listener.onSuccess(it)
                } ?: listener.onFailure("")
            }

        })
    }
}