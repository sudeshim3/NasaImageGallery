package com.example.nasaimagegallery.repository

import com.example.nasaimagegallery.DataSource
import com.example.nasaimagegallery.datamodel.PlanetDataModel

class RepositoryImpl(private val dataSource: DataSource) : Repository {

    override fun loadData(): List<PlanetDataModel> {
        return dataSource.loadLocalResource()
    }
}
