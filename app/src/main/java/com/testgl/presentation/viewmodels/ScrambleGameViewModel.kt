package com.testgl.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.testgl.presentation.model.ScrambleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScrambleGameViewModel : ViewModel() {
    private var currentWord: String = ""
    private var squore = 0

    private val userWords = mutableListOf<String>()

    private val _uiState = MutableStateFlow(ScrambleUiState())
    val uiState = _uiState.asStateFlow()


    init {
        pickNewWord()
    }

    private fun getAllWords(): List<String> {
        return listOf(
            "Adventure",
            "Bicycle",
            "Computer",
            "Guitar",
            "Elephant",
            "Butterfly",
            "Cucumber",
            "Library",
            "Jungle",
            "Sunshine",
            "Banana",
            "Chocolate",
            "Pineapple",
            "Mountain",
            "Rainbow",
            "Ocean",
            "University",
            "President",
            "Restaurant",
            "Wilderness",
            "Technology",
            "Galaxy",
            "Notebook",
            "Telescope",
            "Football",
            "Footballer",
            "Lighthouse",
            "Volcano",
            "Hospital",
            "Waterfall",
            "Wormhole",
            "Happiness",
            "Soccer",
            "Laughter",
            "Holiday",
            "Journey",
            "Pyramid",
            "Eclipse",
            "Meteor",
            "Astronaut",
            "Professor"
        )
    }

    private fun pickNewWord() {
        currentWord = getAllWords().random().uppercase()

        _uiState.update {
            it.copy(currentScrambleWord = String(currentWord.toCharArray().apply { shuffle() }))
        }
    }

    fun checkIfUnscrambled(userWord: String) {

    }

}