package com.algebra.moviefinder30.presentation.ui.favorites

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.databinding.FragmentFavoriteBinding
import com.algebra.moviefinder30.presentation.ui.MainActivity
import com.algebra.moviefinder30.presentation.ui.dialog.DialogListener
import com.algebra.moviefinder30.presentation.viewmodel.FavoriteMoviesViewModel
import com.algebra.moviefinder30.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteAdapterListener, DialogListener {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding get() = _binding!!
    private val viewModelFavorite: FavoriteMoviesViewModel by viewModels()

    @Inject
    lateinit var adapter: FavoriteAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            exitFromApp(requireActivity() as MainActivity)
        }

        adapter.setListener(this)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setUpRecyclerView()
        onBind()
        viewModelFavorite.getAllFavoriteMovies()
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
        searchView.queryHint = context?.getString(R.string.enter_movie)

        searchAction(searchView, view, null)
        return super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAllFavoriteMovies) showRemoveDialog(requireActivity(), this)
        return super.onOptionsItemSelected(item)
    }

    private fun onBind() {
        viewModelFavorite.favorites.observe(
            viewLifecycleOwner,
            { result ->
                when (result) {
                    is ResultOf.Success -> {
                        hideProgressBar(binding.progressBar)
                        if (result.value.isEmpty()) {
                            binding.tvTitleFavorite.text = context?.getString(R.string.favorite)
                            binding.tvDisplayMess.text =
                                context?.getString(R.string.message_no_favorite_movies)
                        } else {
                            binding.tvTitleFavorite.text = ""
                            binding.tvDisplayMess.text = ""
                        }
                        adapter.setList(result.value)
                    }
                    is ResultOf.Failure -> {
                        hideProgressBar(binding.progressBar)
                        result.throwable?.let { displayErrorMessage(it, requireContext()) }
                    }
                    is ResultOf.Loading -> displayProgressBar(binding.progressBar)
                }
            }
        )

        viewModelFavorite.notification.observe(
            viewLifecycleOwner,
            { result ->
                hideProgressBar(binding.progressBar)
                if (result != null && !result.hasBeenHandled()) displayMessage(
                    result.contentIfNotHandled.toString(),
                    requireContext()
                )
            }
        )
    }

    private fun setUpRecyclerView() {
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.adapter = adapter
    }

    override fun onFavClick(imdbId: String) {
        viewModelFavorite.removeFavoriteMovie(imdbId)
    }

    override fun onItemClick(imdbId: String, title: String) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment3(imdbId)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

    override fun okPress() {
        viewModelFavorite.removeAllFavoriteMovies()
    }
}
