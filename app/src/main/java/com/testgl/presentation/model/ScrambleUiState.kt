package com.testgl.presentation.model

data class ScrambleUiState(
    val currentScrambleWord: String = "",
    val hint: String = "",
    val gameScore: Int = 0
)