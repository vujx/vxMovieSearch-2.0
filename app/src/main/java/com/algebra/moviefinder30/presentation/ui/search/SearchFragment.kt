package com.algebra.moviefinder30.presentation.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.databinding.FragmentSearchBinding
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.presentation.ui.MainActivity
import com.algebra.moviefinder30.presentation.viewmodel.SearchMoviesViewModel
import com.algebra.moviefinder30.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapterListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private val viewModelSearch: SearchMoviesViewModel by viewModels()

    @Inject
    lateinit var adapter: SearchAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter.setListener(this)
        setUpToolbar()
        setUpRecyclerView()
        clickListener()
        onBind()
        viewModelSearch.fetchMovies(MainActivity.searchValue)
        viewModelSearch.getAllFavoriteMovies()
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu_search, menu)
        val searchItem = menu.findItem(R.id.searchIcon)
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = context?.getString(R.string.enter_movie)

        searchAction(searchView, view, viewModelSearch)
        return super.onCreateOptionsMenu(menu, menuInflater)
    }

    private fun setUpToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setUpRecyclerView() {
        binding.rvSearchMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchMovies.adapter = adapter
    }

    private fun clickListener() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModelSearch.fetchMovies(MainActivity.searchValue)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun onBind() {
        viewModelSearch.favorites.observe(
            viewLifecycleOwner,
            { result ->
                when (result) {
                    is ResultOf.Success -> {
                        hideProgressBar(binding.progressBar)
                        adapter.setFavoriteList(result.value)
                    }
                    is ResultOf.Failure -> {
                        hideProgressBar(binding.progressBar)
                        result.throwable?.let { displayErrorMessage(it, requireContext()) }
                    }
                    is ResultOf.Loading -> displayProgressBar(binding.progressBar)
                }
            }
        )

        viewModelSearch.movies.observe(
            viewLifecycleOwner,
            { result ->
                when (result) {
                    is ResultOf.Success -> {
                        hideProgressBar(binding.progressBar)
                        if (result.value.isEmpty())
                            binding.tvSearchMess.text = context?.getString(R.string.message_no_search_result)
                        else {
                            adapter.setMovieList(result.value)
                            binding.tvSearchMess.text = ""
                        }
                    }
                    is ResultOf.Failure -> {
                        hideProgressBar(binding.progressBar)
                        result.throwable?.let { displayErrorMessage(it, requireContext()) }
                    }
                    is ResultOf.Loading -> displayProgressBar(binding.progressBar)
                }
            }
        )

        viewModelSearch.notification.observe(
            viewLifecycleOwner,
            { result ->
                hideProgressBar(binding.progressBar)
                if (result != null && !result.hasBeenHandled()) displayMessage(result.contentIfNotHandled.toString(), requireContext())
            }
        )
    }

    override fun onFavClick(movie: Movie, isFav: Boolean) {
        if (isFav) viewModelSearch.removeMovieFromFavorite(movie.imdbId)
        else viewModelSearch.addMovieToFavorite(movie)
    }

    override fun onItemClick(imdb: String, isFav: Boolean) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment2(imdb, isFav)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }
}
