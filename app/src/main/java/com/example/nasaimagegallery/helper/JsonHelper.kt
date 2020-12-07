package com.example.nasaimagegallery.helper

import android.content.Context
import com.example.nasaimagegallery.R

class JsonHelper {
    companion object {
        fun getJsonDataFromAsset(context: Context, fileName: String): Response {
            lateinit var jsonString: String
            try {
                jsonString = context.resources.openRawResource(R.raw.data).bufferedReader().use {
                    it.readText()
                }
            } catch (exception: Exception) {
                return Response.Error(exception)
            }
            return Response.Result(jsonString)
        }
    }
}
