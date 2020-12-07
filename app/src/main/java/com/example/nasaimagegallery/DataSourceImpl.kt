package com.example.nasaimagegallery

import android.content.Context
import com.example.nasaimagegallery.AppConstant.SOURCE_DATA
import com.example.nasaimagegallery.datamodel.PlanetData
import com.example.nasaimagegallery.datamodel.PlanetDataModel
import com.example.nasaimagegallery.helper.JsonHelper
import com.example.nasaimagegallery.helper.Response
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class DataSourceImpl(private val applicationContext: Context, private val moshi: Moshi) :
    DataSource {

    override fun loadLocalResource(): PlanetData {
        val jsonResponseData = JsonHelper.getJsonDataFromAsset(
            applicationContext,
            SOURCE_DATA
        )
        val type: Type = Types.newParameterizedType(List::class.java, PlanetDataModel::class.java)
        val planetJsonAdapter = moshi.adapter<List<PlanetDataModel>>(type)

        return when (jsonResponseData) {
            is Response.Error -> PlanetData.Error(jsonResponseData.exception)
            is Response.Result -> {
                try {
                    val result = planetJsonAdapter.fromJson(jsonResponseData.stringData)
                    if (result.isNullOrEmpty()) {
                        return PlanetData.Empty
                    } else {
                        PlanetData.Result(result)
                    }
                } catch (exception: Exception) {
                    return PlanetData.Error(exception)
                }
            }
        }
    }
}
