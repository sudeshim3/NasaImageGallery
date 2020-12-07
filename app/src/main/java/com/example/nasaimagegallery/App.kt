package com.example.nasaimagegallery

import android.app.Application
import com.example.nasaimagegallery.di.Dependency
import com.example.nasaimagegallery.di.Injector

class App : Application() {
    private val _dependency = Dependency(this)
    private val dependency get() = _dependency
    private val _injector: Injector by lazy {
        Injector((applicationContext as App).dependency)
    }
    val injector get() = _injector
}
