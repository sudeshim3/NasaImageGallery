package com.example.nasaimagegallery.helper

sealed class Response {
    data class Result(val stringData: String) : Response()
    data class Error(val exception: Exception) : Response()
}
