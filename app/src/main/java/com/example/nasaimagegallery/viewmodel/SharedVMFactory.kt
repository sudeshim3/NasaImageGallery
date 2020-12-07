package com.example.nasaimagegallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasaimagegallery.repository.RepositoryImpl

class SharedVMFactory(private val repositoryImpl: RepositoryImpl) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlanetViewModel(repositoryImpl) as T
    }
}
