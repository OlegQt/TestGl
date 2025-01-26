package com.testgl.presentation.navigation

import kotlinx.serialization.Serializable

// Classes that represent the screens in the app
sealed class Graph() {
    @Serializable
    data object GreetingScreen : Graph()

    @Serializable
    data class AboutScreen(val screenName: String) : Graph()

    @Serializable
    data object ScrambleGameScreen : Graph()

    enum class ScreenNames {
        Greetings, Scramble, About
    }
}