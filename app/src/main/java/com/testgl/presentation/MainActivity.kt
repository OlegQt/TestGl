package com.testgl.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.testgl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding:ActivityMainBinding?= null
    private val binding get() = _binding ?: throw Exception("null binding NPE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setUpUi()
    }

    private fun setUpUi(){
        supportActionBar?.let {
            it.title = "GUI test"
            it.subtitle = "application"
        }
    }
}