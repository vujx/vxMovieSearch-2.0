package com.algebra.moviesearching.details

import android.annotation.SuppressLint
import android.view.View
import com.algebra.moviesearching.R
import com.algebra.moviesearching.databinding.ActivityDetailsBinding
import com.algebra.moviesearching.displayMessage
import com.algebra.moviesearching.model.MovieDetail
import com.algebra.moviesearching.viewModel.MovieDetailViewModel
import com.bumptech.glide.Glide

class ObserverActionDetails {

    lateinit var movieDetail: MovieDetail

    @SuppressLint("SetTextI18n")
    fun bind(viewModelDetails: MovieDetailViewModel, binding: ActivityDetailsBinding, activity: DetailsActivity){
        viewModelDetails.moviesObserver.observe(activity, {

            movieDetail = it

            binding.tvTitle.text = it.title

            if(it.description == "N/A") binding.tvDesc.text = "No description available!"
            else binding.tvDesc.text = it.description

            if(it.imdbRating == "N/A") binding.tvRating.text = "No IMDB rating!"
            else binding.tvRating.text = "${it.imdbRating}/10"

            if(it.released == "N/A" && it.duration == "N/A") binding.tvDurationAndPublish.text = "/"
            else if(it.released == "N/A") binding.tvDurationAndPublish.text = it.duration
            else if(it.duration == "N/A") binding.tvDurationAndPublish.text = it.released
            else binding.tvDurationAndPublish.text = "${it.released} - ${it.duration}"

            if(it.genre == "N/A") binding.tvGenre.text = "No genre available!"
            else binding.tvGenre.text = it.genre

            binding.ivImdb.setBackgroundResource(R.drawable.imdb)
            Glide.with(activity)
                .load(it.pictureURL)
                .placeholder(R.drawable.no_image_available)
                .into(binding.ivMovie)
        })

        viewModelDetails.progressBarObserver.observe(activity, {
            if(it == 0) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })

        viewModelDetails.errorObserver.observe(activity, {
            when{
                it.contains("UnknownHostException") || it.contains("No address associated with hostname") ->
                    displayMessage("Check you internet connection!", activity)
                it.contains("HTTP 404 Not Found") -> displayMessage("GitHub Repositories not found, try again!", activity)
                else -> displayMessage("Something went wrong, try again!", activity)
            }
        })
    }
}