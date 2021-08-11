package com.algebra.moviefinder30.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.data.db.SearchDao
import com.algebra.moviefinder30.presentation.ui.dialog.DialogListener
import com.algebra.moviefinder30.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DialogListener {

    private lateinit var sharePreference: SharedPreferences

    @Inject
    lateinit var searchDao: SearchDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharePreference = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        addLastDateOfClearingSearchResults()
    }

    private fun compareTwoDates(lastDate: String): Double {
        val now = Calendar.getInstance().timeInMillis
        return (now - lastDate.toLong()).toDouble() / (24 * 60 * 60 * 1000)
    }

    private fun addLastDateOfClearingSearchResults() {
        val lastDateStore = sharePreference.getString(Constants.LAST_DATE, "")
        if (lastDateStore.isNullOrEmpty() || compareTwoDates(lastDateStore) > 6.99) {
            val editor = sharePreference.edit()
            val now = Calendar.getInstance().timeInMillis
            editor.putString(Constants.LAST_DATE, now.toString())
            editor.apply()
            lifecycleScope.launchWhenCreated { searchDao.removeAllSearchMovies() }
        }
    }

    companion object {
        var searchValue = ""
    }

    override fun okPress() {
        finishAffinity()
    }
}
