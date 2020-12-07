package com.example.nasaimagegallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaimagegallery.datamodel.PlanetDataModel
import com.example.nasaimagegallery.repository.Repository

open class PlanetViewModel(
    private val repositoryImpl: Repository
) : ViewModel() {
    var planetList: List<PlanetDataModel> = listOf()
    private val liveData = MutableLiveData<List<PlanetDataModel>>()

    init {
        loadData()
    }

    private fun loadData() {
        val result = repositoryImpl.loadData()
        liveData.postValue(result)
    }
}
