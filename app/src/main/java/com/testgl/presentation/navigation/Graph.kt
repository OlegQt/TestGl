package com.testgl.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Graph() {
    @Serializable
    data object GreetingScreen : Graph()

    @Serializable
    data class AboutScreen(val screenName: String) : Graph()
}