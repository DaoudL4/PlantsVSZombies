package com.example.plantsvszombies

import android.app.Application
import android.content.res.Resources;

class App : Application() {
    companion object {
        lateinit var instance: App private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}