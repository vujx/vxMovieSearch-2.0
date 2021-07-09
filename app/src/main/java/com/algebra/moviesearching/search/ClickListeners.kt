package com.algebra.moviesearching.search

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import com.algebra.moviesearching.constants.Constants
import com.algebra.moviesearching.details.DetailsActivity
import com.algebra.moviesearching.list.MovieAdapter
import com.algebra.moviesearching.model.FavoriteMovie
import com.algebra.moviesearching.viewModel.FavoriteMoviesViewModel

class ClickListeners {

    fun clickListeners(viewModelFavoriteMovie: FavoriteMoviesViewModel, adapter: MovieAdapter, listOfFavMovies: MutableList<FavoriteMovie>, activity: SearchMoviesActivity){
        adapter.listener = object: MovieAdapter.Listener{
            override fun onFavClick(favMovie: FavoriteMovie, isFav: Boolean) {
                if(!isFav) {
                    viewModelFavoriteMovie.insertMovieToFavorite(favMovie)
                    listOfFavMovies.add(favMovie)
                } else{
                    viewModelFavoriteMovie.removeMovieFromFavorite(favMovie.imdbId)
                    for(i: Int in 0 until listOfFavMovies.size){
                        if(listOfFavMovies[i].imdbId == favMovie.imdbId){
                            listOfFavMovies.removeAt(i)
                            break
                        }
                    }
                }
            }

            @SuppressLint("RestrictedApi")
            override fun onItemClick(imdbId: String, title: String) {
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra(Constants.IMDB_ID, imdbId)
                val bundle = ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
                adapter.listOfFav.forEach {
                    if(it.imdbId == imdbId)
                        intent.putExtra(Constants.IS_FAV, 1)
                }
                intent.putExtra(Constants.TITLE, title)
                activity.startActivity(intent, bundle)
                activity.supportActionBar?.collapseActionView()
            }
        }
    }
}