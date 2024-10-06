package com.testgl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class RootVm : ViewModel() {
    private var startTimeMillis: Long = 0L
    private var onPauseTime: Long = 0L

    private val _date: MutableLiveData<String> = MutableLiveData("toDay")
    val date: LiveData<String> = _date

    init {
        startTimeMillis = System.currentTimeMillis()

        viewModelScope.launch {
            while (true) {
                delay(COUNTING_DELAY_MILLS)

                _date.value = getTimeDelta(
                    timeEarlier = startTimeMillis,
                    timeLater = System.currentTimeMillis()
                )
            }
        }
    }

    private fun getTimeDelta(timeEarlier: Long, timeLater: Long): String {
        val dT = SimpleDateFormat("mm:ss", Locale.getDefault()).format(timeLater - timeEarlier)
        return dT
    }

    fun pauseActivity(){
        onPauseTime = System.currentTimeMillis()
    }

    fun getPauseTime():String = getTimeDelta(onPauseTime,System.currentTimeMillis())

    companion object {
        const val COUNTING_DELAY_MILLS = 100L
    }
}