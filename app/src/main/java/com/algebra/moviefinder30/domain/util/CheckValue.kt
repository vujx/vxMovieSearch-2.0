package com.algebra.moviefinder30.domain.util

fun checkValue(value: String): String =
    if(value == "N/A") "Value not found!"
    else value

fun checkYear(value: String): String =
     try{
        value.toInt()
        if(value.toInt() < 1888) "0"
        else value
    } catch (e: Exception){
        "0"
    }

fun checkPictureURL(value: String): String =
    if(value == "N/A")
        "https://lh3.googleusercontent.com/proxy/GWY6mCuXRX6im-UOTlletKHpXQ3wv9DSlgZFpSz8aetXxAv6XPjQ4oj97ADYXxd0s44eIq9ywXHIwGabLBM0Bfng48E"
    else value
