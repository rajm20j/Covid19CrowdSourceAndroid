package com.example.covid19.utils

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

object Utils {

    private fun toPrettyFormat(jsonString: String): String {
        val parser = JsonParser()
        val json = parser.parse(jsonString)
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(json)
    }

    fun logInPrettyFormat(tag: String?, string: String) {
        val prettyJson = toPrettyFormat(string)
        Log.v(tag, prettyJson)
    }

}