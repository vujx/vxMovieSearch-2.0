package com.algebra.moviesearching.repository

import android.util.Log
import com.algebra.moviesearching.constants.Constants
import com.algebra.moviesearching.model.Movie
import com.algebra.moviesearching.networking.SearchingMoviesService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class SearchMoviesRepository @Inject constructor(private val apiSearchingMoviesService: SearchingMoviesService) {

    interface Listener{
        fun onSuccess(listOfMovies: Movie)
        fun onFailure(error: String)
    }

    fun getMovies(searchValue: String, listener: Listener){
        apiSearchingMoviesService.getSearchMovies(Constants.API_KEY, searchValue).enqueue(object: retrofit2.Callback<Movie>{
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                listener.onFailure(t.message.toString())
                Log.d("dazovo", t.message.toString())
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if(!response.isSuccessful) listener.onFailure("")
                else response.body()?.let {
                    listener.onSuccess(it)
                } ?: listener.onFailure("")
                Log.d("ispisovo", response.body().toString() + "adssd")
                Log.d("isSuucc", response.isSuccessful.toString())
            }
        })
    }

}