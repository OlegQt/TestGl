package com.testgl.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testgl.presentation.model.ScrambleUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScrambleGameViewModel : ViewModel() {
    private var currentWord:String = ""

    private val userWords = mutableListOf<String>()

    private val _uiState = MutableStateFlow(ScrambleUiState())
    val uiState = _uiState.asStateFlow()

    init {
        pickNewWord()
        _uiState.update {
            it.copy(currentScrambleWord = currentWord)
        }
    }

    private fun getAllWords(): List<String> {
        return listOf(
            "Dog",
            "Table",
            "Mushroom",
            "Cocktail",
            "Transparent"
        )
    }

    fun pickNewWord() {
        currentWord = getAllWords().random()

        shuffleWord(currentWord)
    }

    private fun shuffleWord(unShuffledWord:String){
        val word = unShuffledWord.toCharArray()
        word.shuffle()
        _uiState.update {
            it.copy(currentScrambleWord = String(word))
        }

    }

}