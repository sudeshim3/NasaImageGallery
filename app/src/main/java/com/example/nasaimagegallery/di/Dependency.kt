package com.example.nasaimagegallery.di

import android.content.Context
import com.example.nasaimagegallery.DataSource
import com.example.nasaimagegallery.DataSourceImpl
import com.example.nasaimagegallery.repository.RepositoryImpl
import com.example.nasaimagegallery.viewmodel.SharedVMFactory
import com.squareup.moshi.Moshi

class Dependency(private val applicationContext: Context) {

    private var repositoryImpl: RepositoryImpl? = null

    private fun getRepository(): RepositoryImpl {
        return repositoryImpl ?: RepositoryImpl(getDataSource()).also {
            repositoryImpl = it
        }
    }

    private fun getDataSource(): DataSource {
        return DataSourceImpl(applicationContext, getMoshi())
    }

    private fun getMoshi() = Moshi.Builder().build()

    fun getSharedVMFactory(): SharedVMFactory {
        return SharedVMFactory(getRepository())
    }
}
