package com.testgl.presentation.screens.scramble

data class ScrambleUiState(
    val currentScrambleWord: String = "",
    val hint: String = "",
    val gameScore: Int = 0,
    val inputTextLine: String = ""
)