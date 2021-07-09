package com.algebra.moviesearching.search

import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchActionMovies {

    var searchResult = ""

    fun searchAction( searchView: SearchView, observerAction: ObserverAction){

        Observable.create<String> { emiter ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (it.length > 3 && it != searchResult) {
                            searchResult = it
                            observerAction.searchResult = it
                            observerAction.searchActionAfterSubmit()
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
                        observerAction.searchResult = it
                        observerAction.searchActionAfterSubmit()
                        searchResult = it
                    }
                }
            }
    }
}