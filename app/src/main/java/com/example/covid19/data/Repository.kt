package com.example.covid19.data

import com.google.gson.JsonElement
import io.reactivex.Single

class Repository(private val apiCallInterface: ApiCallInterface) {
    fun executeHomeDataApi(): Single<JsonElement> {
        return apiCallInterface.homeData()
    }
}