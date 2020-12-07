package com.example.nasaimagegallery

import com.example.nasaimagegallery.datamodel.PlanetData

interface DataSource {
    fun loadLocalResource(): PlanetData
}
