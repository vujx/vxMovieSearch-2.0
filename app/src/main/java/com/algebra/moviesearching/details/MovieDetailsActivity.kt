package com.algebra.moviesearching.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.algebra.moviesearching.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        
    }
}