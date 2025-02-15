package com.testgl.presentation.screens.abouttestscreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AboutViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AboutScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val textInput = String()

    fun onEvent(eventType: AboutEventType) {
        when (eventType) {
            AboutEventType.Click -> changeInputText("Click")
            is AboutEventType.InputTxt -> changeInputText(eventType.newTxt)
        }
    }

    private fun changeInputText(newInputTxt: String) {
        _uiState.update {
            it.copy(txtInput = newInputTxt)
        }
    }
}

data class AboutScreenUiState(
    val txtInput: String = String()
)

sealed class AboutEventType() {
    data object Click : AboutEventType()
    data class InputTxt(val newTxt: String) : AboutEventType()
}