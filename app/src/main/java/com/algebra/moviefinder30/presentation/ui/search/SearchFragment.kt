package com.algebra.moviefinder30.presentation.ui.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.databinding.FragmentSearchBinding
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.presentation.ui.MainActivity
import com.algebra.moviefinder30.presentation.viewmodel.FavoriteMoviesViewModel
import com.algebra.moviefinder30.presentation.viewmodel.SearchMoviesViewModel
import com.algebra.moviefinder30.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private val viewModelFavorite: FavoriteMoviesViewModel by viewModels()
    private val viewModelSearch: SearchMoviesViewModel by viewModels()
    private val adapter = SearchAdapter()
    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setUpRecyclerView()
        clickListener()
        onBind()
        viewModelFavorite.getAllFavoriteMovies()
        viewModelSearch.fetchMovies(MainActivity.searchValue)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu.findItem(R.id.searchIcon)
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Enter movie"

        searchAction(searchView, view, viewModelSearch)
        return super.onCreateOptionsMenu(menu, menuInflater)
    }

    private fun setUpRecyclerView(){
        binding.rvSearchMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchMovies.adapter = adapter
    }

    private fun clickListener(){
        adapter.listener = object: SearchAdapter.Listener{
            override fun onFavClick(movie: Movie, isFav: Boolean) {
                if(isFav) viewModelSearch.removeMovieFromFavorite(movie.imdbId)
                else viewModelSearch.addMovieToFavorite(movie)
            }

            override fun onItemClick(imdbId: String, title: String) {
                view?.let { Navigation.findNavController(it).navigate(R.id.action_favoriteFragment_to_detailsFragment3) }
            }
        }
    }

    private fun onBind(){
        viewModelFavorite.favorites.observe(viewLifecycleOwner, { result->
            when (result) {
                is ResultOf.Success -> adapter.setFavoriteList(result.value)
                is ResultOf.Failure -> result.message?.let { displayMessage(it, requireContext()) }
            }})

        viewModelSearch.movies.observe(viewLifecycleOwner, {result ->
            when(result){
                is ResultOf.Success -> {
                    hideProgressBar(binding.progressBar)
                    if(result.value.isEmpty())
                        binding.tvSearchMess.text =  context?.getString(R.string.message_no_search_result)
                    else{
                        adapter.setMovieList(result.value)
                        binding.tvSearchMess.text = ""
                    }
                }
                is ResultOf.Failure -> {
                    hideProgressBar(binding.progressBar)
                    result.message?.let { displayMessage(it, requireContext())}
                }
                is ResultOf.Loading -> displayProgressBar(binding.progressBar)
            }
        })

        viewModelSearch.notification.observe(viewLifecycleOwner, { result ->
            hideProgressBar(binding.progressBar)
            if(result != null && !result.hasBeenHandled()) displayMessage(result.contentIfNotHandled.toString(), requireContext())
        })
    }
}