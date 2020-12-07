package com.example.nasaimagegallery

import com.example.nasaimagegallery.datamodel.PlanetDataModel

interface DataSource {
    fun loadLocalResource(): List<PlanetDataModel>
}
