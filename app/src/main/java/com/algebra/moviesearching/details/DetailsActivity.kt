package com.algebra.moviesearching.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.algebra.moviesearching.R
import com.algebra.moviesearching.constants.Constants
import com.algebra.moviesearching.databinding.ActivityDetailsBinding
import com.algebra.moviesearching.model.FavoriteMovie
import com.algebra.moviesearching.viewModel.FavoriteMoviesViewModel
import com.algebra.moviesearching.viewModel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val viewModelDetails: MovieDetailViewModel by viewModels()
    private val observerAction = ObserverActionDetails()
    private val viewModelFavoriteMovies: FavoriteMoviesViewModel by viewModels()
    private var isFav: Int = 0
    private var firstFav = 0
    private var imdb = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.title = intent.getStringExtra(Constants.TITLE)
        isFav = intent.getIntExtra(Constants.IS_FAV, 0)
        firstFav = isFav
        imdb = intent.getStringExtra(Constants.IMDB_ID) ?: ""
        viewModelDetails.getMovieDetails(imdb)
        setUpToolbar()
        if(isFav == 0) binding.ivFavMovie.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24_white))
        else binding.ivFavMovie.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24))

    }

    override fun onResume() {
        super.onResume()
        observerAction.bind(viewModelDetails, binding, this)
    }

    private fun setUpToolbar(){
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24)
        setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            checkIfChange = isFav != firstFav
            super.onBackPressed()
        }
    }

    fun clickListener(){
        binding.ivFavMovie.setOnClickListener {
            if(isFav == 0){
                isFav = 1
                binding.ivFavMovie.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24))
                if(observerAction.movieDetail.year.length > 4)
                    observerAction.movieDetail.year = observerAction.movieDetail.year.substring(0, 4)
                viewModelFavoriteMovies.insertMovieToFavorite(FavoriteMovie(0, observerAction.movieDetail.title, observerAction.movieDetail.year, observerAction.movieDetail.pictureURL, imdb))
            } else {
                isFav = 0
                binding.ivFavMovie.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24_white))
                viewModelFavoriteMovies.removeMovieFromFavorite(imdb)
            }
        }
    }

    override fun onBackPressed() {
        checkIfChange = isFav != firstFav
        super.onBackPressed()
    }

    companion object{
        var checkIfChange = false
    }
}