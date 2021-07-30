package com.algebra.moviefinder30.presentation.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.algebra.moviefinder30.App
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.databinding.FragmentDetailsBinding
import com.algebra.moviefinder30.domain.model.remote.MovieDetails
import com.algebra.moviefinder30.presentation.viewmodel.DetailsMoviesViewModel
import com.algebra.moviefinder30.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!
    private val viewModelDetails: DetailsMoviesViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    private var isFav: Boolean = false
    private var movieDetails: MovieDetails? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        isFav = args.isFav
        setUpToolbar()
        bind()
        binding.ivImdb.visibility = View.GONE
        viewModelDetails.fetchMovieDetails(args.imdb)
        clickListener()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setUpToolbar(){
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        if(isFav)
            binding.ivFavMovie.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_border_24_black))
    }

    @SuppressLint("SetTextI18n")
    private fun bind() {
        viewModelDetails.notification.observe(viewLifecycleOwner, {result ->
            hideProgressBar(binding.progressBar)
            if(result != null && !result.hasBeenHandled()) displayMessage(result.contentIfNotHandled.toString(), requireContext())
        })

        viewModelDetails.movieDetail.observe(viewLifecycleOwner, {result ->
            when (result) {
                is ResultOf.Success -> {
                    movieDetails = result.value
                    hideProgressBar(binding.progressBar)
                    binding.apply {
                        hideProgressBar(binding.progressBar)
                        ivImdb.visibility = View.VISIBLE
                        view?.let { displayPic(it, result.value.pictureURL, ivMovie) }
                        tvDesc.text = result.value.description
                        tvDurationAndPublish.text = result.value.duration
                        tvGenre.text = result.value.genre
                        if(result.value.imdbRating != App.getResource().getString(R.string.check_value_message)) tvRating.text = "${result.value.imdbRating}/10"
                        else tvRating.text = result.value.imdbRating
                        tvTitle.text = result.value.title
                    }
                }
                is ResultOf.Failure -> {
                    hideProgressBar(binding.progressBar)
                    result.throwable?.let { displayErrorMessage(it, requireContext()) }
                }
                is ResultOf.Loading -> displayProgressBar(binding.progressBar)
            }
        })
    }

    private fun clickListener(){
        binding.ivFavMovie.setOnClickListener {
           if(isFav && movieDetails != null){
                isFav = false
                binding.ivFavMovie.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_border_24))
                movieDetails?.imdbId?.let {  viewModelDetails.removeMovieFromFavorite(it) }
            } else if(movieDetails != null){
                isFav = true
                binding.ivFavMovie.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_border_24_black))
                movieDetails?.let { viewModelDetails.addMovieToFavorite(it) }
            } else displayMessage(App.getResource().getString(R.string.error_message), requireContext())
        }
    }
}