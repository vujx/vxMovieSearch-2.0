package com.algebra.moviefinder30.domain.util

fun checkValue(value: String): String =
    if(value == "N/A") "Value not found!"
    else value

fun checkYear(value: String): String =
     try{
        value.toInt()
        if(value.toInt() < 1888) "No year available"
        else value
    } catch (e: Exception){
        "No year available"
    }

fun checkPictureURL(value: String): String =
    if(value == "N/A")
        "https://images.app.goo.gl/xNoNTyvhgvYWVu1e9"
    else value
