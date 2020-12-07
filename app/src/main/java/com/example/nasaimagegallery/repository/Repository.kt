package com.example.nasaimagegallery.repository

import com.example.nasaimagegallery.datamodel.PlanetData

interface Repository {
    fun loadData(): PlanetData
}
