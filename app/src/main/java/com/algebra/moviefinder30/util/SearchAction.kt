package com.algebra.moviefinder30.util

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.Navigation
import com.algebra.moviefinder30.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@SuppressLint("RestrictedApi", "CheckResult")
fun searchAction(searchView: SearchView, view: View?){
    var checkIfSubmit = false
    Observable.create<String> { emiter ->
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if(it.isNotEmpty()){
                        checkIfSubmit = true
                        view?.let { Navigation.findNavController(it).navigate(R.id.action_favoriteFragment_to_searchFragment2) }
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
                it.length >= 3 && !checkIfSubmit -> view?.let { Navigation.findNavController(it).navigate(R.id.action_favoriteFragment_to_searchFragment2) }
            }
        }
}


