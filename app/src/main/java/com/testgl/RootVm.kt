package com.testgl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RootVm : ViewModel() {
    private val _date:MutableLiveData<String> = MutableLiveData("toDay")
    val date:LiveData<String> = _date

    init {
        viewModelScope.launch {
            while (true){
                delay(10)
                _date.value = System.currentTimeMillis().toString()
            }
        }
    }
}