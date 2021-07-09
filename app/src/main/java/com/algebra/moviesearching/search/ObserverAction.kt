package com.algebra.moviesearching.search

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.algebra.moviesearching.databinding.ActivitySearchMoviesBinding
import com.algebra.moviesearching.displayMessage
import com.algebra.moviesearching.list.MovieAdapter
import com.algebra.moviesearching.model.MovieDetails
import com.algebra.moviesearching.model.SearchHistory
import com.algebra.moviesearching.viewModel.SearchHistoryViewModel
import com.algebra.moviesearching.viewModel.SearchMoviesViewModel

class ObserverAction(private val viewModelMovies: SearchMoviesViewModel, var searchResult: String, private val activity: SearchMoviesActivity,
                     private val binding: ActivitySearchMoviesBinding, private val adapter: MovieAdapter, private val viewModelSearchHistory: SearchHistoryViewModel) {

    @SuppressLint("SetTextI18n")
    fun bind() {

        viewModelMovies.progressBarObserver.observe(activity, {
            if (it == 0) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })

        viewModelMovies.errorObserver.observe(activity, {
            when {
                it.contains("UnknownHostException") || it.contains("No address associated with hostname") ->
                    displayMessage("Check you internet connection!", activity)
                it.contains("HTTP 404 Not Found") -> displayMessage(
                    "Movie not found, try again!", activity)
                else -> displayMessage("Something went wrong, try again!", activity)
            }
        })

        viewModelMovies.moviesObserver.observe(activity, {
            if (it.movieDetails.isNullOrEmpty()){
                binding.tvSearchMess.text =  "Movie not found, try again!"
                adapter.setList(emptyList())
            }
            else {
                binding.tvSearchMess.text = ""
                adapter.setList(it.movieDetails)
                it.movieDetails.forEach {
                    viewModelSearchHistory.insertSearchHistory(
                        SearchHistory(0, it.title, it.year, it.pictureUrl, it.imdbId, searchResult))
                }
            }
        })
    }

    fun searchActionAfterSubmit(){
        activity.lifecycleScope.launchWhenResumed {
            binding.progressBar.visibility = View.VISIBLE
            val listOfSearchHistory = viewModelSearchHistory.getSearchHistory(searchResult)
            if (listOfSearchHistory.isNotEmpty()) {
                val listOfMovies = mutableListOf<MovieDetails>()
                binding.tvSearchMess.text = ""
                listOfSearchHistory.forEach {
                    listOfMovies.add(MovieDetails(it.title, it.year, it.pictureUrl, it.imdbID))
                }
                binding.progressBar.visibility = View.GONE
                adapter.setList(listOfMovies)
            } else viewModelMovies.getAllMovies(searchResult)
        }
    }
}