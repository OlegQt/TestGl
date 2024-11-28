package com.testgl.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var _msgStr = MutableLiveData<String>()
    val msgStr = _msgStr as LiveData<String>

    private val _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long> = _elapsedTime

    private val startAppTime = System.currentTimeMillis()

    private var timerJob: Job? = null

    init {
        _msgStr.value = "Hello! This is ViewModel initialisation"

        startTimer()
    }

    fun changeData(newStringData: String) {
        _msgStr.postValue(newStringData)
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(10)
                _elapsedTime.postValue(System.currentTimeMillis() - startAppTime)
            }
        }.also { it.start() }
    }

    fun stopTimer() {
        timerJob?.cancel()
    }
}