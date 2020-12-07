package com.example.nasaimagegallery.repository

import com.example.nasaimagegallery.DataSource
import com.example.nasaimagegallery.datamodel.PlanetData

class RepositoryImpl(private val dataSource: DataSource) : Repository {

    override fun loadData(): PlanetData {
        return dataSource.loadLocalResource()
    }
}
