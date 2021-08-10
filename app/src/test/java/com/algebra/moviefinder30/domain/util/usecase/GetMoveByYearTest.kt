package com.algebra.moviefinder30.domain.util.usecase

import com.algebra.moviefinder30.data.FakeMovieNetworkRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetMoveByYearTest {

    private lateinit var fakeRepo: FakeMovieNetworkRepository

    @Before
    fun setup() {
        fakeRepo = FakeMovieNetworkRepository()
    }

    @Test
    fun `use case which check if api returns exception for list of movies`() = runBlockingTest {
        fakeRepo.setShouldReturnNetworkError(true)
        val result = fakeRepo.getMoviesByYear("")

        assertThat(result).isEqualTo(null)
    }

    @Test
    fun `use case which check if api returns list of movies by search value`() = runBlockingTest {
        fakeRepo.setShouldReturnNetworkError(false)
        val result = fakeRepo.getMoviesByYear("titanic")

        assertThat(result).isEqualTo(listOf(FakeMovieNetworkRepository.movie))
    }
}
