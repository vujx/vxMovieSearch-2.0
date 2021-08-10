package com.algebra.moviefinder30.domain.util.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CheckValueKtTest {

    private fun checkValueTest(value: String): String =
        if (value == "N/A" || value == "") "Value not available!"
        else value

    @Test
    fun `check value after passing empty string if returns Value not available!`() {
        val result = checkValueTest("")
        assertThat(result).isEqualTo("Value not available!")
    }

    @Test
    fun `check value after passing NA if returns ValueNotAvailable`() {
        val result = checkValueTest("N/A")
        assertThat(result).isEqualTo("Value not available!")
    }

    @Test
    fun `check value after passing correct value if returns pass value`() {
        val result = checkValueTest("test")
        assertThat(result).isEqualTo("test")
    }

    private fun checkYearTest(value: String) =
        try {
            value.toInt()
            if (value.toInt() < 1888) "Year not available"
            else value
        } catch (e: Exception) {
            "Year not available"
        }

    @Test
    fun `check year after passing empty string if returns Year not available`() {
        val result = checkYearTest("")
        assertThat(result).isEqualTo("Year not available")
    }

    @Test
    fun `check year after passing wrong entry if returns Year not available`() {
        val result = checkYearTest("1887")
        assertThat(result).isEqualTo("Year not available")
    }

    @Test
    fun `check year after passing correct value if returns pass year`() {
        val result = checkYearTest("1999")
        assertThat(result).isEqualTo("1999")
    }

    private fun checkPictureURLTest(value: String): String =
        if (value == "N/A")
            ""
        else value

    @Test
    fun `check picture URL after passing wrong entry if returns empty string`() {
        val result = checkPictureURLTest("N/A")
        assertThat(result).isEqualTo("")
    }

    @Test
    fun `check picture URL after passing correct URL if returns pass URL`() {
        val result = checkPictureURLTest("http://test.com")
        assertThat(result).isEqualTo("http://test.com")
    }
}
