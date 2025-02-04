package com.example.stamen_stoyanov_android

import android.app.Application
import com.example.stamen_stoyanov_android.data.AppDataContainer

class BookStoreApp : Application() {
    lateinit var container: AppDataContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}