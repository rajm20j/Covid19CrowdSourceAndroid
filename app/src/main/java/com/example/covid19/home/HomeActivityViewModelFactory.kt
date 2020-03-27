package com.example.covid19.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.covid19.data.Repository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class HomeActivityViewModelFactory @Inject
constructor(private val repository: Repository) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeActivityViewModel::class.java)) {
            return HomeActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}