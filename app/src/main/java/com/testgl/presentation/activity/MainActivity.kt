package com.testgl.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowDesign()
        }
    }
}

@Composable
fun ShowDesign(){
    Column {
        BasicText("String")
        Text("Text")
        Button(onClick = {}) {
            Text("Push")

        }
    }
}