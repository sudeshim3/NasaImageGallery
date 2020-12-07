package com.example.nasaimagegallery.datamodel

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PlanetDataModel(
    @Json(name = "copyright")
    val copyright: String? = null,
    @Json(name = "date")
    val date: String,
    @Json(name = "explanation")
    val imageDetail: String,
    @Json(name = "hdurl")
    val highResImageUrl: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val imageUrl: String
) : Parcelable
