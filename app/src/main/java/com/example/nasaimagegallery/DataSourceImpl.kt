package com.example.nasaimagegallery

import com.example.nasaimagegallery.datamodel.PlanetDataModel

class DataSourceImpl() : DataSource {

    override fun loadLocalResource(): List<PlanetDataModel> {
        val jsonResponseData = listOf<PlanetDataModel>()
        return jsonResponseData
    }
}
