package com.example.covid19.di

import com.example.covid19.data.Repository
import com.example.covid19.home.HomeActivity
import com.example.covid19.rawData.DataActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, UtilsModule::class])
@Singleton
interface AppComponent {
    fun doInjection(repository: Repository)

    fun doInjection(dataActivity: DataActivity)

    fun doInjection(homeActivity: HomeActivity)
}