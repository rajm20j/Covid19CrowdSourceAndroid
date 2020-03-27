package com.example.covid19.rawData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.covid19.data.Repository
import javax.inject.Inject

class DataActivityViewModelFactory @Inject
constructor(private val repository: Repository) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataActivityViewModel::class.java)) {
            return DataActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}