package com.example.covid19.travelHistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covid19.R
import com.example.covid19.databinding.ActivityTravelBinding

class TravelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTravelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTravelBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
