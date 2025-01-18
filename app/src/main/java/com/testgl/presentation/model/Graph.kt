package com.testgl.presentation.model

import kotlinx.serialization.Serializable


sealed class Graph() {
    @Serializable
    data class MainScreen(val screenName: String) : Graph()

    @Serializable
    data class OptionScreen(val screenName: String) : Graph()
}