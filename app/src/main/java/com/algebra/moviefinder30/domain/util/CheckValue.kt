package com.algebra.moviefinder30.domain.util

import com.algebra.moviefinder30.App
import com.algebra.moviefinder30.R

fun checkValue(value: String): String =
    if(value == "N/A") App.getResource().getString(R.string.check_value_message)
    else value

fun checkYear(value: String): String =
     try{
        value.toInt()
        if(value.toInt() < 1888) App.getResource().getString(R.string.check_year_message)
        else value
    } catch (e: Exception){
         App.getResource().getString(R.string.check_year_message)
    }

fun checkPictureURL(value: String): String =
    if(value == "N/A")
        ""
    else value
