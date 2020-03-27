package com.example.covid19.rawData

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covid19.MyApp
import com.example.covid19.R

class DataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        (application as MyApp).myComponent.doInjection(this)
    }
}
