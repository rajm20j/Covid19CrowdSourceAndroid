package com.example.covid19.data

import com.google.gson.JsonElement
import io.reactivex.Single
import retrofit2.http.GET

interface ApiCallInterface {

    @GET("/data.json")
    fun homeData(): Single<JsonElement>
}