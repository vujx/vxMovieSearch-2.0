package com.algebra.moviesearching.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.moviesearching.MainActivity
import com.algebra.moviesearching.R
import com.algebra.moviesearching.constants.Constants
import com.algebra.moviesearching.databinding.ActivitySearchMoviesBinding
import com.algebra.moviesearching.list.MovieAdapter
import com.algebra.moviesearching.model.FavoriteMovie
import com.algebra.moviesearching.viewModel.FavoriteMoviesViewModel
import com.algebra.moviesearching.viewModel.SearchMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchMoviesBinding
    private lateinit var adapter: MovieAdapter
    private val viewModelMovies: SearchMoviesViewModel by viewModels()
    private lateinit var searchView: SearchView
    private val listOfFavMovies = mutableListOf<FavoriteMovie>()
    private val viewModelFavoriteMovie: FavoriteMoviesViewModel by viewModels()
    private val searchAction = SearchActionMovies()
    private val observerActions = ObserverAction()
    private val clickListeners = ClickListeners()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        lifecycleScope.launchWhenResumed {
            listOfFavMovies.addAll(viewModelFavoriteMovie.getAllFavMoviesCour())
            adapter = MovieAdapter(this@SearchMoviesActivity, listOfFavMovies)
            setUpRecyclerView()
            observerActions.bind(viewModelMovies, searchAction.searchResult, this@SearchMoviesActivity, binding, adapter)
        }

        searchAction.searchResult = intent.getStringExtra(Constants.SEARCH_VALUE) ?: ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu?.findItem(R.id.searchIcon)
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Enter movie"

       searchAction.searchAction(viewModelMovies, searchView)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpRecyclerView(){
        binding.rvSearchMovies.layoutManager = LinearLayoutManager(this)
        binding.rvSearchMovies.adapter = adapter

        clickListeners.clickListeners(viewModelFavoriteMovie, adapter, listOfFavMovies, this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}