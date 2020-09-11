package com.example.covid19.home.newModel.dataV4

import com.example.covid19.home.newModel.dataV4.districts.Districts
import com.example.covid19.home.newModel.dataV4.meta.Meta
import com.example.covid19.home.newModel.dataV4.total.Total
import com.example.covid19.home.newModel.dataV4.delta.Delta
import java.util.*

class StateData {
    val districts: SortedMap<String, Districts>? = null
    val meta: Meta? = null
    val total: Total? = null
    val delta: Delta? = null
}