package com.algebra.moviefinder30.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.algebra.moviefinder30.databinding.FragmentDetailsBinding
import com.algebra.moviefinder30.presentation.viewmodel.DetailsMoviesViewModel
import com.algebra.moviefinder30.util.displayMessage
import com.algebra.moviefinder30.util.displayPic
import com.algebra.moviefinder30.util.hideProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!
    private val viewModelDetails: DetailsMoviesViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        bind()
        binding.ivImdb.visibility = View.GONE
        viewModelDetails.fetchMovieDetails(args.imdb)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun bind() {
        viewModelDetails.notification.observe(viewLifecycleOwner, {result ->
            hideProgressBar(binding.progressBar)
            if(result != null && !result.hasBeenHandled()) displayMessage(result.contentIfNotHandled.toString(), requireContext())
        })

        viewModelDetails.movieDetail.observe(viewLifecycleOwner, {result ->
            binding.apply {
                hideProgressBar(binding.progressBar)
                ivImdb.visibility = View.VISIBLE
                view?.let { displayPic(it, result.pictureURL, ivMovie) }
                tvDesc.text = result.description
                tvDurationAndPublish.text = result.duration
                tvGenre.text = result.genre
                tvRating.text = result.imdbRating
                tvTitle.text = result.title
            }
        })


    }
}