package com.example.covid19.home.model

data class Delta(
    val active: Int,
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int
)