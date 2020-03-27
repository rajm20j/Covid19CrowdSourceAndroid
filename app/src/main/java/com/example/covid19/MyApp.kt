package com.example.covid19

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.covid19.di.AppComponent
import com.example.covid19.di.AppModule
import com.example.covid19.di.DaggerAppComponent
import com.example.covid19.di.UtilsModule

class MyApp : Application() {
    lateinit var myComponent: AppComponent

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        myComponent = createMyComponent()
        context = this.applicationContext
    }

    private fun createMyComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .appModule(AppModule(this.applicationContext))
            .utilsModule(UtilsModule())
            .build()
    }
}