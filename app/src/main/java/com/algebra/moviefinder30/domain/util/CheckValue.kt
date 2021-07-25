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
