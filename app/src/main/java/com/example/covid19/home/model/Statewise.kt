package com.example.covid19.home.model

data class Statewise(
    val active: String,
    val confirmed: String,
    val deaths: String,
    val delta: Delta,
    val lastupdatedtime: String,
    val recovered: String,
    val state: String
)