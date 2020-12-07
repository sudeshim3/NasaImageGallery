package com.example.nasaimagegallery.di

import com.example.nasaimagegallery.fragments.ImageListFragment

class Injector(private val dependency: Dependency) {

    fun inject(client: Any) {
        when (client) {
            is ImageListFragment -> injectDependencies(client)
        }
    }

    private fun injectDependencies(client: ImageListFragment) {
        client.viewModelFactory = dependency.getSharedVMFactory()
    }
}
