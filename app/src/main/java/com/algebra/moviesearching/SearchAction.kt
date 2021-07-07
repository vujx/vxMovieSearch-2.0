package com.algebra.moviesearching

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.algebra.moviesearching.constants.Constants
import com.algebra.moviesearching.search.SearchMoviesActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchAction(private val activity: AppCompatActivity) {

    var checkIfSubmit = false

    fun searchAction(searchView: SearchView){
        Observable.create<String> { emiter ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if(it.isNotEmpty()){
                            checkIfSubmit = true
                            val intent = Intent(activity, SearchMoviesActivity::class.java)
                            intent.putExtra(Constants.SEARCH_VALUE, it)
                            activity.startActivity(intent)
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
                    it.length >= 3 && !checkIfSubmit -> {
                        val intent = Intent(activity, SearchMoviesActivity::class.java)
                        intent.putExtra(Constants.SEARCH_VALUE, it)
                        activity.startActivity(intent)
                    }
                }
            }
    }
}