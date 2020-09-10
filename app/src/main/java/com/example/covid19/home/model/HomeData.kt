package com.example.covid19.home.model

data class HomeData(
    val cases_time_series: List<CasesTimeSeryX>,
    val statewise: List<StatewiseX>,
    val tested: List<TestedX>
)