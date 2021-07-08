package com.algebra.moviesearching.repository

import com.algebra.moviesearching.database.AppDatabase
import com.algebra.moviesearching.di.DatabaseIOExecutor
import com.algebra.moviesearching.model.SearchHistory
import java.util.concurrent.Executor
import javax.inject.Inject

class SearchHistoryRepository @Inject constructor(private val appDatabase: AppDatabase,
                                                  @DatabaseIOExecutor private val databaseExecutor: Executor
){

    private val searchHistoryDao = appDatabase.searchHistoryDao()

    fun insertSearchHistory(searchHistory: SearchHistory){
        databaseExecutor.execute {
            searchHistoryDao.insertSearchHistory(searchHistory)
        }
    }

    suspend fun getSearchHistory(searchValue: String) = searchHistoryDao.getSearchHistory(searchValue)

}