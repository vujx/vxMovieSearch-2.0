package com.algebra.moviesearching.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.moviesearching.R
import com.algebra.moviesearching.constants.Constants
import com.algebra.moviesearching.databinding.ActivitySearchMoviesBinding
import com.algebra.moviesearching.details.DetailsActivity
import com.algebra.moviesearching.list.MovieAdapter
import com.algebra.moviesearching.model.FavoriteMovie
import com.algebra.moviesearching.viewModel.FavoriteMoviesViewModel
import com.algebra.moviesearching.viewModel.SearchHistoryViewModel
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
    private lateinit var observerActions: ObserverAction
    private val clickListeners = ClickListeners()
    private val viewModelSearchHistory: SearchHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpToolbar()
        searchAction.searchResult = intent.getStringExtra(Constants.SEARCH_VALUE) ?: ""

        lifecycleScope.launchWhenStarted {
            listOfFavMovies.addAll(viewModelFavoriteMovie.getAllFavMoviesCour())
            adapter = MovieAdapter(this@SearchMoviesActivity, listOfFavMovies)
            observerActions = ObserverAction(viewModelMovies, searchAction.searchResult, this@SearchMoviesActivity, binding, adapter, viewModelSearchHistory)
            setUpRecyclerView()
            observerActions.bind()
            observerActions.searchActionAfterSubmit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu?.findItem(R.id.searchIcon)
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Enter movie"

       searchAction.searchAction(searchView, observerActions)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onRestart() {
        super.onRestart()
        if(DetailsActivity.checkIfChange){
            lifecycleScope.launchWhenResumed {
                val list = viewModelFavoriteMovie.getAllFavMoviesCour()
                listOfFavMovies.clear()
                listOfFavMovies.addAll(list)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setUpToolbar(){
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    private fun setUpRecyclerView(){
        binding.rvSearchMovies.layoutManager = LinearLayoutManager(this)
        binding.rvSearchMovies.adapter = adapter

        clickListeners.clickListeners(viewModelFavoriteMovie, adapter, listOfFavMovies, this)

        binding.swipeRefresh.setOnRefreshListener {
            observerActions.searchActionAfterSubmit()
            binding.swipeRefresh.isRefreshing = false
        }
    }
}