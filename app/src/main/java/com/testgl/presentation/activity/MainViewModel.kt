package com.testgl.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    val textData = MutableStateFlow("Text")

    init {
        viewModelScope.launch {
            while (true){
                delay(100)
                textData.emit(System.currentTimeMillis().toString())
            }
        }
    }
}