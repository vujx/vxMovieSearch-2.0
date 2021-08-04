package com.algebra.moviefinder30.util

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.Navigation
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.presentation.ui.MainActivity
import com.algebra.moviefinder30.presentation.viewmodel.SearchMoviesViewModel
import kotlinx.coroutines.*
import java.util.*

var queryTextChangedJob: Job? = null

@SuppressLint("RestrictedApi", "CheckResult")
fun searchAction(searchView: SearchView, view: View?, viewModel: SearchMoviesViewModel?) {
    searchView.setOnQueryTextListener(
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty()) {
                        MainActivity.searchValue = it.toLowerCase(Locale.ROOT)
                        if (viewModel == null)
                            view?.let { Navigation.findNavController(it).navigate(R.id.action_favoriteFragment_to_searchFragment2) }
                        else
                            viewModel.fetchMovies(it.toLowerCase(Locale.ROOT))
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                queryTextChangedJob?.cancel()

                queryTextChangedJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    performSearch(newText ?: "", viewModel, view)
                }
                return true
            }
        }
    )
}

private fun performSearch(query: String, viewModel: SearchMoviesViewModel?, mView: View?) {
    when {
        query.length >= 3 -> {
            MainActivity.searchValue = query.toLowerCase(Locale.ROOT)
            if (viewModel == null)
                mView?.let { view -> Navigation.findNavController(view).navigate(R.id.action_favoriteFragment_to_searchFragment2) }
            else viewModel.fetchMovies(query.toLowerCase(Locale.ROOT))
        }
    }
}
