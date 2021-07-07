package com.algebra.moviesearching.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.algebra.moviesearching.R
import com.algebra.moviesearching.constants.Constants
import com.algebra.moviesearching.databinding.ActivityDetailsBinding
import com.algebra.moviesearching.viewModel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val viewModelDetails: MovieDetailViewModel by viewModels()
    private val observerAction = ObserverActionDetails()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intent.getStringExtra(Constants.IMDB_ID)?.let { viewModelDetails.getMovieDetails(it) }
        setUpToolbar()

        observerAction.bind(viewModelDetails, binding, this)
    }

    private fun setUpToolbar(){
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24)
        setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }
}