package com.algebra.moviesearching.repository

import com.algebra.moviesearching.model.MovieDetail
import com.algebra.moviesearching.networking.SearchingMoviesService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import org.mockito.Mockito.*


@RunWith(MockitoJUnitRunner::class)
class FakeMovieDetailRepository {

    @Mock
    private lateinit var apiSearchingMoviesService: SearchingMoviesService
    private lateinit var movieDetailRepo: MovieDetailRepository

    @Before
    fun setup(){
        movieDetailRepo = MovieDetailRepository(apiSearchingMoviesService)
    }

    @Test
    fun getMovieDetailTest(){
        val call = mock(Call::class.java) as Call<MovieDetail>

        `when`(apiSearchingMoviesService.getMovieDetails(anyString(), anyString()))
            .thenReturn(call)

        movieDetailRepo.getMovieDetail("test", object: MovieDetailRepository.Listener{
            override fun onSuccess(movieDetail: MovieDetail) {}
            override fun onFailure(error: String) {}
        })

        verify(call, times(1)).enqueue(any())
    }

    @Test(expected = NullPointerException::class)
    fun getMovieDetailRepoTest_NullPointerException(){
        `when`(apiSearchingMoviesService.getMovieDetails(anyString(), anyString()))
            .thenReturn(null)

        movieDetailRepo.getMovieDetail("test", object: MovieDetailRepository.Listener{
            override fun onSuccess(movieDetail: MovieDetail) {}
            override fun onFailure(error: String) {}
        })
    }


}