package com.algebra.moviesearching.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.algebra.moviesearching.model.Movie
import com.algebra.moviesearching.repository.SearchMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchMoviesViewModel @Inject constructor(private val repo: SearchMoviesRepository): ViewModel() {

    var errorObserver: MutableLiveData<String> = MutableLiveData()
    var progressBarObserver: MutableLiveData<Int> = MutableLiveData()
    var moviesObserver: MutableLiveData<Movie> = MutableLiveData()

    fun getAllMovies(searchValue: String){
        progressBarObserver.postValue(0)
        repo.getMovies(searchValue, object: SearchMoviesRepository.Listener{
            override fun onSuccess(listOfMovies: Movie) {
                moviesObserver.postValue(listOfMovies)
                progressBarObserver.postValue(1)
            }

            override fun onFailure(error: String) {
                errorObserver.postValue(error)
                progressBarObserver.postValue(1)
            }
        })
    }
}