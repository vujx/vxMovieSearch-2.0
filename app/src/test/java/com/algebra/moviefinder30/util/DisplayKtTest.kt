package com.algebra.moviefinder30.util

import com.algebra.moviefinder30.domain.network.connection.NoConnectivityException
import com.google.common.truth.Truth
import org.junit.Test
import java.io.IOException
import java.net.UnknownHostException

class DisplayKtTest {

    private fun displayErrorMessage(throwable: Throwable): String {
        return when {
            throwable.toString().contains("NoConnectivityException") || throwable.toString().contains("UnknownHostException") ->
                "Check you internet connection!"
            throwable.message.toString().contains("HTTP 404 Not Found") -> "Movie not found, try again!"
            else -> "Something went wrong, try again!"
        }
    }

    @Test
    fun `check error message for NullPointerException`() {
        val result = displayErrorMessage(NullPointerException())
        Truth.assertThat(result).isEqualTo("Something went wrong, try again!")
    }

    @Test
    fun `check error message for NoInternetException`() {
        val result = displayErrorMessage(NoConnectivityException())
        Truth.assertThat(result).isEqualTo("Check you internet connection!")
    }

    @Test
    fun `check error message for NoInternetUnknownHostException`() {
        val result = displayErrorMessage(UnknownHostException())
        Truth.assertThat(result).isEqualTo("Check you internet connection!")
    }

    @Test
    fun `check error message for IOException`() {
        val result = displayErrorMessage(IOException())
        Truth.assertThat(result).isEqualTo("Something went wrong, try again!")
    }
}
