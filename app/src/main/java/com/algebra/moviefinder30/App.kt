package com.algebra.moviefinder30

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mInstace = this
        myResources = resources
    }

    companion object{
        lateinit var mInstace: App
        lateinit var myResources: Resources
        fun getInstance() = mInstace
        fun getResource() = myResources
    }
}
