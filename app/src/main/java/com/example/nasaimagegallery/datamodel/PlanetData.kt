package com.example.nasaimagegallery.datamodel

sealed class PlanetData {
    object Empty : PlanetData()
    data class Error(val exception: Exception) : PlanetData()
    data class Result(val data: List<PlanetDataModel>) : PlanetData()
}
