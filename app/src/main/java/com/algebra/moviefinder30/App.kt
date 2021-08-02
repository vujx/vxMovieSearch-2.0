package com.algebra.moviefinder30

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        getResources = resources
    }

    companion object {
        lateinit var getResources: Resources
        fun getResource() = getResources
    }
}
