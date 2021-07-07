package com.algebra.moviesearching.search

import android.view.View
import androidx.lifecycle.Observer
import com.algebra.moviesearching.databinding.ActivitySearchMoviesBinding
import com.algebra.moviesearching.displayMessage
import com.algebra.moviesearching.list.MovieAdapter
import com.algebra.moviesearching.viewModel.SearchMoviesViewModel

class ObserverAction {

    fun bind(viewModelMovies: SearchMoviesViewModel, searchResult: String, activity: SearchMoviesActivity, binding: ActivitySearchMoviesBinding, adapter: MovieAdapter){
        viewModelMovies.getAllMovies(searchResult)

        viewModelMovies.moviesObserver.observe(activity, Observer {
            if(it.movieDetails.isNullOrEmpty()) binding.tvSearchMess.text = "Movie not found, try again!"
            else adapter.setList(it.movieDetails)
        })

        viewModelMovies.progressBarObserver.observe(activity, Observer {
            if(it == 0) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })

        viewModelMovies.errorObserver.observe(activity, Observer {
            when{
                it.contains("UnknownHostException") || it.contains("No address associated with hostname") ->
                    displayMessage("Check you internet connection!", activity)
                it.contains("HTTP 404 Not Found") -> displayMessage("GitHub Repositories not found, try again!", activity)
                else -> displayMessage("Something went wrong, try again!", activity)
            }
        })
    }
}