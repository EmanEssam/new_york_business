package com.example.moviesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@HiltAndroidApp
class NewYorkBusinessApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}