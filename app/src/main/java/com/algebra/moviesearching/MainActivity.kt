package com.algebra.moviesearching

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.moviesearching.constants.Constants
import com.algebra.moviesearching.databinding.ActivityMainBinding
import com.algebra.moviesearching.list.FavoriteAdapter
import com.algebra.moviesearching.search.SearchMoviesActivity
import com.algebra.moviesearching.viewModel.FavoriteMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = FavoriteAdapter(this)
    private lateinit var searchView: SearchView
    private val viewModelFavorite: FavoriteMoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpRecyclerView()
        setSupportActionBar(binding.toolbar)
    }

    override fun onResume() {
        super.onResume()
        viewModelFavorite.getAllFavoriteMovies().observe(this, Observer {
            adapter.setList(it)
            if(it.isEmpty()){
                binding.tvTitleFavorite.text = "Favorite"
                binding.tvDisplayMess.text = "You don't have any favorite movies in your list.\n Search movies by name to add them"
            } else {
                binding.tvTitleFavorite.text = ""
                binding.tvDisplayMess.text = ""
            }
            binding.progressBar.visibility = View.GONE
        })
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
                        if(it.isNotEmpty()){
                            val intent = Intent(this@MainActivity, SearchMoviesActivity::class.java)
                            intent.putExtra(Constants.SEARCH_VALUE, it)
                            startActivity(intent)
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
                    it.length >= 3-> {
                        Log.d("ispsisovo", it)
                        val intent = Intent(this@MainActivity, SearchMoviesActivity::class.java)
                        intent.putExtra(Constants.SEARCH_VALUE, it)
                        startActivity(intent)
                    }
                }
            }
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpRecyclerView(){
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter
    }
}