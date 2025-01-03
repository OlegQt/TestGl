package com.testgl.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val textData = MutableStateFlow("Text")
    val observable = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            while (true){
                delay(100)
                textData.emit(System.currentTimeMillis().toString())
            }
        }
    }

    fun showMessageDlg(newMessage:String){
        observable.value = newMessage
    }
}