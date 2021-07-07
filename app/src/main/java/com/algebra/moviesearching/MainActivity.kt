package com.algebra.moviesearching

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.moviesearching.databinding.ActivityMainBinding
import com.algebra.moviesearching.dialog.DialogExit
import com.algebra.moviesearching.list.FavoriteAdapter
import com.algebra.moviesearching.viewModel.FavoriteMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = FavoriteAdapter(this)
    private lateinit var searchView: SearchView
    private val viewModelFavorite: FavoriteMoviesViewModel by viewModels()
    private var searchAction = SearchAction(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpRecyclerView()
        setSupportActionBar(binding.toolbar)
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        viewModelFavorite.getAllFavoriteMovies().observe(this, {
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
        searchAction.checkIfSubmit = false
    }

    override fun onBackPressed() {
        val dialog = DialogExit("Are you sure you want to exit?")
        dialog.show(supportFragmentManager, "Logout")
        dialog.listener = object : DialogExit.ListenerForDialog {
            override fun okPress(isPress: Boolean) {
                finishAffinity()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu?.findItem(R.id.searchIcon)
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Enter movie"
        searchAction.searchAction(searchView)

        return super.onCreateOptionsMenu(menu)
    }


    private fun setUpRecyclerView(){
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter

        adapter.listener = object: FavoriteAdapter.Listener{
            override fun onFavClick(imdbId: String) {
                viewModelFavorite.removeMovieFromFavorite(imdbId)
            }
        }
    }
}