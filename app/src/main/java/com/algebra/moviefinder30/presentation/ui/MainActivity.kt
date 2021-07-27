package com.algebra.moviefinder30.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.util.exitFromApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exitFromApp(this)
    }
}