package com.algebra.moviesearching.search

import androidx.appcompat.widget.SearchView
import com.algebra.moviesearching.viewModel.SearchMoviesViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchActionMovies {

    var searchResult = ""

    fun searchAction(viewModelMovies: SearchMoviesViewModel, searchView: SearchView) {

        Observable.create<String> { emiter ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (it.length > 3 && it != searchResult) {
                            viewModelMovies.getAllMovies(it)
                            searchResult = it
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        if (!emiter.isDisposed)
                            emiter.onNext(newText)
                    }
                    return true
                }
            })
        }.debounce(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when {
                    it.length >= 3 && searchResult != it -> {
                        viewModelMovies.getAllMovies(it)
                        searchResult = it
                    }
                }
            }
    }
}