package com.algebra.moviesearching.repository

import com.algebra.moviesearching.model.Movie
import com.algebra.moviesearching.networking.SearchingMoviesService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call

@RunWith(MockitoJUnitRunner::class)
class FakeSearchMoviesRepository {

    @Mock
    private lateinit var apiSearchingMoviesService: SearchingMoviesService
    private lateinit var searchMoviesRepo: SearchMoviesRepository

    @Before
    fun setup(){
        searchMoviesRepo = SearchMoviesRepository(apiSearchingMoviesService)
    }

    @Test
    fun getSearchMoviesTest(){
        val call = mock(Call::class.java) as Call<Movie>

        `when`(apiSearchingMoviesService.getSearchMovies(anyString(), anyString())).thenReturn(call)

        searchMoviesRepo.getMovies("test", object: SearchMoviesRepository.Listener{
            override fun onSuccess(listOfMovies: Movie) {}
            override fun onFailure(error: String) {}
        })

        verify(call, times(1)).enqueue(any())
    }

    @Test(expected = NullPointerException::class)
    fun getSearchMoviesRepoTest_NullPointerException(){
        `when`(apiSearchingMoviesService.getSearchMovies(anyString(), anyString())).thenReturn(null)

        searchMoviesRepo.getMovies("test", object: SearchMoviesRepository.Listener{
            override fun onSuccess(listOfMovies: Movie) {}
            override fun onFailure(error: String) {}
        })
    }


}