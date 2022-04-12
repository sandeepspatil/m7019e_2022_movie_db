package com.ltu.m7019edemoapp

import android.app.Application
import timber.log.Timber

class TheMovieDatabase : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}