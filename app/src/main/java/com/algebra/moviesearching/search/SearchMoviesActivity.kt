package com.algebra.moviesearching.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.moviesearching.R
import com.algebra.moviesearching.constants.Constants
import com.algebra.moviesearching.databinding.ActivitySearchMoviesBinding
import com.algebra.moviesearching.displayMessage
import com.algebra.moviesearching.list.MovieAdapter
import com.algebra.moviesearching.model.FavoriteMovie
import com.algebra.moviesearching.viewModel.FavoriteMoviesViewModel
import com.algebra.moviesearching.viewModel.SearchMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchMoviesBinding
    private lateinit var adapter: MovieAdapter
    private val viewModelMovies: SearchMoviesViewModel by viewModels()
    private var searchResult = ""
    private lateinit var searchView: SearchView
    private val listOfFavMovies = mutableListOf<FavoriteMovie>()
    private val viewModelFavoriteMovie: FavoriteMoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        lifecycleScope.launchWhenResumed {
            listOfFavMovies.addAll(viewModelFavoriteMovie.getAllFavMoviesCour())
            adapter = MovieAdapter(this@SearchMoviesActivity, listOfFavMovies)
            setUpRecyclerView()
            bind()
        }

        searchResult = intent.getStringExtra(Constants.SEARCH_VALUE) ?: ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu?.findItem(R.id.searchIcon)
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Enter movie"

        Observable.create<String> { emiter ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if(it.length > 3 && it != searchResult) {
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
                    it.length >= 3 && searchResult != it-> {
                        viewModelMovies.getAllMovies(it)
                        searchResult = it
                    }
                }
            }
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpRecyclerView(){
        binding.rvSearchMovies.layoutManager = LinearLayoutManager(this)
        binding.rvSearchMovies.adapter = adapter

        adapter.listener = object: MovieAdapter.Listener{
            override fun onFavClick(favMovie: FavoriteMovie, isFav: Boolean) {
                if(!isFav) viewModelFavoriteMovie.insertMovieToFavorite(favMovie)
                else viewModelFavoriteMovie.removeMovieFromFavorite(favMovie.id)
            }
        }
    }

    private fun bind(){
        viewModelMovies.getAllMovies(searchResult)

        viewModelMovies.moviesObserver.observe(this, Observer {
            if(it.movieDetails.isEmpty()) binding.tvSearchMess.text = "No search result, try again!"
            else adapter.setList(it.movieDetails)
        })

        viewModelMovies.progressBarObserver.observe(this, Observer {
            if(it == 0) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })

        viewModelMovies.errorObserver.observe(this, Observer {
            when{
                it.contains("UnknownHostException") || it.contains("No address associated with hostname") ->
                    displayMessage("Check you internet connection!", this)
                it.contains("HTTP 404 Not Found") -> displayMessage("GitHub Repositories not found, try again!", this)
                else -> displayMessage("Something went wrong, try again!", this)
            }
        })
    }
}