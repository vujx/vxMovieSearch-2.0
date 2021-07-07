package com.algebra.moviesearching.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.algebra.moviesearching.model.MovieDetail
import com.algebra.moviesearching.repository.MovieDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repo: MovieDetailRepository): ViewModel() {

    var errorObserver: MutableLiveData<String> = MutableLiveData()
    var progressBarObserver: MutableLiveData<Int> = MutableLiveData()
    var moviesObserver: MutableLiveData<MovieDetail> = MutableLiveData()

    fun getMovieDetails(imdbId: String){
        progressBarObserver.postValue(0)
        repo.getMovieDetail(imdbId, object: MovieDetailRepository.Listener{
            override fun onSuccess(movieDetail: MovieDetail) {
                moviesObserver.postValue(movieDetail)
                progressBarObserver.postValue(1)
            }

            override fun onFailure(error: String) {
                errorObserver.postValue(error)
                progressBarObserver.postValue(1)
            }
        })
    }

}