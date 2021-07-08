package com.algebra.moviesearching.viewModel

import androidx.lifecycle.ViewModel
import com.algebra.moviesearching.model.SearchHistory
import com.algebra.moviesearching.repository.SearchHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(private val repo: SearchHistoryRepository): ViewModel() {

    suspend fun getSearchHistory(searchValue: String) = repo.getSearchHistory(searchValue)

    fun insertSearchHistory(searchHistory: SearchHistory){
        repo.insertSearchHistory(searchHistory)
    }

}