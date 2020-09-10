package com.example.covid19.data

import com.google.gson.JsonElement
import io.reactivex.Single
import retrofit2.http.GET

interface ApiCallInterface {

    @GET("v4/data.json")
    fun homeData(): Single<JsonElement>

    @GET("v4/timeseries.json")
    fun homeStateData(): Single<JsonElement>
}