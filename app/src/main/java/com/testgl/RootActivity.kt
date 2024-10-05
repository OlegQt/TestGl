package com.testgl

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.testgl.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bar = supportActionBar
        bar?.title = "Title"
        bar?.subtitle = "Test"

        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txt.text = "Hello"
    }
}