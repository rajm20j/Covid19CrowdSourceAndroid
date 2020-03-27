package com.example.covid19.home.model

data class HomeDataClass(
    val cases_time_series: List<CasesTimeSery>,
    val key_values: List<KeyValue>,
    val statewise: List<Statewise>,
    val tested: List<Tested>
)