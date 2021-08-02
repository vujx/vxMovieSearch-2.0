package com.algebra.moviefinder30.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.presentation.ui.dialog.DialogListener
import com.algebra.moviefinder30.util.exitFromApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DialogListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        exitFromApp(this)
    }

    fun onBack() {
        super.onBackPressed()
    }

    companion object {
        var searchValue = ""
    }

    override fun okPress() {
        finishAffinity()
    }
}
