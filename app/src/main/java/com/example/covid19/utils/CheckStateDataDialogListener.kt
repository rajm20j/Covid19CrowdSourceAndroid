package com.example.covid19.utils

interface CheckStateDataDialogListener {
    fun getStateBS(email: String, name: String)
    fun dismissDialog()
}