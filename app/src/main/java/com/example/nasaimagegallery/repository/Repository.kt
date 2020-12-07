package com.example.nasaimagegallery.repository

import com.example.nasaimagegallery.datamodel.PlanetDataModel

interface Repository {
    fun loadData(): List<PlanetDataModel>
}
